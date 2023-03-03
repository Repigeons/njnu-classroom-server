package cn.repigeons.njnu.classroom.commons.httpTrace

import cn.repigeons.commons.utils.GsonUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

internal class HttpTraceLogFilter : OncePerRequestFilter(), Ordered {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun getOrder(): Int = Ordered.LOWEST_PRECEDENCE - 10
    override fun doFilterInternal(
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var request = httpRequest
        var response = httpResponse
        val startTime = System.currentTimeMillis()
        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response)
            return
        }
        if (request !is ContentCachingRequestWrapper) {
            request = ContentCachingRequestWrapper(request)
        }
        if (response !is ContentCachingResponseWrapper) {
            response = ContentCachingResponseWrapper(response)
        }
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        try {
            filterChain.doFilter(request, response)
            status = response.getStatus()
        } finally {
            val path = request.getRequestURI()
            if (!filterLog(request.getRequestURI())) {
                val traceLog = HttpTraceLog(
                    path = path,
                    parameterMap = GsonUtils.toJson(request.getParameterMap()),
                    method = request.getMethod(),
                    timeTaken = System.currentTimeMillis() - startTime,
                    time = LocalDateTime.now().toString(),
                    status = status,
                    requestBody = getRequestBody(request),
                    responseBody = getResponseBody(response)
                )
                log.info("{}", traceLog)
            }
            updateResponse(response)
        }
    }

    private fun filterLog(requestURI: String): Boolean {
        return requestURI.startsWith("/actuator/")
    }

    private fun isRequestValid(request: HttpServletRequest): Boolean {
        return try {
            URI(request.requestURL.toString())
            true
        } catch (ex: URISyntaxException) {
            false
        }
    }

    private fun getRequestBody(request: HttpServletRequest): String {
        if (request.contentType?.let { MediaType.parseMediaType(it) } in IGNORE_CONTENT_TYPE) return ""
        val wrapper = WebUtils.getNativeRequest(
            request,
            ContentCachingRequestWrapper::class.java
        )
        val requestBody = if (wrapper != null) {
            String(wrapper.contentAsByteArray, StandardCharsets.UTF_8)
        } else ""
        return formatLog(requestBody)
    }

    private fun getResponseBody(response: HttpServletResponse): String {
        if (response.status != HttpStatus.OK.value()) return ""
        if (!response.contentType.startsWith("application/json")) return ""
        val wrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        val responseBody = if (wrapper != null) {
            String(wrapper.contentAsByteArray, StandardCharsets.UTF_8)
        } else ""
        return formatLog(responseBody)
    }

    private fun formatLog(log: String): String {
        // 最多输出1万字符
        var logs = log
        if (StringUtils.hasLength(logs) && logs.length > 10000) {
            logs = logs.substring(0, 10000)
            logs += "......"
        }
        return logs
    }

    private fun updateResponse(response: HttpServletResponse) {
        val responseWrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        responseWrapper?.copyBodyToResponse()
    }

    private data class HttpTraceLog(
        val path: String,
        val parameterMap: String,
        val method: String,
        val timeTaken: Long,
        val time: String,
        val status: Int,
        val requestBody: String,
        val responseBody: String,
    )

    companion object {
        private val IGNORE_CONTENT_TYPE = arrayOf(
            MediaType.MULTIPART_FORM_DATA
        )
    }
}
package cn.repigeons.njnu.classroom.commons.component

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange

@ControllerAdvice
class GlobalExceptionHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun handle(e: Exception, exchange: ServerWebExchange): CommonResult<Nothing> {
        val request = exchange.request
        logger.error("全局异常：{} {}", request.method, request.path)
        request.queryParams.forEach { (key: String, value: List<String>) ->
            logger.error("*****请求参数*****:{},{}", key, value)
        }
        logger.error("*********异常信息:{}", e.message, e)
        return when (e) {
            is HttpClientErrorException -> CommonResult.failed(e.responseMessage)
            is ResponseStatusException -> CommonResult.failed(e.reason ?: e.responseMessage)
            else -> CommonResult.failed(e.responseMessage)
        }
    }

    private val Exception.responseMessage: String
        get() = message.takeUnless { it.isNullOrBlank() } ?: "服务器异常"
}
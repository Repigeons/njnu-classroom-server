package cn.repigeons.njnu.classroom.commons.httpTrace

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Configuration
class WebfluxTraceLogFilter : WebFilter {
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> = ExchangeTrace(exchange).run {
        val startTime = System.currentTimeMillis()
        chain.filter(this).doFinally {
            if (!filterLog(request.path.value())) {
                val traceLog = HttpTraceLog(
                    path = request.path.value(),
                    method = request.method.name(),
                    status = response.statusCode.value(),
                    time = LocalDateTime.now().toString(),
                    timeTaken = System.currentTimeMillis() - startTime,
                    parameter = request.queryParams,
                    requestBody = request.body().formatLog(),
                    responseBody = response.body().formatLog(),
                )
                logger.info("{}", traceLog)
            }
        }
    }
}
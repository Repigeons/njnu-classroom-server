package cn.repigeons.njnu.classroom.commons.httpTrace

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Configuration
@ConditionalOnClass(ServerWebExchange::class)
class WebfluxTraceLogFilter : WebFilter {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ): Mono<Void> = ExchangeTrace(exchange).run {
        val startTime = System.currentTimeMillis()
        chain.filter(this).doFinally {
            if (!filterLog(request.path.value())) {
                val traceLog = HttpTraceLog(
                    path = request.path.value(),
                    parameterMap = request.queryParams,
                    method = request.method.name(),
                    timeTaken = System.currentTimeMillis() - startTime,
                    time = LocalDateTime.now().toString(),
                    status = response.statusCode.value(),
                    requestBody = request.body().formatLog(),
                    responseBody = response.body().formatLog(),
                )
                log.info("{}", traceLog)
            }
        }
    }
}
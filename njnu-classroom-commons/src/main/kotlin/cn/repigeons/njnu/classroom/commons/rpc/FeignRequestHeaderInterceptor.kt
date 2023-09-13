package cn.repigeons.njnu.classroom.commons.rpc

import feign.RequestInterceptor
import feign.RequestTemplate
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Configuration
class FeignRequestHeaderInterceptor : RequestInterceptor {
    @OptIn(DelicateCoroutinesApi::class)
    override fun apply(template: RequestTemplate) {
        GlobalScope.future {
            val exchange = Mono.deferContextual { context ->
                val exchange = context.get(ServerWebExchange::class.java)
                Mono.just(exchange)
            }
            val headers = exchange.map { it.request.headers }.awaitSingle()
            headers.forEach { key, values ->
                if (!key.startsWith("content", ignoreCase = true)) {
                    template.header(key, values)
                }
            }
        }
    }
}
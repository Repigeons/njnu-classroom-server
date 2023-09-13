package cn.repigeons.njnu.classroom.commons.utils

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Configuration
class RequestHolder : WebFilter {
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val request = exchange.request
        return chain.filter(exchange).contextWrite { ctx -> ctx.put("request", request) }
    }

    companion object {
        @JvmStatic
        @OptIn(DelicateCoroutinesApi::class)
        val request: ServerHttpRequest
            get() = GlobalScope.future {
                Mono.deferContextual<ServerHttpRequest> { ctx -> ctx.get("request") }.awaitSingle()
            }.get()
    }
}
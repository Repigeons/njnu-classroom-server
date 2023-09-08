package cn.repigeons.njnu.classroom.commons.utils

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
        val request: ServerHttpRequest
            get() = Mono.deferContextual<ServerHttpRequest> { ctx -> ctx.get("request") }.block()!!
    }
}
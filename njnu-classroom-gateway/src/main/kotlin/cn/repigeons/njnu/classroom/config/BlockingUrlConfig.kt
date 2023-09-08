package cn.repigeons.njnu.classroom.config

import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPatternRouteMatcher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Configuration
class BlockingUrlConfig(
    private val blockingUrlProperties: BlockingUrlProperties,
) : WebFilter {
    private val matcher = PathPatternRouteMatcher()
    private val forbiddenResponse = "{\"status\":403,\"message\":\"Blocking URL\",\"data\":null}".toByteArray()
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val requestPath = exchange.request.uri.path
        val response = exchange.response
        blockingUrlProperties.path.forEach { path ->
            if (path == requestPath) {
                response.statusCode = HttpStatus.FORBIDDEN
                response.headers["Content-Type"] = "application/json"
                val buffer = response.bufferFactory().wrap(forbiddenResponse)
                return response.writeWith(Flux.just(buffer))
            }
        }
        blockingUrlProperties.pattern.forEach { pattern ->
            if (matcher.match(pattern, matcher.parseRoute(requestPath))) {
                response.statusCode = HttpStatus.FORBIDDEN
                response.headers["Content-Type"] = "application/json"
                val buffer = response.bufferFactory().wrap(forbiddenResponse)
                return response.writeWith(Flux.just(buffer))
            }
        }
        return chain.filter(exchange)
    }
}
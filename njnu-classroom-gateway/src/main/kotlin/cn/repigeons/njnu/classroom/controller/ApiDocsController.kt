package cn.repigeons.njnu.classroom.controller

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/v3/api-docs")
class ApiDocsController(
    private val routeLocator: RouteLocator,
) {
    private val webClient = WebClient.create()

    @GetMapping
    fun default(exchange: ServerWebExchange): Mono<*> {
        val uri = exchange.request.uri
        val paths = routeLocator.routes
            .flatMap { route ->
                webClient.get()
                    .uri("${uri.scheme}://${uri.host}:${uri.port}/${route.id}${uri.path}")
                    .retrieve()
                    .bodyToMono<Map<String, Any>>()
                    .mapNotNull { it["paths"] as Map<*, *> }
                    .flatMapIterable { paths ->
                        paths.entries.mapNotNull { (key, value) ->
                            if (key == null) null
                            else "/${route.id}${key}" to value
                        }
                    }
                    .onErrorResume { Flux.empty() }
            }
            .collectList()
            .map { mapOf(*it.toTypedArray()) }
        return paths.map {
            mapOf(
                "openapi" to "3.0.1",
                "info" to mapOf(
                    "title" to "OpenAPI",
                    "version" to "v0"
                ),
                "servers" to emptyList<Nothing>(),
                "paths" to it,
            )
        }
    }
}
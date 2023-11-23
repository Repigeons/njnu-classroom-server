package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.rpc.fallback.CoreClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import reactor.core.publisher.Mono

@FeignClient(
    value = "njnu-classroom-core",
    path = "/core",
    fallbackFactory = CoreClientFallbackFactory::class
)
interface CoreClient {
    @PostMapping("/api/flushCache")
    fun flushCache(): Mono<Void>
}
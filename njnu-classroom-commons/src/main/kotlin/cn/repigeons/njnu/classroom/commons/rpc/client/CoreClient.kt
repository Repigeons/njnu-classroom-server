package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.rpc.fallback.CoreClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    value = "njnu-classroom-core",
    fallback = CoreClientFallback::class
)
interface CoreClient {
    @PostMapping("/api/flushCache")
    fun flushCache()
}
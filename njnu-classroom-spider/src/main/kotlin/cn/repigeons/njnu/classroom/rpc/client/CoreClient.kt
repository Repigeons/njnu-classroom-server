package cn.repigeons.njnu.classroom.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.rpc.fallback.CoreClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping

@FeignClient(
    value = "njnu-classroom-core",
    fallback = CoreClientFallback::class
)
interface CoreClient {
    @PostMapping("/api/flushCache")
    fun flushCache(): CommonResult<*>
}
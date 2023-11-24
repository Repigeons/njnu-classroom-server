package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.fallback.PortalClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    value = "njnu-classroom-portal",
    path = "/portal",
    fallbackFactory = PortalClientFallbackFactory::class
)
interface PortalClient {
    @GetMapping("/sso/openid")
    suspend fun token2openid(): CommonResult<String>
}
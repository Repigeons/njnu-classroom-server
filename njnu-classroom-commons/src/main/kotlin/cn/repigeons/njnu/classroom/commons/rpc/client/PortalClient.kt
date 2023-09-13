package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.fallback.PortalClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    value = "njnu-classroom-portal",
    path = "/portal",
    fallback = PortalClientFallback::class
)
interface PortalClient {
    @GetMapping("/sso/openid")
    fun token2openid(): CommonResult<String>
}
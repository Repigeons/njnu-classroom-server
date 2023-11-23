package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.fallback.PortalClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@FeignClient(
    value = "njnu-classroom-portal",
    path = "/portal",
    fallbackFactory = PortalClientFallbackFactory::class
)
interface PortalClient {
    @GetMapping("/sso/openid")
    fun token2openid(): Mono<CommonResult<String>>
}
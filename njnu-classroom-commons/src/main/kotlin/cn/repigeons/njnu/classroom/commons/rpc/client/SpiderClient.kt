package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.fallback.SpiderClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Mono

@FeignClient(
    value = "njnu-classroom-spider",
    path = "/spider",
    fallback = SpiderClientFallback::class
)
interface SpiderClient {
    @PostMapping("/run")
    fun run()

    @PostMapping("/checkWithEhall")
    fun checkWithEhall(
        @RequestParam jasdm: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
        @RequestParam zylxdm: String
    ): Mono<CommonResult<Boolean>>
}
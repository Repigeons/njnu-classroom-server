package cn.repigeons.njnu.classroom.commons.rpc.client

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.fallback.SpiderClientFallbackFactory
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    value = "njnu-classroom-spider",
    path = "/spider",
    fallbackFactory = SpiderClientFallbackFactory::class
)
interface SpiderClient {
    @PostMapping("/run")
    suspend fun run()

    @PostMapping("/checkWithEhall")
    suspend fun checkWithEhall(
        @RequestParam jasdm: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Int,
        @RequestParam zylxdm: String
    ): CommonResult<Boolean>
}
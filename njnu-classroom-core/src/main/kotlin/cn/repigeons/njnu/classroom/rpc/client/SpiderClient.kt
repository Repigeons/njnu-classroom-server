package cn.repigeons.njnu.classroom.rpc.client

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.rpc.fallback.SpiderClientFallback
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "SpiderClient",
    url = "http://spider:8080",
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
    ): CommonResponse<Boolean>
}

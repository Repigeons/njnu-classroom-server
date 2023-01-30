package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.service.SpiderService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SpiderController(
    private val spiderService: SpiderService,
) {
    @PostMapping("run")
    fun runSpider(): CommonResponse<*> {
        spiderService.run()
        return CommonResponse.success()
    }

    @PostMapping("checkWithEhall")
    fun checkWithEhall(
        @RequestParam jasdm: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
        @RequestParam zylxdm: String
    ): CommonResponse<Boolean> {
        val result = spiderService.checkWithEhall(jasdm, weekday, jc, zylxdm)
        return CommonResponse.success(result)
    }
}
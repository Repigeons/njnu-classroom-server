package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
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
    fun runSpider(): CommonResult<*> {
        spiderService.run()
        return CommonResult.success()
    }

    @PostMapping("checkWithEhall")
    fun checkWithEhall(
        @RequestParam jasdm: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
        @RequestParam zylxdm: String
    ): CommonResult<Boolean> {
        val result = spiderService.checkWithEhall(jasdm, weekday, jc, zylxdm)
        return CommonResult.success(result)
    }
}
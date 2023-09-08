package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.service.SpiderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "爬虫服务")
@RestController
class SpiderController(
    private val spiderService: SpiderService,
) {
    @Operation(summary = "异步执行")
    @PostMapping("run")
    fun runSpider(): CommonResult<Nothing> {
        spiderService.run()
        return CommonResult.success()
    }

    @Operation(summary = "校验一致性")
    @Parameters(
        Parameter(name = "jasdm", description = "教室代码"),
        Parameter(name = "weekday", description = "星期"),
        Parameter(name = "jc", description = "节次"),
        Parameter(name = "zylxdm", description = "资源类型代码"),
    )
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
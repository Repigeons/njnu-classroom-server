package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.service.RedisService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "服务开关")
@RestController
class ServiceSwitchController(
    private val redisService: RedisService,
) {
    private suspend fun get() =
        redisService.opsForValue().get("serviceSwitch").awaitSingleOrNull() as? Boolean ?: true

    private suspend fun set(value: Boolean) =
        redisService.opsForValue().set("serviceSwitch", value).awaitSingle()

    @Operation(summary = "获取服务开关")
    @GetMapping("serviceSwitch")
    suspend fun getServiceSwitch(): CommonResult<Boolean> {
        return CommonResult.success(get())
    }

    @Operation(summary = "设置服务开关")
    @PutMapping("serviceSwitch")
    suspend fun setServiceSwitch(
        @RequestParam value: Boolean
    ): CommonResult<Boolean> {
        set(value)
        return CommonResult.success(value)
    }
}
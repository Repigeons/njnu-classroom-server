package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.service.RedisService
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 服务开关
 */
@RestController
class ServiceSwitchController(
    private val redisService: RedisService,
) {
    private suspend fun get() =
        redisService.opsForValue().get("serviceSwitch").awaitSingle() as? Boolean ?: true

    private suspend fun set(value: Boolean) =
        redisService.opsForValue().set("serviceSwitch", value).awaitSingle()

    /**
     * 获取服务开关
     */
    @GetMapping("serviceSwitch")
    suspend fun getServiceSwitch(): CommonResult<Boolean> {
        return CommonResult.success(get())
    }

    /**
     * 设置服务开关
     */
    @PutMapping("serviceSwitch")
    suspend fun setServiceSwitch(
        @RequestParam value: Boolean
    ): CommonResult<Boolean> {
        set(value)
        return CommonResult.success(value)
    }
}
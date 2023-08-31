package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.component.ServiceSwitch
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * 服务开关
 */
@RestController
class ServiceSwitchController(
    private val serviceSwitch: ServiceSwitch
) {
    /**
     * 获取服务开关
     */
    @GetMapping("serviceSwitch")
    fun getServiceSwitch(): CommonResult<Boolean> {
        return CommonResult.success(serviceSwitch.value)
    }

    /**
     * 设置服务开关
     */
    @PutMapping("serviceSwitch")
    fun setServiceSwitch(
        @RequestParam value: Boolean
    ): CommonResult<Boolean> {
        serviceSwitch.value = value
        return CommonResult.success(serviceSwitch.value)
    }
}
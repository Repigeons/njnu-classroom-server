package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
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
    fun getServiceSwitch(): CommonResponse<Boolean> {
        return CommonResponse.success(serviceSwitch.value)
    }

    /**
     * 设置服务开关
     */
    @PutMapping("serviceSwitch")
    fun setServiceSwitch(
        @RequestParam value: Boolean
    ): CommonResponse<Boolean> {
        serviceSwitch.value = value
        return CommonResponse.success(serviceSwitch.value)
    }
}
package cn.repigeons.njnu.classroom.commons.component

import cn.repigeons.commons.redisService.RedisService
import org.springframework.stereotype.Component

@Component
class ServiceSwitch(
    private val redisService: RedisService
) {
    var value: Boolean
        set(value) = redisService.set("serviceSwitch", value)
        get() = redisService["serviceSwitch"] == true
}
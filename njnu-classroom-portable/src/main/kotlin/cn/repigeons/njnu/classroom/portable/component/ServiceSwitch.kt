package cn.repigeons.njnu.classroom.portable.component

import cn.repigeons.commons.redisService.RedisService
import org.springframework.stereotype.Component

@Component
class ServiceSwitch(
    private val redisService: RedisService
) {
    var value: Boolean
        private set(value) = redisService.set("serviceSwitch", value)
        get() = redisService["serviceSwitch"] == true
}
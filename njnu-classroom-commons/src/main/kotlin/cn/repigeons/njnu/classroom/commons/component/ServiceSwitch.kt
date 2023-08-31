package cn.repigeons.njnu.classroom.commons.component

import cn.repigeons.njnu.classroom.commons.service.RedisService
import org.springframework.stereotype.Component

@Component
class ServiceSwitch(
    private val redisService: RedisService
) {
    var value: Boolean
        get() = redisService.opsForValue().get("serviceSwitch").block() as? Boolean ?: true
        set(value) {
            redisService.opsForValue().set("serviceSwitch", value).subscribe()
        }
}
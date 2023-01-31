package cn.repigeons.njnu.classroom

import cn.repigeons.commons.utils.SpringUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@EnableCaching
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
open class CoreApplication

fun main(args: Array<String>) {
    SpringUtils.context = runApplication<CoreApplication>(*args)
}
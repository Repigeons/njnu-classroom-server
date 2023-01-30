package cn.repigeons.njnu.classroom

import cn.repigeons.commons.utils.SpringUtils
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@EnableScheduling
@SpringBootApplication
open class SpiderApplication

fun main(args: Array<String>) {
    SpringUtils.context = runApplication<SpiderApplication>(*args)
}
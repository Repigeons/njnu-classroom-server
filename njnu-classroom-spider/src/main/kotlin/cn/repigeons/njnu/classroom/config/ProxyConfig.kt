package cn.repigeons.njnu.classroom.config

import cn.repigeons.njnu.classroom.service.CookieService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy

@Configuration
class ProxyConfig(
    @Lazy
    private val cookieService: CookieService,
    @Value("\${proxy-pool.all:}")
    private val allProxy: String,
) {
    @Bean
    fun proxyPool() = ProxyPool(
        cookieService = cookieService,
        allProxy = allProxy,
    )
}
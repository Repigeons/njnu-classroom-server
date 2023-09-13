package cn.repigeons.njnu.classroom.commons.config

import cn.dev33.satoken.reactor.filter.SaReactorFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SaTokenConfig {
    @Bean
    fun saReactorFilter() = SaReactorFilter()
}
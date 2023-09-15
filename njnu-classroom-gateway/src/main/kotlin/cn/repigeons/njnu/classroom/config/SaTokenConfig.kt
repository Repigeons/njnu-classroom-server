package cn.repigeons.njnu.classroom.config

import cn.dev33.satoken.context.SaHolder
import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.util.SaResult
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.HttpClientErrorException

@Configuration
class SaTokenConfig {
    @Bean
    fun saReactorFilter(): SaReactorFilter = SaReactorFilter()
        .addInclude("/**")
        .setBeforeAuth {
            SaHolder.getResponse()
                .setHeader("server", "webflux/3.1.3")
        }
        .setError {
            if (it is HttpClientErrorException)
                return@setError SaResult.code(403).setMsg("Forbidden")
            throw it
        }
}
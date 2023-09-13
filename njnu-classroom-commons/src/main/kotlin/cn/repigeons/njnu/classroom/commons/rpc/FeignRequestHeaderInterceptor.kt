package cn.repigeons.njnu.classroom.commons.rpc

import cn.dev33.satoken.context.SaHolder
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ServerHttpRequest

@Configuration
class FeignRequestHeaderInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val request = SaHolder.getRequest().source as ServerHttpRequest
        request.headers.forEach { key, values ->
            if (!key.startsWith("content", ignoreCase = true)) {
                template.header(key, values)
            }
        }
    }
}
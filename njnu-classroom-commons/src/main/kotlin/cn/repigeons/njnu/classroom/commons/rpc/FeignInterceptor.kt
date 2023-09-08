package cn.repigeons.njnu.classroom.commons.rpc

import cn.repigeons.njnu.classroom.commons.utils.RequestHolder
import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.context.annotation.Configuration

@Configuration
class FeignRequestHeaderInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        RequestHolder.request.headers.forEach { key, values ->
            if (!key.startsWith("content", ignoreCase = true)) {
                template.header(key, values)
            }
        }
    }
}
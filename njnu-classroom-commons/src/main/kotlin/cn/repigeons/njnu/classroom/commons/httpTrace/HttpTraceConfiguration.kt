package cn.repigeons.njnu.classroom.commons.httpTrace

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnWebApplication
open class HttpTraceConfiguration {
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    internal class ServletTraceFilterConfiguration {
        @Bean
        fun httpTraceLogFilter(): HttpTraceLogFilter {
            return HttpTraceLogFilter()
        }
    }
}
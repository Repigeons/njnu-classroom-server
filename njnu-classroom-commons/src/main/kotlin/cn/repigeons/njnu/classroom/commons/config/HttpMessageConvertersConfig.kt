package cn.repigeons.njnu.classroom.commons.config

import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import java.util.stream.Collectors

@Configuration
class HttpMessageConvertersConfig {
    @Bean
    @ConditionalOnMissingBean
    fun messageConverters(converters: ObjectProvider<HttpMessageConverter<*>>): HttpMessageConverters {
        return HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()))
    }
}
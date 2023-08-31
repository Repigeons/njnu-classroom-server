package cn.repigeons.njnu.classroom.commons.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
class RedisCacheProperties {
    var keyPrefix: String = "\${spring.application.name}"
    val key: Map<String, String> = mutableMapOf()
    val expire: Map<String, Duration> = mutableMapOf()
}
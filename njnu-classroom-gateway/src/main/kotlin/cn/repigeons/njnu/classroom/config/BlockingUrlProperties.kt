package cn.repigeons.njnu.classroom.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "blocking")
class BlockingUrlProperties {
    var path: List<String> = mutableListOf()
    var pattern: List<String> = mutableListOf()
}
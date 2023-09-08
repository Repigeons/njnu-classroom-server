package cn.repigeons.njnu.classroom.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.context.annotation.Configuration

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "blocking")
class BlockingUrlProperties {
    val path: List<String> = mutableListOf()
    val pattern: List<String> = mutableListOf()
}
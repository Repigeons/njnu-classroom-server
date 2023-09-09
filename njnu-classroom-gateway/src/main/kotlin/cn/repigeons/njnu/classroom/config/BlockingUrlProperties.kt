package cn.repigeons.njnu.classroom.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = BlockingUrlProperties.PREFIX)
class BlockingUrlProperties {
    var path: List<String> = mutableListOf()
    var pattern: List<String> = mutableListOf()

    companion object {
        const val PREFIX = "blocking"
    }
}
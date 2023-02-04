package cn.repigeons.njnu.classroom.commons.config

import cn.repigeons.commons.redisTemplate.Repigeons2JsonRedisSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
open class RedisConfig {
    @Bean
    open fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Repigeons2JsonRedisSerializer<Any>()
        redisTemplate.hashKeySerializer = redisTemplate.keySerializer
        redisTemplate.hashValueSerializer = redisTemplate.valueSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}

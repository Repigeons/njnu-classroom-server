package cn.repigeons.njnu.classroom.portable.config

import cn.repigeons.commons.redisCache.RedisCacheManagerBuilder
import cn.repigeons.commons.redisService.RedisService
import cn.repigeons.commons.redisService.RedisServiceBuilder
import cn.repigeons.commons.redisTemplate.RedisTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
open class RedisConfig {
    @Bean
    open fun redisTemplate(
        redisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, Any> =
        RedisTemplateBuilder<Any>()
            .redisConnectionFactory(redisConnectionFactory)
            .build()

    @Bean
    open fun redisService(
        redisTemplate: RedisTemplate<String, Any>
    ): RedisService =
        RedisServiceBuilder()
            .redisTemplate(redisTemplate)
            .build()

    @Bean
    open fun redisCacheManager(
        redisConnectionFactory: RedisConnectionFactory,
        redisTemplate: RedisTemplate<String, Any>
    ): RedisCacheManager =
        RedisCacheManagerBuilder()
            .redisConnectionFactory(redisConnectionFactory)
            .redisTemplate(redisTemplate)
            .build()
}

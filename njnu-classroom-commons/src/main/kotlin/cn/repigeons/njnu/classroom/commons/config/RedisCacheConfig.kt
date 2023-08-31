package cn.repigeons.njnu.classroom.commons.config

import cn.repigeons.njnu.classroom.commons.component.CustomRedisCacheManager
import cn.repigeons.njnu.classroom.commons.properties.RedisCacheProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
@EnableConfigurationProperties(RedisCacheProperties::class)
class RedisCacheConfig {
    @Bean
    fun redisCacheManager(
        serializer: RedisSerializer<Any>,
        redisConnectionFactory: RedisConnectionFactory,
        redisCacheProperties: RedisCacheProperties,
    ): RedisCacheManager {
        val cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith("${redisCacheProperties.keyPrefix}::")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
            .disableCachingNullValues()
        return CustomRedisCacheManager(
            cacheWriter = cacheWriter,
            defaultCacheConfiguration = config,
            keys = redisCacheProperties.key,
            expires = redisCacheProperties.expire,
        )
    }
}
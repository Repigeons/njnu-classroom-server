package cn.repigeons.njnu.classroom.commons.component

import org.springframework.data.redis.cache.RedisCache
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import java.time.Duration

class CustomRedisCacheManager(
    cacheWriter: RedisCacheWriter,
    private val defaultCacheConfiguration: RedisCacheConfiguration,
    private val keys: Map<String, String>?,
    private val expires: Map<String, Duration>?,
) : RedisCacheManager(cacheWriter, defaultCacheConfiguration) {
    override fun createRedisCache(name: String, redisCacheConfiguration: RedisCacheConfiguration?): RedisCache {
        var cacheConfig = redisCacheConfiguration ?: defaultCacheConfiguration
        val cacheName = keys?.get(name)
        val expire = expires?.get(name)
            ?: cacheName?.run { Duration.ofSeconds(-1) }
            ?: Duration.ofHours(24)
        cacheConfig = cacheConfig.entryTtl(expire)
        return super.createRedisCache(cacheName ?: "_$name", cacheConfig)
    }
}
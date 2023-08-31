package cn.repigeons.njnu.classroom.commons.service.impl

import cn.repigeons.njnu.classroom.commons.service.RedisService
import org.springframework.data.redis.core.*
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant

@Service
class RedisServiceImpl(
    private val template: ReactiveRedisTemplate<String, Any>
) : RedisService {
    override fun opsForValue() = template.opsForValue()
    override fun opsForHash() = template.opsForHash<String, Any>()
    override fun opsForList() = template.opsForList()
    override fun opsForSet() = template.opsForSet()
    override fun opsForZSet() = template.opsForZSet()

    override suspend fun hasKey(key: String) =
        template.hasKeyAndAwait(key)

    override suspend fun delete(vararg keys: String) =
        template.deleteAndAwait(*keys)

    override suspend fun getExpire(key: String) =
        template.getExpireAndAwait(key)

    override suspend fun expire(key: String, time: Duration) =
        template.expireAndAwait(key, time)

    override suspend fun expireAt(key: String, time: Instant) =
        template.expireAtAndAwait(key, time)
}
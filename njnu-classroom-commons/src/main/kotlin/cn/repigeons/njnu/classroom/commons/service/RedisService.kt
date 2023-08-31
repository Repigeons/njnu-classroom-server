package cn.repigeons.njnu.classroom.commons.service

import org.springframework.data.redis.core.*
import java.time.Duration
import java.time.Instant

interface RedisService {
    fun opsForValue(): ReactiveValueOperations<String, Any>
    fun opsForHash(): ReactiveHashOperations<String, String, Any>
    fun opsForList(): ReactiveListOperations<String, Any>
    fun opsForSet(): ReactiveSetOperations<String, Any>
    fun opsForZSet(): ReactiveZSetOperations<String, Any>
    suspend fun hasKey(key: String): Boolean
    suspend fun delete(vararg keys: String): Long
    suspend fun getExpire(key: String): Duration?
    suspend fun expire(key: String, time: Duration): Boolean
    suspend fun expireAt(key: String, time: Instant): Boolean
}
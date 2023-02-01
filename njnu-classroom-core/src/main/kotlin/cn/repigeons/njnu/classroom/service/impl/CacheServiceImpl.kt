package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.commons.redisService.RedisService
import cn.repigeons.njnu.classroom.mbg.mapper.*
import cn.repigeons.njnu.classroom.model.ClassroomVO
import cn.repigeons.njnu.classroom.model.EmptyClassroomVO
import cn.repigeons.njnu.classroom.model.PositionVO
import cn.repigeons.njnu.classroom.model.TimetableVO
import cn.repigeons.njnu.classroom.service.CacheService
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isIn
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
open class CacheServiceImpl(
    private val redissonClient: RedissonClient,
    private val redisService: RedisService,
    private val jasMapper: JasMapper,
    private val positionsMapper: PositionsMapper,
    private val timetableMapper: TimetableMapper,
) : CacheService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable("classrooms")
    override fun getClassrooms() = flushClassrooms()

    @CachePut("classrooms")
    override fun flushClassrooms(): Map<String, List<ClassroomVO>> {
        return jasMapper.select { it }
            .map {
                ClassroomVO(
                    jxlmc = it.jxldmDisplay,
                    jsmph = it.jasmc?.replace(Regex("^${it.jxldmDisplay}"), "")?.trim(),
                    jasdm = it.jasdm,
                )
            }
            .groupBy { it.jxlmc }
    }

    @Cacheable("buildings-position")
    override fun getBuildingPositions() = flushBuildingPositions()

    @CachePut("buildings-position")
    override fun flushBuildingPositions(): List<PositionVO> {
        return positionsMapper.select {
            it.where(PositionsDynamicSqlSupport.kind, isEqualTo(1))
        }.map {
            PositionVO(
                name = it.name,
                position = listOf(it.latitude, it.longitude),
            )
        }
    }

    override fun flushCache(): CompletableFuture<*> = CompletableFuture.supplyAsync {
        val lock = redissonClient.getLock("lock:flushCache")
        if (!lock.tryLock()) {
            logger.info("刷新缓存数据已处于运行中...")
            return@supplyAsync
        }
        try {
            logger.info("开始刷新缓存数据...")
            val startTime = System.currentTimeMillis()
            val t1 = flushEmptyClassrooms()
            val t2 = flushOverview()
            t1.join(); t2.join()
            val endTime = System.currentTimeMillis()
            logger.info("缓存刷新完成. 共计耗时 {} 秒", (endTime - startTime) / 1000)
        } finally {
            lock.unlock()
        }
    }

    private fun flushEmptyClassrooms(): CompletableFuture<*> = CompletableFuture.supplyAsync {
        redisService.del("empty")
        logger.info("开始刷新空教室缓存...")
        val map = timetableMapper.select {
            it.where(TimetableDynamicSqlSupport.zylxdm, isIn("00", "10", "11"))
        }
            .groupBy {
                "${it.jxlmc}:${it.weekday}"
            }
            .map { (key, records) ->
                logger.debug("Flushing empty classroom: {}", key)
                val value = records.map { record ->
                    EmptyClassroomVO(
                        jasdm = record.jasdm!!,
                        jsmph = record.jsmph!!,
                        skzws = record.skzws!!,
                        jcKs = record.jcKs!!,
                        jcJs = record.jcJs!!,
                        zylxdm = record.zylxdm!!,
                    )
                }
                key to value
            }
            .toTypedArray()
            .let { mapOf(*it) }
        redisService.hSetAll("empty", map)
        logger.info("空教室缓存刷新完成")
    }

    private fun flushOverview(): CompletableFuture<*> = CompletableFuture.supplyAsync {
        redisService.del("overview")
        logger.info("开始刷新教室概览缓存...")
        val map = timetableMapper.select { it }
            .groupBy { it.jasdm!! }
            .map { (key, records) ->
                val classroomName = records.firstOrNull()?.let { it.jxlmc + it.jsmph }
                logger.debug("Flushing overview: {}", classroomName)
                val value = records.map { TimetableVO(it) }
                key to value
            }
            .toTypedArray()
            .let { mapOf(*it) }
        redisService.hSetAll("overview", map)
        logger.info("教室概览缓存刷新完成")
    }
}
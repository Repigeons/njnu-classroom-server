package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.service.RedisService
import cn.repigeons.njnu.classroom.commons.utils.ThreadPoolUtils
import cn.repigeons.njnu.classroom.model.vo.ClassroomVO
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import cn.repigeons.njnu.classroom.model.vo.PositionVO
import cn.repigeons.njnu.classroom.model.vo.TimetableVO
import cn.repigeons.njnu.classroom.mybatis.model.Positions
import cn.repigeons.njnu.classroom.mybatis.model.Timetable
import cn.repigeons.njnu.classroom.mybatis.service.IJasService
import cn.repigeons.njnu.classroom.mybatis.service.IPositionsService
import cn.repigeons.njnu.classroom.mybatis.service.ITimetableService
import cn.repigeons.njnu.classroom.service.CacheService
import com.mybatisflex.core.query.QueryWrapper
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.putAllAndAwait
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class CacheServiceImpl(
    private val redissonClient: RedissonClient,
    private val redisService: RedisService,
    private val jasService: IJasService,
    private val positionsService: IPositionsService,
    private val timetableService: ITimetableService,
) : CacheService {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Cacheable("classrooms")
    override fun getClassrooms(): List<ClassroomVO> = flushClassrooms()

    @CachePut("classrooms")
    override fun flushClassrooms(): List<ClassroomVO> {
        return jasService.list()
            .map {
                ClassroomVO.ClassroomItemVO(
                    jxlmc = it.jxldmDisplay,
                    jsmph = it.jasmc?.replace(Regex("^${it.jxldmDisplay}"), "")?.trim(),
                    jasdm = it.jasdm,
                )
            }
            .groupBy { it.jxlmc }
            .map { (jxlmc, list) ->
                ClassroomVO(
                    jxlmc = jxlmc,
                    list = list
                )
            }
    }

    @Cacheable("buildings-position")
    override fun getBuildingPositions() = flushBuildingPositions()

    @CachePut("buildings-position")
    override fun flushBuildingPositions(): List<PositionVO> {
        return positionsService.list(
            QueryWrapper()
                .eq(Positions::getKind, 1)
        ).map {
            PositionVO(
                name = it.name,
                position = listOf(it.latitude, it.longitude),
            )
        }
    }

    @PostConstruct
    override fun flushCache(): CompletableFuture<Void> = ThreadPoolUtils.runAsync {
        val lock = redissonClient.getLock("lock:flushCache")
        if (!lock.tryLock()) {
            logger.info("刷新缓存数据已处于运行中...")
            return@runAsync
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

    private fun flushEmptyClassrooms(): CompletableFuture<Unit> = CoroutineScope(Dispatchers.Default).future {
        redisService.delete("core::empty")
        logger.info("开始刷新空教室缓存...")
        val pairs = timetableService.list(
            QueryWrapper()
                .`in`(Timetable::getZylxdm, "00", "10", "11")
        )
            .groupBy {
                "${it.jxlmc}:${it.weekday}"
            }
            .map { (key, records) ->
                key to records.map { EmptyClassroomVO(it) }
            }
        redisService.opsForHash().putAllAndAwait("core::empty", mapOf(*pairs.toTypedArray()))
        logger.info("空教室缓存刷新完成")
    }

    private fun flushOverview(): CompletableFuture<Unit> = CoroutineScope(Dispatchers.Default).future {
        redisService.delete("core::overview")
        logger.info("开始刷新教室概览缓存...")
        val pairs = timetableService.list()
            .groupBy { it.jasdm }
            .map { (key, records) ->
                key to records.map { TimetableVO(it) }
            }
        redisService.opsForHash().putAllAndAwait("core::overview", mapOf(*pairs.toTypedArray()))
        logger.info("教室概览缓存刷新完成")
    }
}
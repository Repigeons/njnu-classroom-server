package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.service.RedisService
import cn.repigeons.njnu.classroom.model.vo.TimetableVO
import cn.repigeons.njnu.classroom.service.OverviewService
import org.springframework.data.redis.core.getAndAwait
import org.springframework.stereotype.Service

@Service
class OverviewServiceImpl(
    private val redisService: RedisService,
) : OverviewService {
    @Suppress("UNCHECKED_CAST")
    override suspend fun getOverview(jasdm: String): List<TimetableVO> =
        redisService.opsForHash().getAndAwait("core::overview", jasdm) as? List<TimetableVO>
            ?: throw IllegalArgumentException("无效参数: [jasdm]")
}

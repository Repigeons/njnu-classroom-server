package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.SpiderClient
import cn.repigeons.njnu.classroom.commons.service.RedisService
import cn.repigeons.njnu.classroom.commons.utils.EmailUtils
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.mbg.dao.FeedbackDAO
import cn.repigeons.njnu.classroom.mbg.mapper.CorrectionMapper
import cn.repigeons.njnu.classroom.mbg.mapper.FeedbackMapper
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableMapper
import cn.repigeons.njnu.classroom.mbg.model.Correction
import cn.repigeons.njnu.classroom.mbg.model.Feedback
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.withContext
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.springframework.data.redis.core.getAndAwait
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
class EmptyClassroomServiceImpl(
    private val spiderClient: SpiderClient,
    private val redisService: RedisService,
    private val cacheService: CacheService,
    private val timetableMapper: TimetableMapper,
    private val feedbackMapper: FeedbackMapper,
    private val feedbackDAO: FeedbackDAO,
    private val correctionMapper: CorrectionMapper,
) : EmptyClassroomService {
    override suspend fun getEmptyClassrooms(jxlmc: String, weekday: Weekday, jc: Short): List<EmptyClassroomVO> {
        if (jc !in 1..12) return emptyList()
        val jxlmcList = cacheService.getBuildingPositions().map { it.name }
        if (jxlmc !in jxlmcList) return emptyList()
        val classrooms = redisService.opsForHash().getAndAwait("core::empty", "$jxlmc:${weekday.name}") as List<*>?
            ?: return emptyList()
        return classrooms.mapNotNull { classroom ->
            classroom as EmptyClassroomVO
            if (jc in classroom.jcKs..classroom.jcJs)
                classroom
            else
                null
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun feedback(
        jxlmc: String,
        weekday: Weekday,
        jc: Short,
        results: List<EmptyClassroomVO>,
        index: Int
    ): CompletableFuture<Unit> = GlobalScope.future {
        val item = results[index]

        // 检查缓存一致性
        val count = timetableMapper.count {
            it.where(TimetableDynamicSqlSupport.weekday, isEqualTo(weekday.name))
                .and(TimetableDynamicSqlSupport.jcKs, isEqualTo(item.jcKs))
                .and(TimetableDynamicSqlSupport.jcJs, isEqualTo(item.jcJs))
                .and(TimetableDynamicSqlSupport.jasdm, isEqualTo(item.jasdm))
                .and(TimetableDynamicSqlSupport.zylxdm, isEqualTo(item.zylxdm))
        }
        if (count == 0L) {
            cacheService.flushCache()
            return@future
        }

        val obj = mapOf(
            "jc" to jc,
            "item" to item,
            "index" to index,
            "results" to results,
        )
        val detail = GsonUtils.toJson(obj)
        val subject = "【南师教室】用户反馈：" +
                "$jxlmc ${item.jsmph}教室 " +
                "${item.jcKs}-${item.jcJs}节有误" +
                "（当前为第${jc}节）"

        // 检查数据库一致性
        val checkWithEhall = withContext(Dispatchers.IO) {
            spiderClient.checkWithEhall(item.jasdm, weekday, jc, item.zylxdm).awaitSingle()
        }
        if (checkWithEhall.data == true) {
            spiderClient.run()
            val content = "验证一站式平台：数据不一致\n" +
                    "操作方案：更新数据库\n" +
                    "反馈数据详情：$detail"
            EmailUtils.sendAndAwait(
                nickname = "南师教室",
                subject = subject,
                content = content,
            )
            return@future
        }

        // 记录反馈内容
        if (item.zylxdm != "00") {
            val content = "验证一站式平台：数据一致（非空教室）\n" +
                    "操作方案：${null}\n" +
                    "反馈数据详情：$detail"
            EmailUtils.sendAndAwait(
                nickname = "南师教室",
                subject = subject,
                content = content,
            )
            return@future
        } else {
            val map = autoCorrect(
                jxlmc = jxlmc,
                jasdm = item.jasdm,
                jsmph = item.jsmph,
                weekday = weekday,
                jc = jc
            )
            val weekCount = map["weekCount"]!!
            val totalCount = map["totalCount"]!!
            val content = "验证一站式平台：数据一致\n" +
                    "上报计数：${totalCount}\n" +
                    "本周计数：${weekCount}\n" +
                    "操作方案：${if (weekCount == totalCount) null else "自动纠错"}\n" +
                    "反馈数据详情：$detail"
            EmailUtils.sendAndAwait(
                nickname = "南师教室",
                subject = subject,
                content = content,
            )
            return@future
        }
    }

    private fun autoCorrect(
        jxlmc: String,
        jasdm: String,
        jsmph: String,
        weekday: Weekday,
        jc: Short
    ): Map<String, Long> {
        val record = Feedback()
        record.jc = jc
        record.jasdm = jasdm
        record.time = Date()
        feedbackMapper.insert(record)
        val statistic = feedbackDAO.statistic(jasdm, mapDay(weekday), jc)
        val weekCount = statistic.lastOrNull() ?: 0
        val totalCount = statistic.sumOf { it }
        if (weekCount != totalCount)
            correctionMapper.insertSelective(
                Correction().apply {
                    this.weekday = weekday.name
                    this.jxlmc = jxlmc
                    this.jsmph = jsmph
                    this.jasdm = jasdm
                    this.jcKs = jc
                    this.jcJs = jc
                    this.jyytms = "占用"
                    this.kcm = "####占用"
                }
            )
        return mapOf(
            "weekCount" to weekCount,
            "totalCount" to totalCount,
        )
    }

    private fun mapDay(weekday: Weekday) = when (weekday) {
        Weekday.Sun -> 1
        Weekday.Mon -> 2
        Weekday.Tue -> 3
        Weekday.Wed -> 4
        Weekday.Thu -> 5
        Weekday.Fri -> 6
        Weekday.Sat -> 7
    }
}
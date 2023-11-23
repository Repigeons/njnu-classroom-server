package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.SpiderClient
import cn.repigeons.njnu.classroom.commons.service.RedisService
import cn.repigeons.njnu.classroom.commons.utils.EmailUtils
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import cn.repigeons.njnu.classroom.mybatis.model.Correction
import cn.repigeons.njnu.classroom.mybatis.model.Feedback
import cn.repigeons.njnu.classroom.mybatis.model.Timetable
import cn.repigeons.njnu.classroom.mybatis.service.ICorrectionService
import cn.repigeons.njnu.classroom.mybatis.service.IFeedbackService
import cn.repigeons.njnu.classroom.mybatis.service.ITimetableService
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import com.mybatisflex.core.query.QueryWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.future.future
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.getAndAwait
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

@Service
class EmptyClassroomServiceImpl(
    private val spiderClient: SpiderClient,
    private val redisService: RedisService,
    private val cacheService: CacheService,
    private val timetableService: ITimetableService,
    private val feedbackService: IFeedbackService,
    private val correctionService: ICorrectionService,
) : EmptyClassroomService {
    override suspend fun getEmptyClassrooms(jxlmc: String, weekday: Weekday, jc: Int): List<EmptyClassroomVO> {
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

    override fun feedback(
        jxlmc: String,
        weekday: Weekday,
        jc: Int,
        results: List<EmptyClassroomVO>,
        index: Int
    ): CompletableFuture<Unit> = CoroutineScope(Dispatchers.Default).future {
            val item = results[index]

            // 检查缓存一致性
            val count = timetableService.count(
                QueryWrapper()
                    .eq(Timetable::getWeekday, weekday.name)
                    .eq(Timetable::getJcKs, item.jcKs)
                    .eq(Timetable::getJcJs, item.jcJs)
                    .eq(Timetable::getJasdm, item.jasdm)
                    .eq(Timetable::getZylxdm, item.zylxdm)
            )
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
                withContext(Dispatchers.IO) { spiderClient.run().awaitSingle() }
                val content = "验证一站式平台：数据不一致\n" +
                        "操作方案：更新数据库\n" +
                        "反馈数据详情：$detail"
                EmailUtils.send(
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
                EmailUtils.send(
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
                EmailUtils.send(
                    nickname = "南师教室",
                    subject = subject,
                    content = content,
                )
                return@future
            }
        }
    }

    private fun autoCorrect(
        jxlmc: String,
        jasdm: String,
        jsmph: String,
        weekday: Weekday,
        jc: Int
    ): Map<String, Long> {
        val record = Feedback()
        record.jc = jc
        record.jasdm = jasdm
        record.time = LocalDateTime.now()
        feedbackService.save(record)
        val statistic = feedbackService.statistic(jasdm, mapDay(weekday), jc)
        val weekCount = statistic.lastOrNull() ?: 0
        val totalCount = statistic.sumOf { it }
        if (weekCount != totalCount)
            correctionService.save(
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
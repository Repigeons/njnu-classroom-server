package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.commons.redisService.RedisService
import cn.repigeons.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.util.EmailUtil
import cn.repigeons.njnu.classroom.mbg.dao.FeedbackDAO
import cn.repigeons.njnu.classroom.mbg.mapper.CorrectionMapper
import cn.repigeons.njnu.classroom.mbg.mapper.FeedbackMapper
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableMapper
import cn.repigeons.njnu.classroom.mbg.model.Correction
import cn.repigeons.njnu.classroom.mbg.model.Feedback
import cn.repigeons.njnu.classroom.model.EmptyClassroomVO
import cn.repigeons.njnu.classroom.rpc.client.SpiderClient
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
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
    override fun getEmptyClassrooms(jxlmc: String, weekday: Weekday, jc: Short): List<EmptyClassroomVO> {
        if (jc !in 1..12) return emptyList()
        val jxlmcList = cacheService.getBuildingPositions().map { it.name }
        if (jxlmc !in jxlmcList) return emptyList()
        val classrooms = redisService.hGet("core::empty", "$jxlmc:${weekday.name}") as List<*>?
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
        jc: Short,
        results: List<EmptyClassroomVO>,
        index: Int
    ): CompletableFuture<*> = CompletableFuture.supplyAsync {
        val item = results[index]

        // ?????????????????????
        val count = timetableMapper.count {
            it.where(TimetableDynamicSqlSupport.weekday, isEqualTo(weekday.name))
                .and(TimetableDynamicSqlSupport.jcKs, isEqualTo(item.jcKs))
                .and(TimetableDynamicSqlSupport.jcJs, isEqualTo(item.jcJs))
                .and(TimetableDynamicSqlSupport.jasdm, isEqualTo(item.jasdm))
                .and(TimetableDynamicSqlSupport.zylxdm, isEqualTo(item.zylxdm))
        }
        if (count == 0L) {
            cacheService.flushCache()
            return@supplyAsync
        }

        val obj = mapOf(
            "jc" to jc,
            "item" to item,
            "index" to index,
            "results" to results,
        )
        val detail = GsonUtils.toJson(obj)
        val subject = "?????????????????????????????????" +
                "$jxlmc ${item.jsmph}?????? " +
                "${item.jcKs}-${item.jcJs}?????????" +
                "???????????????${jc}??????"

        // ????????????????????????
        if (spiderClient.checkWithEhall(item.jasdm, weekday, jc, item.zylxdm).data == true) {
            spiderClient.run()
            val content = "???????????????????????????????????????\n" +
                    "??????????????????????????????\n" +
                    "?????????????????????$detail"
            EmailUtil.send(
                nickname = "????????????",
                subject = subject,
                content = content,
            )
            return@supplyAsync
        }

        // ??????????????????
        if (item.zylxdm != "00") {
            val content = "??????????????????????????????????????????????????????\n" +
                    "???????????????${null}\n" +
                    "?????????????????????$detail"
            EmailUtil.send(
                nickname = "????????????",
                subject = subject,
                content = content,
            )
            return@supplyAsync
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
            val content = "????????????????????????????????????\n" +
                    "???????????????${totalCount}\n" +
                    "???????????????${weekCount}\n" +
                    "???????????????${if (weekCount == totalCount) null else "????????????"}\n" +
                    "?????????????????????$detail"
            EmailUtil.send(
                nickname = "????????????",
                subject = subject,
                content = content,
            )
            return@supplyAsync
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
                    this.jyytms = "??????"
                    this.kcm = "####??????"
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
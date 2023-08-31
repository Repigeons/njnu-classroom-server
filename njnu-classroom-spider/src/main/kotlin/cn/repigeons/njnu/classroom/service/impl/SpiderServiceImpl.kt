package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.commons.utils.ThreadPoolUtils
import cn.repigeons.njnu.classroom.mbg.dao.KcbDAO
import cn.repigeons.njnu.classroom.mbg.dao.TimetableDAO
import cn.repigeons.njnu.classroom.mbg.mapper.*
import cn.repigeons.njnu.classroom.mbg.model.Jas
import cn.repigeons.njnu.classroom.mbg.model.Kcb
import cn.repigeons.njnu.classroom.mbg.model.Timetable
import cn.repigeons.njnu.classroom.model.JxlInfo
import cn.repigeons.njnu.classroom.model.KcbItem
import cn.repigeons.njnu.classroom.model.TimeInfo
import cn.repigeons.njnu.classroom.rpc.client.CoreClient
import cn.repigeons.njnu.classroom.service.CookieService
import cn.repigeons.njnu.classroom.service.SpiderService
import com.google.gson.JsonParser
import jakarta.annotation.Resource
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.dynamic.sql.SqlBuilder.isEqualTo
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.Lazy
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CompletableFuture

@Service
class SpiderServiceImpl(
    private val redissonClient: RedissonClient,
    private val sqlSessionFactory: SqlSessionFactory,
    private val coreClient: CoreClient,
    @Lazy
    private val cookieService: CookieService,
    private val correctionMapper: CorrectionMapper,
    private val jasMapper: JasMapper,
    private val kcbMapper: KcbMapper,
    private val timetableMapper: TimetableMapper,
    private val kcbDAO: KcbDAO,
    private val timetableDAO: TimetableDAO,
) : SpiderService {
    @Lazy
    @Resource
    private lateinit var `this`: SpiderService
    private val logger = LoggerFactory.getLogger(javaClass)
    private val rqFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.PRC)
    private lateinit var httpClient: OkHttpClient

    @Scheduled(cron = "0 0 8 * * ?")
    override fun run(): CompletableFuture<Void> = ThreadPoolUtils.runAsync {
        val lock = redissonClient.getLock("lock::spider")
        if (!lock.tryLock()) {
            logger.info("课程信息收集工作已处于运行中...")
            return@runAsync
        }
        try {
            logger.info("开始课程信息收集工作...")
            this.actRun()
            coreClient.flushCache()
        } finally {
            lock.unlock()
        }
    }

    private fun actRun() {
        val startTime = System.currentTimeMillis()
        val cookies = cookieService.getCookies()
        httpClient = cookieService.getHttpClient(cookies)
        logger.info("开始采集基础信息...")
        val timeInfo = `this`.getTimeInfo()
        val jxlInfoList = `this`.getJxlInfo()
        logger.info("基础信息采集完成.")

        logger.info("开始采集课程信息...")
        kcbDAO.truncate()
        jxlInfoList.forEach { jxlInfo ->
            val classroomList = `this`.getClassrooms(jxlInfo.jxldm)
            logger.info("开始查询教学楼[{}]...", jxlInfo.jxlmc)
            classroomList.map { classroom ->
                logger.debug("正在查询教室[{}]...", classroom.jasmc)
                getClassInfo(classroom, timeInfo)
            }.forEach { future -> future.join() }
        }
        logger.info("课程信息采集完成.")
        timetableDAO.truncate()
        timetableDAO.cloneFromKcb()
        logger.info("开始校正数据...")
        correctData()
        logger.info("数据校正完成...")
        logger.info("开始归并数据...")
        mergeData()
        logger.info("数据归并完成...")
        val endTime = System.currentTimeMillis()
        logger.info("本轮课程信息收集工作成功完成. 共计耗时 {} 秒", (endTime - startTime) / 1000)
    }

    @Cacheable("time-info")
    override fun getTimeInfo(): TimeInfo {
        val timeInfo = TimeInfo()
        val request1 = Request.Builder()
            .url("https://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxdqxnxq.do")
            .build()
        val response1 = httpClient.newCall(request1).execute()
        val result1 = response1.body?.string()
        val data1 = JsonParser.parseString(result1)
            .asJsonObject
            .getAsJsonObject("datas")
            .getAsJsonObject("cxdqxnxq")
            .getAsJsonArray("rows")
            .get(0)
            .asJsonObject
        timeInfo.XNXQDM = data1.get("DM").asString
        timeInfo.XNDM = data1.get("XNDM").asString
        timeInfo.XQDM = data1.get("XQDM").asString

        val requestBody2 = FormBody.Builder()
            .add("XN", data1.get("XNDM").asString)
            .add("XQ", data1.get("XQDM").asString)
            .add("RQ", rqFormatter.format(LocalDateTime.now()))
            .build()
        val request2 = Request.Builder()
            .url("https://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxrqdydzcxq.do")
            .post(requestBody2)
            .build()
        val response2 = httpClient.newCall(request2).execute()
        val result2 = response2.body?.string()
        val data2 = JsonParser.parseString(result2)
            .asJsonObject
            .getAsJsonObject("datas")
            .getAsJsonObject("cxrqdydzcxq")
            .getAsJsonArray("rows")
            .get(0)
            .asJsonObject
        timeInfo.ZC = data2.get("ZC").asString.toInt()
        timeInfo.ZZC = data2.get("ZZC").asString.toInt()

        val requestBody3 = FormBody.Builder()
            .add("XN", data1.get("XNDM").asString)
            .add("XQ", data1.get("XQDM").asString)
            .add("RQ", rqFormatter.format(LocalDateTime.now()))
            .build()
        val request3 = Request.Builder()
            .url("https://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxxljc.do")
            .post(requestBody3)
            .build()
        val response3 = httpClient.newCall(request3).execute()
        val result3 = response3.body?.string()
        val data3 = JsonParser.parseString(result3)
            .asJsonObject
            .getAsJsonObject("datas")
            .getAsJsonObject("cxxljc")
            .getAsJsonArray("rows")
            .get(0)
            .asJsonObject
        timeInfo.ZJXZC = data3.get("ZJXZC").asString.toInt()
        return timeInfo
    }

//    @Cacheable("jxl-info")
//    override fun getJxlInfo(): List<JxlInfo> {
//        val jasList = jasMapper.select { it }
//            .groupBy { it.jxldmDisplay }
//        jasList.values.forEach { jasRecords ->
//            jasRecords as MutableList
//            jasRecords.sortBy { it.jasmc }
//        }
//        return jasList.map { (jxlmc, jasRecords) ->
//            jxlmc to jasRecords.sortedBy { it.jasmc }
//        }.toTypedArray().let { mapOf(*it) }
//    }

    @Cacheable("jxl-info")
    override fun getJxlInfo(): List<JxlInfo> {
        val map = jasMapper.select { it }
            .groupBy { it.jxldm }
        return map.map { (_, list) ->
            val item = list.first()
            JxlInfo(
                jxldm = item.jxldm,
                jxlmc = item.jxldmDisplay,
                xxxqdm = item.xxxqdm,
                xxxqmc = item.xxxqdmDisplay
            )
        }
    }

    @Cacheable("classroom-info", key = "#jxldm")
    override fun getClassrooms(jxldm: String): List<Jas> {
        return jasMapper.select {
            it.where(
                JasDynamicSqlSupport.jxldm, isEqualTo(jxldm)
            )
        }
    }

    private fun getClassInfo(classroom: Jas, timeInfo: TimeInfo): CompletableFuture<Void> =
        ThreadPoolUtils.runAsync {
            val thisWeek = timeInfo.ZC
            val nextWeek = if (timeInfo.ZC < timeInfo.ZJXZC) timeInfo.ZC + 1 else timeInfo.ZJXZC
            val kcb = getKcb(timeInfo.XNXQDM, thisWeek.toString(), classroom.jasdm)
            if (nextWeek != thisWeek) {
                val kcb2 = getKcb(timeInfo.XNXQDM, nextWeek.toString(), classroom.jasdm)
                val weekday = (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 4) % 7
                for (day in 0..weekday) {
                    kcb[day] = kcb2[day]
                }
            }
            for (day in 0..6) {
                kcb[day].forEach { kcbItem ->
                    val jc = kcbItem.JC.split(',')
                    val kcbRecord = Kcb().apply {
                        jxlmc = classroom.jxldmDisplay
                        jsmph = classroom.jasmc?.replace(Regex("^${classroom.jxldmDisplay}"), "")?.trim()
                        jasdm = classroom.jasdm
                        skzws = classroom.skzws
                        zylxdm = kcbItem.ZYLXDM.ifEmpty { "00" }
                        jcKs = jc.firstOrNull()?.toShort()
                        jcJs = jc.lastOrNull()?.toShort()
                        weekday = Weekday[day].name
                        sfyxzx = classroom.sfyxzx
                        jyytms = if (kcbItem.JYYTMS.isNullOrEmpty()) "" else kcbItem.JYYTMS
                        kcm = kcbItem.KCM ?: kcbItem.KBID?.let { "研究生[$it]" } ?: "未知"
                    }
                    kcbMapper.insert(kcbRecord)
                }
            }
        }

    private fun getKcb(xnxqdm: String, week: String, jasdm: String): MutableList<List<KcbItem>> {
        val requestBody = FormBody.Builder()
            .add("XNXQDM", xnxqdm)
            .add("ZC", week)
            .add("JASDM", jasdm)
            .build()
        val request = Request.Builder()
            .url("https://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxyzjskjyqk.do")
            .post(requestBody)
            .build()
        val response = httpClient.newCall(request).execute()
        val result = response.body?.string()
        val by1 = JsonParser.parseString(result)
            .asJsonObject
            .getAsJsonObject("datas")
            .getAsJsonObject("cxyzjskjyqk")
            .getAsJsonArray("rows")
            .get(0)
            .asJsonObject
            .get("BY1")
            .asString
        return GsonUtils.fromJson(by1)
    }

    private fun correctData() {
        val corrections = correctionMapper.select { it }
        logger.debug("Corrections = {}", corrections)

        corrections.forEach { correction ->
            if (correction.jcKs < correction.jcJs) {
                for (jc in correction.jcKs until correction.jcJs)
                    timetableMapper.delete {
                        it.where(TimetableDynamicSqlSupport.weekday, isEqualTo(correction.weekday))
                            .and(TimetableDynamicSqlSupport.jasdm, isEqualTo(correction.jasdm))
                            .and(TimetableDynamicSqlSupport.jcKs, isEqualTo(jc.toShort()))
                            .and(TimetableDynamicSqlSupport.jcJs, isEqualTo(jc.toShort()))
                    }
            }
            timetableMapper.update {
                it.set(TimetableDynamicSqlSupport.skzws).equalTo(correction.skzws)
                    .set(TimetableDynamicSqlSupport.zylxdm).equalTo(correction.zylxdm)
                    .set(TimetableDynamicSqlSupport.jyytms).equalTo(correction.jyytms)
                    .set(TimetableDynamicSqlSupport.kcm).equalTo(correction.kcm)
                    .set(TimetableDynamicSqlSupport.jcKs).equalTo(correction.jcKs)
                    .where(TimetableDynamicSqlSupport.weekday, isEqualTo(correction.weekday))
                    .and(TimetableDynamicSqlSupport.jasdm, isEqualTo(correction.jasdm))
                    .and(TimetableDynamicSqlSupport.jcKs, isEqualTo(correction.jcJs))
                    .and(TimetableDynamicSqlSupport.jcJs, isEqualTo(correction.jcJs))
            }
        }
    }

    private fun mergeData() {
        val data = timetableMapper.select {
            it.orderBy(
                TimetableDynamicSqlSupport.weekday,
                TimetableDynamicSqlSupport.jxlmc,
                TimetableDynamicSqlSupport.jsmph,
                TimetableDynamicSqlSupport.jcJs,
            )
        }.groupBy { it.jxlmc }
        val result = mutableMapOf<String, MutableList<Timetable>>()
        data.map { (jxlmc, records) ->
            mergeJxl(jxlmc, records, result)
        }.forEach { future -> future.join() }
        // 清空数据库
        timetableDAO.truncate()
        // 重新插入数据库
        val sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false)
        val timetableMapper = sqlSession.getMapper(TimetableMapper::class.java)
        result.forEach { (jxlmc, records) ->
            records.forEach { record ->
                timetableMapper.insert(record)
            }
            sqlSession.commit()
            logger.info("[{}] 归并完成.", jxlmc)
        }
        sqlSession.clearCache()
    }

    private fun mergeJxl(
        jxlmc: String,
        records: List<Timetable>,
        result: MutableMap<String, MutableList<Timetable>>
    ): CompletableFuture<Void> = ThreadPoolUtils.runAsync {
        logger.info("[{}] 开始归并...", jxlmc)
        val classrooms = mutableListOf<Timetable>()
        result[jxlmc] = classrooms
        records.forEach { record ->
            if (classrooms.isNotEmpty()
                && record.jasdm == classrooms.last().jasdm
                && record.zylxdm == "00"
                && classrooms.last().zylxdm == "00"
            )
                classrooms.last().jcJs = record.jcJs
            else
                classrooms.add(record)
        }
    }

    override fun checkWithEhall(jasdm: String, weekday: Weekday, jc: Short, zylxdm: String): Boolean {
        val cookies = cookieService.getCookies()
        httpClient = cookieService.getHttpClient(cookies)
        val timeInfo = `this`.getTimeInfo()
        val kcb = getKcb(timeInfo.XNXQDM, timeInfo.ZC.toString(), jasdm)
        kcb[weekday.ordinal].forEach {
            val bool1 = jc.toString() in it.JC.split(',')
            val bool2 = it.ZYLXDM == zylxdm || it.ZYLXDM.isEmpty()
            if (bool1 && bool2) return true
        }
        return false
    }
}
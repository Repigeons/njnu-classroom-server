package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.CoreClient
import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.model.bo.JxlInfo
import cn.repigeons.njnu.classroom.model.bo.KcbItem
import cn.repigeons.njnu.classroom.model.bo.TimeInfo
import cn.repigeons.njnu.classroom.mybatis.model.Jas
import cn.repigeons.njnu.classroom.mybatis.model.Kcb
import cn.repigeons.njnu.classroom.mybatis.model.Timetable
import cn.repigeons.njnu.classroom.mybatis.service.ICorrectionService
import cn.repigeons.njnu.classroom.mybatis.service.IJasService
import cn.repigeons.njnu.classroom.mybatis.service.IKcbService
import cn.repigeons.njnu.classroom.mybatis.service.ITimetableService
import cn.repigeons.njnu.classroom.service.CookieService
import cn.repigeons.njnu.classroom.service.SpiderService
import cn.repigeons.njnu.classroom.utils.SpiderThreadPool
import com.google.gson.JsonParser
import com.mybatisflex.core.query.QueryWrapper
import jakarta.annotation.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
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
import kotlin.random.Random

@Service
class SpiderServiceImpl(
    private val redissonClient: RedissonClient,
    private val coreClient: CoreClient,
    @Lazy
    private val cookieService: CookieService,
    private val correctionService: ICorrectionService,
    private val jasService: IJasService,
    private val kcbService: IKcbService,
    private val timetableService: ITimetableService,
) : SpiderService {
    @Lazy
    @Resource
    private lateinit var `this`: SpiderService
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var httpClient: OkHttpClient

    @Scheduled(cron = "0 0 8 * * ?")
    override fun run(): CompletableFuture<Unit> = CoroutineScope(Dispatchers.Default).future {
        val lock = redissonClient.getLock("lock::spider")
        if (!lock.tryLock()) {
            logger.info("课程信息收集工作已处于运行中...")
            return@future
        }
        try {
            logger.info("开始课程信息收集工作...")
            actRun()
            withContext(Dispatchers.IO) { coreClient.flushCache() }
        } catch (e: Exception) {
            logger.error("课程信息收集失败: {}", e.message, e)
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
        kcbService.truncate()
        jxlInfoList.forEach { jxlInfo ->
            val classroomList = `this`.getClassrooms(jxlInfo.jxldm)
            logger.info("开始查询教学楼[{}]...", jxlInfo.jxlmc)
            classroomList.map { classroom ->
                logger.debug("正在查询教室[{}]...", classroom.jasmc)
                getClassInfo(classroom, timeInfo)
            }.forEach { future -> future.join() }
        }
        logger.info("课程信息采集完成.")
        timetableService.truncate()
        timetableService.cloneFromKcb()
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
            .url("http://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxdqxnxq.do")
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
            .add("RQ", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .build()
        val request2 = Request.Builder()
            .url("http://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxrqdydzcxq.do")
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
            .add("RQ", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
            .build()
        val request3 = Request.Builder()
            .url("http://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxxljc.do")
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

    @Cacheable("jxl-info")
    override fun getJxlInfo(): List<JxlInfo> {
        val map = jasService.list()
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
        return jasService.list(
            QueryWrapper()
                .eq(Jas::getJxldm, jxldm)
        )
    }

    private fun getClassInfo(classroom: Jas, timeInfo: TimeInfo): CompletableFuture<Void> =
        SpiderThreadPool.runAsync {
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
                val records = kcb[day].map { kcbItem ->
                    val jc = kcbItem.JC.split(',')
                    Kcb().apply {
                        jxlmc = classroom.jxldmDisplay
                        jsmph = classroom.jasmc?.replace(Regex("^${classroom.jxldmDisplay}"), "")?.trim()
                        jasdm = classroom.jasdm
                        skzws = classroom.skzws
                        zylxdm = kcbItem.ZYLXDM.ifEmpty { "00" }
                        jcKs = jc.firstOrNull()?.toInt()
                        jcJs = jc.lastOrNull()?.toInt()
                        weekday = Weekday[day].name
                        sfyxzx = classroom.sfyxzx
                        jyytms = if (kcbItem.JYYTMS.isNullOrEmpty()) "" else kcbItem.JYYTMS
                        kcm = kcbItem.KCM ?: kcbItem.KBID?.let { "研究生[$it]" } ?: "未知"
                    }
                }
                kcbService.saveBatch(records)
            }
        }

    private fun getKcb(xnxqdm: String, week: String, jasdm: String): MutableList<List<KcbItem>> {
        Thread.sleep(Random.nextLong(100, 500))
        val requestBody = FormBody.Builder()
            .add("XNXQDM", xnxqdm)
            .add("ZC", week)
            .add("JASDM", jasdm)
            .build()
        val request = Request.Builder()
            .url("http://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/modules/jsjysq/cxyzjskjyqk.do")
            .post(requestBody)
            .build()
        try {
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
        } catch (e: Exception) {
            logger.error("查询课程表失败：{}, {}", jasdm, e.message, e)
            return mutableListOf()
        }
    }

    private fun correctData() {
        val corrections = correctionService.list()
        logger.debug("Corrections = {}", corrections)

        corrections.forEach { correction ->
            if (correction.jcKs < correction.jcJs) {
                for (jc in correction.jcKs until correction.jcJs) {
                    timetableService.remove(
                        QueryWrapper()
                            .eq(Timetable::getWeekday, correction.weekday)
                            .eq(Timetable::getJasdm, correction.jasdm)
                            .eq(Timetable::getJcKs, Timetable::getJcJs)
                            .between(Timetable::getJcKs, correction.jcKs, correction.jcJs - 1)
                            .eq(Timetable::getJcJs, jc.toShort())
                    )
                }
            }
            timetableService.updateChain()
                .set(Timetable::getSkzws, correction.skzws)
                .set(Timetable::getZylxdm, correction.zylxdm)
                .set(Timetable::getJyytms, correction.jyytms)
                .set(Timetable::getKcm, correction.kcm)
                .set(Timetable::getJcKs, correction.jcKs)
                .eq(Timetable::getWeekday, correction.weekday)
                .eq(Timetable::getJasdm, correction.jasdm)
                .eq(Timetable::getJcKs, correction.jcJs)
                .eq(Timetable::getJcJs, correction.jcJs)
                .update()
        }
    }

    private fun mergeData() {
        val data = timetableService.list(
            QueryWrapper()
                .orderBy(Timetable::getWeekday).asc()
                .orderBy(Timetable::getJxlmc).asc()
                .orderBy(Timetable::getJsmph).asc()
                .orderBy(Timetable::getJcJs).asc()
        ).groupBy { it.jxlmc }
        val result = mutableMapOf<String, MutableList<Timetable>>()
        data.map { (jxlmc, records) ->
            mergeJxl(jxlmc, records, result)
        }.forEach { future -> future.join() }
        // 清空数据库
        timetableService.truncate()
        // 重新插入数据库
        result.values.forEach(timetableService::saveBatch)
    }

    private fun mergeJxl(
        jxlmc: String,
        records: List<Timetable>,
        result: MutableMap<String, MutableList<Timetable>>
    ): CompletableFuture<Void> = SpiderThreadPool.runAsync {
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
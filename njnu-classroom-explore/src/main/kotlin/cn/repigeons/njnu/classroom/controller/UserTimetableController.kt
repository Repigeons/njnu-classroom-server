package cn.repigeons.njnu.classroom.controller


import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.model.RequestPayload
import cn.repigeons.njnu.classroom.commons.util.JwtUtil
import cn.repigeons.njnu.classroom.mbg.mapper.*
import cn.repigeons.njnu.classroom.mbg.model.UserTimetable
import cn.repigeons.njnu.classroom.model.UserTimetableDTO
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrElse

/**
 * 用户时间表
 */
@RestController
@RequestMapping("user")
class UserTimetableController(
    private val userTimetableMapper: UserTimetableMapper
) {
    /**
     * 查询用户时间表
     */
    @GetMapping("timetable")
    fun getTimeTable(
        @RequestHeader("Authorization") token: String
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val records = userTimetableMapper.select {
            it.where(UserTimetableDynamicSqlSupport.openid, isEqualTo(openid))
        }
        val data = records.map { record ->
            UserTimetableDTO(
                id = record.id,
                weekday = Weekday.valueOf(record.weekday),
                jcKs = record.ksjc,
                jcJs = record.jsjc,
                place = record.place,
                remark = GsonUtils.fromJson(record.remark)
            )
        }
        return CommonResponse.success(data)
    }

    /**
     * 新增/修改用户时间表
     */
    @PostMapping("timetable")
    fun saveTimetable(
        @RequestHeader("Authorization") token: String,
        @RequestBody payload: UserTimetableDTO
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val record = UserTimetable().apply {
            this.id = payload.id
            this.openid = openid
            this.weekday = payload.weekday.name
            this.ksjc = payload.jcKs
            this.jsjc = payload.jcJs
            this.place = payload.place
            this.remark = GsonUtils.toJson(payload.remark)
        }
        if (record.id == null)
            userTimetableMapper.insert(record)
        else
            userTimetableMapper.updateByPrimaryKey(record)
        return CommonResponse.success(
            mapOf(
                "id" to record.id
            )
        )
    }

    /**
     * 删除用户时间表
     */
    @DeleteMapping("timetable")
    fun deleteTimetable(
        @RequestHeader("Authorization") token: String,
        @RequestBody payload: RequestPayload<Long>
    ): CommonResponse<*> {
        val openid = token2openid(token)
            ?: return CommonResponse.unauthorized()
        val id = requireNotNull(payload["id"]) { "请求参数缺失：id" }
        val record = userTimetableMapper.selectByPrimaryKey(id)
            .getOrElse { return CommonResponse.failed("记录不存在") }
        if (record.openid != openid)
            return CommonResponse.forbidden()
        userTimetableMapper.deleteByPrimaryKey(id)
        return CommonResponse.success()
    }

    private fun token2openid(token: String) =
        token.takeIf { token.startsWith(JwtUtil.TOKEN_HEAD) }
            ?.let { JwtUtil.parse(it) }
            ?.subject
}
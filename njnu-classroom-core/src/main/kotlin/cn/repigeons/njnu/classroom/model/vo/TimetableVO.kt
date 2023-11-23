package cn.repigeons.njnu.classroom.model.vo

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.mybatis.model.Timetable
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "时间表")
data class TimetableVO(
    val id: Int,
    @Schema(description = "教学楼名称")
    val jxlmc: String,
    @Schema(description = "教室门牌号")
    val jsmph: String,
    @Schema(description = "上课座位数")
    val skzws: Int,
    @Schema(description = "星期")
    val weekday: Weekday,
    @Schema(description = "开始节次")
    val jcKs: Int,
    @Schema(description = "结束节次")
    val jcJs: Int,
    @Schema(description = "资源类型代码")
    val zylxdm: String,
    @Schema(description = "借用用途说明")
    val jyytms: String,
    @Schema(description = "课程名")
    val kcm: String,
) {
    constructor(record: Timetable) : this(
        id = record.id,
        jxlmc = record.jxlmc,
        jsmph = record.jsmph,
        skzws = record.skzws,
        weekday = Weekday.valueOf(record.weekday),
        jcKs = record.jcKs,
        jcJs = record.jcJs,
        zylxdm = record.zylxdm,
        jyytms = record.jyytms,
        kcm = record.kcm,
    )
}
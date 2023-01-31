package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.mbg.model.Timetable

data class TimetableVO(
    /**
     * 教学楼名称
     */
    val jxlmc: String,
    /**
     * 教室门牌号
     */
    val jsmph: String,
    /**
     * 上课座位数
     */
    val skzws: Int,
    /**
     * 星期
     */
    val weekday: Weekday,
    /**
     * 开始节次
     */
    val jcKs: Short,
    /**
     * 结束节次
     */
    val jcJs: Short,
    /**
     * 资源类型代码
     */
    val zylxdm: String,
    /**
     * 借用用途说明
     */
    val jyytms: String,
    /**
     * 课程名
     */
    val kcm: String,
) {
    constructor(record: Timetable) : this(
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
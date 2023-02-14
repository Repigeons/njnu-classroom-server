package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.mbg.model.Timetable

data class EmptyClassroomVO(
    val id: Int,
    /**
     * 教室代码
     */
    val jasdm: String,
    /**
     * 教室门牌号
     */
    val jsmph: String,
    /**
     * 上课座位数
     */
    val skzws: Int,
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
) {
    constructor(record: Timetable) : this(
        id = record.id,
        jasdm = record.jasdm,
        jsmph = record.jsmph,
        skzws = record.skzws,
        jcKs = record.jcKs,
        jcJs = record.jcJs,
        zylxdm = record.zylxdm,
    )
}
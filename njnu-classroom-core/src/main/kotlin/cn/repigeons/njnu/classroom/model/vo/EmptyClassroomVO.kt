package cn.repigeons.njnu.classroom.model.vo

import cn.repigeons.njnu.classroom.mbg.model.Timetable
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "空教室信息")
data class EmptyClassroomVO(
    val id: Int,
    @Schema(description = "教室代码")
    val jasdm: String,
    @Schema(description = "教室门牌号")
    val jsmph: String,
    @Schema(description = "上课座位数")
    val skzws: Int,
    @Schema(description = "开始节次")
    val jcKs: Short,
    @Schema(description = "结束节次")
    val jcJs: Short,
    @Schema(description = "资源类型代码")
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
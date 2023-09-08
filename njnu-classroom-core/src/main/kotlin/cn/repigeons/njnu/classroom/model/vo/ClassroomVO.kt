package cn.repigeons.njnu.classroom.model.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "教室信息")
data class ClassroomVO(
    @Schema(description = "教学楼名称")
    val jxlmc: String,
    @Schema(description = "教室列表")
    val list: List<ClassroomItemVO>
) {
    data class ClassroomItemVO(
        @Schema(description = "教学楼名称")
        /**
         * 教学楼名称
         */
        val jxlmc: String,
        @Schema(description = "教室门牌号")
        val jsmph: String?,
        @Schema(description = "教室代码")
        val jasdm: String,
    )
}
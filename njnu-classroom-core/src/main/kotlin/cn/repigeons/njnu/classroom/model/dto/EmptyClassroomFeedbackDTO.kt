package cn.repigeons.njnu.classroom.model.dto

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "空教室反馈")
data class EmptyClassroomFeedbackDTO(
    @Schema(description = "星期")
    val weekday: Weekday,
    @Schema(description = "节次")
    val jc: Short,
    @Schema(description = "教学楼名称")
    val jxlmc: String,
    @Schema(description = "查询结果集")
    val results: List<EmptyClassroomVO>,
    @Schema(description = "异常结果索引")
    val index: Int,
)
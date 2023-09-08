package cn.repigeons.njnu.classroom.model.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "教学楼位置")
data class PositionVO(
    @Schema(description = "教学楼名称")
    val name: String,
    @Schema(description = "经纬度")
    val position: List<Float>,
)
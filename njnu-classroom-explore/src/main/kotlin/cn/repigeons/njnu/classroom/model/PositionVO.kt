package cn.repigeons.njnu.classroom.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "校车站位置")
data class PositionVO(
    @Schema(description = "车站名称")
    val name: String,
    @Schema(description = "经纬度")
    val position: List<Float>,
)

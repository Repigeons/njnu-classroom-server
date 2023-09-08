package cn.repigeons.njnu.classroom.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "校车时刻表")
data class ShuttleRoute(
    @Schema(description = "发车时间")
    val startTime: String,
    @Schema(description = "起点站")
    val startStation: String,
    @Schema(description = "终点站")
    val endStation: String,
)
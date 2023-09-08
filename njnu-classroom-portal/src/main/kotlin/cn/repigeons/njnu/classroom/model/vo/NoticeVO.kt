package cn.repigeons.njnu.classroom.model.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "公告")
data class NoticeVO(
    @Schema(description = "公告id")
    val id: Int = 0,
    @Schema(description = "公告时间")
    val timestamp: Long = 0,
    @Schema(description = "公告日期")
    val date: String = "",
    @Schema(description = "公告内容")
    val text: String = "",
)
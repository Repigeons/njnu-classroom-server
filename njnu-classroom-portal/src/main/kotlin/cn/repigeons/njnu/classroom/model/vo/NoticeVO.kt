package cn.repigeons.njnu.classroom.model.vo

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Schema(description = "公告")
data class NoticeVO(
    @Schema(description = "公告id")
    val id: Int,
    @Schema(description = "公告时间")
    val timestamp: Long,
    @Schema(description = "公告日期")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val date: LocalDateTime,
    @Schema(description = "公告内容")
    val text: String,
)
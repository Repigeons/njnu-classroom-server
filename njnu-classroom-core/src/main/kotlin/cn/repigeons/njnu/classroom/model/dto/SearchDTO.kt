package cn.repigeons.njnu.classroom.model.dto

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.RequestParam

@Schema(description = "查询参数")
data class SearchDTO(
    @Schema(description = "星期")
    @RequestParam(required = false)
    val weekday: Weekday?,
    @Schema(description = "开始节次")
    @RequestParam(defaultValue = "1")
    val jcKs: Short,
    @Schema(description = "结束")
    @RequestParam(defaultValue = "12")
    val jcJs: Short,
    @Schema(description = "教学楼名称")
    @RequestParam(defaultValue = "")
    val jxlmc: String,
    @Schema(description = "资源类型代码")
    @RequestParam(defaultValue = "")
    val zylxdm: String,
    @Schema(description = "关键词")
    @RequestParam(defaultValue = "")
    val keyword: String,
    @Schema(description = "分页页号")
    @RequestParam(defaultValue = "1")
    val page: Int,
    @Schema(description = "分页大小")
    @RequestParam(defaultValue = "10")
    val size: Int,
)
package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "用户收藏")
data class UserFavoritesDTO(
    val id: Long?,
    @Schema(description = "标题")
    val title: String,
    @Schema(description = "星期")
    val weekday: Weekday,
    @Schema(description = "开始节次")
    val jcKs: Short,
    @Schema(description = "结束节次")
    val jcJs: Short,
    @Schema(description = "地点")
    val place: String,
    @Schema(description = "颜色")
    val color: String,
    @Schema(description = "备注")
    val remark: Map<String, *>,
)
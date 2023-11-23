package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.mybatis.model.Grids
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "发现栏")
data class GridVO(
    @Schema(description = "名称")
    val text: String,
    @Schema(description = "图表")
    val imgUrl: String,
    @Schema(description = "跳转目标页面")
    val url: String?,
    @Schema(description = "点击执行方法")
    val method: String?,
    @Schema(description = "开放方法")
    val button: Map<*, *>?,
) {
    constructor(record: Grids) : this(
        text = record.text,
        imgUrl = record.imgUrl,
        url = record.url,
        method = record.method,
        button = record.button?.let { GsonUtils.fromJson<Map<*, *>>(it) },
    )
}
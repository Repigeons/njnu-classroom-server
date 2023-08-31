package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.mbg.model.Grids

data class GridVO(
    val text: String,
    val imgUrl: String,
    val url: String?,
    val method: String?,
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
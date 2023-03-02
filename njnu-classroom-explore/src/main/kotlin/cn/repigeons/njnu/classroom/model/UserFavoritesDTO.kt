package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday

data class UserFavoritesDTO(
    /**
     * 记录id
     */
    val id: Long?,
    /**
     * 标题
     */
    val title: String,
    /**
     * 星期
     */
    val weekday: Weekday,
    /**
     * 开始节次
     */
    val jcKs: Short,
    /**
     * 结束节次
     */
    val jcJs: Short,
    /**
     * 地点
     */
    val place: String,
    /**
     * 颜色
     */
    val color: String,
    /**
     * 备注
     */
    val remark: Map<String, *>
)
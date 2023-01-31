package cn.repigeons.njnu.classroom.model

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday

data class EmptyClassroomFeedbackDTO(
    /**
     * 星期
     */
    val weekday: Weekday,
    /**
     * 节次
     */
    val jc: Short,
    /**
     * 教学楼名称
     */
    val jxlmc: String,
    /**
     * 查询结果集
     */
    val results: List<EmptyClassroomVO>,
    /**
     * 异常结果索引
     */
    val index: Int,
)
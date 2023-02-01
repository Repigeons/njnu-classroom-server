package cn.repigeons.njnu.classroom.model

data class ShuttleRoute(
    /**
     * 发车时间
     */
    val startTime: String,
    /**
     * 起点站
     */
    val startStation: String,
    /**
     * 终点站
     */
    val endStation: String
)
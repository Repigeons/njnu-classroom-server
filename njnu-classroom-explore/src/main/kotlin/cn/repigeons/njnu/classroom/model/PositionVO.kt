package cn.repigeons.njnu.classroom.model

data class PositionVO(
    /**
     * 车站名称
     */
    val name: String,
    /**
     * 经纬度
     */
    val position: List<Float>,
)

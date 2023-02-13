package cn.repigeons.njnu.classroom.model

data class ClassroomVO(
    /**
     * 教学楼名称
     */
    val jxlmc: String,
    /**
     * 教室列表
     */
    val list: List<ClassroomItemVO>
) {
    data class ClassroomItemVO(
        /**
         * 教学楼名称
         */
        val jxlmc: String,
        /**
         * 教室门牌号
         */
        val jsmph: String?,
        /**
         * 教室代码
         */
        val jasdm: String,
    )
}
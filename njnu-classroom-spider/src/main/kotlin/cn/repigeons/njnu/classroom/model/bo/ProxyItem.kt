package cn.repigeons.njnu.classroom.model.bo

data class ProxyItem(
    val https: Boolean,
    val proxy: String,
    val region: String,
    val source: String,
)
package cn.repigeons.njnu.classroom.commons.httpTrace

data class HttpTraceLog(
    val path: String,
    val method: String,
    val status: Int,
    val time: String,
    val timeTaken: Long,
    val parameter: Map<String, List<String>>,
    val requestBody: String,
    val responseBody: String,
)
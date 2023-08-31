package cn.repigeons.njnu.classroom.commons.httpTrace

data class HttpTraceLog(
    val path: String,
    val parameterMap: Map<String, List<String>>,
    val method: String,
    val timeTaken: Long,
    val time: String,
    val status: Int,
    val requestBody: String,
    val responseBody: String,
)
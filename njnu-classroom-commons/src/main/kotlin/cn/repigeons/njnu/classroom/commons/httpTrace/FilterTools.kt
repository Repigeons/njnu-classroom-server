package cn.repigeons.njnu.classroom.commons.httpTrace

import org.springframework.http.MediaType

internal val REQUEST_CONTENT_TYPE: Array<MediaType?> = arrayOf(
    MediaType.APPLICATION_JSON,
    MediaType.APPLICATION_FORM_URLENCODED,
)
internal val RESPONSE_CONTENT_TYPE: Array<MediaType?> = arrayOf(
    MediaType.APPLICATION_JSON,
    null,
)

internal fun filterLog(requestPath: String): Boolean {
    return requestPath.endsWith("/actuator/health")
}

internal fun String.formatLog(): String {
    val maxLength = 10_000
    return if (length > maxLength) {
        substring(0, maxLength) + "......"
    } else this
}

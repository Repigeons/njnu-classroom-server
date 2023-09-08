package cn.repigeons.njnu.classroom.commons.api

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus

data class CommonResult<T : Any>
internal constructor(
    @Schema(description = "状态码", defaultValue = "200", required = true, nullable = false)
    val status: Int,
    @Schema(description = "错误信息", required = true, nullable = true)
    val message: String,
    @Schema(required = true, nullable = true)
    val data: T?,
) {
    constructor(httpStatus: HttpStatus, data: T?) : this(httpStatus.value(), httpStatus.reasonPhrase, data)
    constructor(httpStatus: HttpStatus, message: String, data: T?) : this(httpStatus.value(), message, data)

    companion object {
        @JvmStatic
        @JvmName("successNothing")
        fun success(): CommonResult<Nothing> =
            CommonResult(HttpStatus.OK, null)

        @JvmStatic
        @JvmName("successT")
        inline fun <reified T : Any> success(): CommonResult<T> =
            CommonResult(HttpStatus.OK, null)

        @JvmStatic
        @JvmName("successData")
        inline fun <reified T : Any> success(data: T?): CommonResult<T> =
            CommonResult(HttpStatus.OK, data)

        @JvmStatic
        @JvmName("failedNothing")
        fun failed(): CommonResult<Nothing> =
            CommonResult(HttpStatus.INTERNAL_SERVER_ERROR, null)

        @JvmStatic
        @JvmName("failedT")
        inline fun <reified T : Any> failed(): CommonResult<T> =
            CommonResult(HttpStatus.INTERNAL_SERVER_ERROR, null)

        @JvmStatic
        @JvmName("failedMessage")
        inline fun <reified T : Any> failed(message: String): CommonResult<T> =
            CommonResult(HttpStatus.INTERNAL_SERVER_ERROR, message, null)

        @JvmStatic
        @JvmName("failedNothingMessage")
        fun failed(message: String): CommonResult<Nothing> =
            CommonResult(HttpStatus.INTERNAL_SERVER_ERROR, message, null)

        @JvmStatic
        @JvmName("unauthorizedNothing")
        fun unauthorized(): CommonResult<Nothing> = CommonResult(HttpStatus.UNAUTHORIZED, null)

        @JvmStatic
        @JvmName("unauthorizedT")
        inline fun <reified T : Any> unauthorized(): CommonResult<T> = CommonResult(HttpStatus.UNAUTHORIZED, null)

        @JvmStatic
        @JvmName("forbiddenNothing")
        fun forbidden(): CommonResult<Nothing> = CommonResult(HttpStatus.FORBIDDEN, null)

        @JvmStatic
        @JvmName("forbiddenT")
        inline fun <reified T : Any> forbidden(): CommonResult<T> = CommonResult(HttpStatus.FORBIDDEN, null)
    }
}
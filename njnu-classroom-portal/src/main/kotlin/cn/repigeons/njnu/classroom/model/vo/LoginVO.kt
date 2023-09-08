package cn.repigeons.njnu.classroom.model.vo

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "登录信息")
data class LoginVO(
    val token: String,
)
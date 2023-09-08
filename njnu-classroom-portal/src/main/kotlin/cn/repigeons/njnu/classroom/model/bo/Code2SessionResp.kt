package cn.repigeons.njnu.classroom.model.bo

import com.fasterxml.jackson.annotation.JsonProperty

data class Code2SessionResp(
    val errcode: Int?,
    val errmsg: String?,
    val unionid: String?,
    val openid: String?,
    @JsonProperty("session_key")
    val sessionKey: String?,
)

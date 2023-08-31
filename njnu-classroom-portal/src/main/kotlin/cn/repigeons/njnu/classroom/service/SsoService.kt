package cn.repigeons.njnu.classroom.service

interface SsoService {
    suspend fun getOpenidByJsCode(jsCode: String): String
    fun updateLoginRecordByOpenid(openid: String)
}
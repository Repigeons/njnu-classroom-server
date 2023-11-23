package cn.repigeons.njnu.classroom.service

interface SsoService {
    suspend fun getOpenidByJsCode(jsCode: String): String
    suspend fun updateLoginRecordByOpenid(openid: String)
}
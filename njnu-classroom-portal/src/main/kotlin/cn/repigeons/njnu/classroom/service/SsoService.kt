package cn.repigeons.njnu.classroom.service

interface SsoService {
    fun getOpenidByJsCode(jsCode: String): String
    fun updateLoginRecordByOpenid(openid: String)
}
package cn.repigeons.njnu.classroom.service

import reactor.core.publisher.Mono

interface SsoService {
    suspend fun getOpenidByJsCode(jsCode: String): String
    fun updateLoginRecordByOpenid(openid: String): Mono<Unit>
}
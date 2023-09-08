package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.mbg.mapper.UsersMapper
import cn.repigeons.njnu.classroom.mbg.model.Users
import cn.repigeons.njnu.classroom.model.bo.Code2SessionResp
import cn.repigeons.njnu.classroom.service.SsoService
import kotlinx.coroutines.reactor.mono
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import kotlin.jvm.optionals.getOrDefault

@Service
class SsoServiceImpl(
    private val usersMapper: UsersMapper,
    @Value("\${mp.appid}")
    private val appid: String,
    @Value("\${mp.secret}")
    private val secret: String,
) : SsoService {
    private val webClient = WebClient.create()

    override suspend fun getOpenidByJsCode(jsCode: String): String {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session")
            .queryParam("appid", appid)
            .queryParam("secret", secret)
            .queryParam("js_code", jsCode)
            .queryParam("grant_type", "authorization_code")
            .toUriString()
        val body = webClient.get()
            .uri(url)
            .retrieve()
            .awaitBody<String>()
        val resp = GsonUtils.fromJson<Code2SessionResp>(body)
        check(resp.errcode?.takeUnless { it == 0 } == null) {
            resp.errmsg ?: "errcode=${resp.errcode}"
        }
        return resp.openid!!
    }

    override fun updateLoginRecordByOpenid(openid: String) = mono {
        val record = usersMapper.selectByPrimaryKey(openid).getOrDefault(Users())
        record.openid = openid
        record.lastLoginTime = Date()
        if (record.firstLoginTime == null) {
            record.firstLoginTime = record.lastLoginTime
            usersMapper.insert(record)
        } else {
            usersMapper.updateByPrimaryKey(record)
        }
        Unit
    }
}
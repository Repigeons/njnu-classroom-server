package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.mbg.mapper.UsersMapper
import cn.repigeons.njnu.classroom.mbg.model.Users
import cn.repigeons.njnu.classroom.model.Code2SessionResp
import cn.repigeons.njnu.classroom.service.SsoService
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder
import java.util.*
import kotlin.jvm.optionals.getOrDefault

@Service
open class SsoServiceImpl(
    private val restTemplate: RestTemplate,
    private val usersMapper: UsersMapper,
    @Value("\${mp.appid}")
    private val appid: String,
    @Value("\${mp.secret}")
    private val secret: String,
) : SsoService {
    override fun getOpenidByJsCode(jsCode: String): String {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session")
            .queryParam("appid", appid)
            .queryParam("secret", secret)
            .queryParam("js_code", jsCode)
            .queryParam("grant_type", "authorization_code")
            .toUriString()
        val resp: Code2SessionResp = restTemplate.getForObject(url)
        check(resp.errcode?.takeUnless { it == 0 } == null) {
            resp.errmsg ?: "errcode=${resp.errcode}"
        }
        return resp.openid!!
    }

    @Async
    override fun updateLoginRecordByOpenid(openid: String) {
        val record = usersMapper.selectByPrimaryKey(openid).getOrDefault(Users())
        record.openid = openid
        record.lastLoginTime = Date()
        if (record.firstLoginTime == null) {
            record.firstLoginTime = record.lastLoginTime
            usersMapper.insert(record)
        } else {
            usersMapper.updateByPrimaryKey(record)
        }
    }
}
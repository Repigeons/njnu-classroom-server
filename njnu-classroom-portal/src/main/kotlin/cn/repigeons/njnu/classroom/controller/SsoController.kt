package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.util.JwtUtil
import cn.repigeons.njnu.classroom.model.Code2SessionResp
import io.jsonwebtoken.impl.DefaultClaims
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("sso")
class SsoController(
    private val restTemplate: RestTemplate,
    @Value("\${mp.appid}")
    private val appid: String,
    @Value("\${mp.secret}")
    private val secret: String,
) {
    @GetMapping("login")
    fun login(
        @RequestParam jsCode: String
    ): CommonResponse<*> {
        val url = UriComponentsBuilder
            .fromHttpUrl("https://api.weixin.qq.com/sns/jscode2session")
            .queryParam("appid", appid)
            .queryParam("secret", secret)
            .queryParam("js_code", jsCode)
            .queryParam("grant_type", "authorization_code")
            .toUriString()
        val resp: Code2SessionResp = restTemplate.getForObject(url)
        if (resp.errcode?.takeUnless { it == 0 } != null) {
            return CommonResponse.failed(resp.errmsg ?: "jscode2session: ${resp.errcode}")
        }
        val token = JwtUtil.generate(DefaultClaims().setSubject(resp.openid))
        return CommonResponse.success(
            mapOf(
                "token" to token
            )
        )
    }
}
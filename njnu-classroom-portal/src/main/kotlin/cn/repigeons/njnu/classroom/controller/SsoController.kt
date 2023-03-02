package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.util.JwtUtil
import cn.repigeons.njnu.classroom.service.SsoService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("sso")
class SsoController(
    private val ssoService: SsoService,
) {
    @GetMapping("login")
    fun login(
        @RequestParam jsCode: String
    ): CommonResponse<*> {
        val openid = ssoService.getOpenidByJsCode(jsCode)
        ssoService.updateLoginRecordByOpenid(openid)
        val token = JwtUtil.generate(openid)
        return CommonResponse.success(
            mapOf(
                "token" to token
            )
        )
    }
}
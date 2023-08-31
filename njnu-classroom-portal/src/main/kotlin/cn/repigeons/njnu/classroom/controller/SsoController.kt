package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.utils.JwtUtils
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
    suspend fun login(
        @RequestParam jsCode: String
    ): CommonResult<*> {
        val openid = ssoService.getOpenidByJsCode(jsCode)
        ssoService.updateLoginRecordByOpenid(openid)
        val token = JwtUtils.generate(openid)
        return CommonResult.success(
            mapOf("token" to token)
        )
    }
}
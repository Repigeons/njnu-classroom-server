package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.model.vo.LoginVO
import cn.repigeons.njnu.classroom.service.SsoService
import cn.repigeons.njnu.classroom.utils.JwtUtils
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.*

@Tag(name = "用户服务")
@RestController
@RequestMapping("sso")
class SsoController(
    private val ssoService: SsoService,
) {
    @Operation(summary = "用户登录")
    @Parameter(name = "jsCode", description = "小程序登录code")
    @GetMapping("login")
    suspend fun login(
        @RequestParam jsCode: String
    ): CommonResult<LoginVO> {
        val openid = withContext(Dispatchers.IO) { ssoService.getOpenidByJsCode(jsCode) }
        withContext(Dispatchers.IO) { ssoService.updateLoginRecordByOpenid(openid) }
        val token = JwtUtils.generate(openid)
        return CommonResult.success(
            LoginVO(token)
        )
    }

    @Operation(summary = "获取openid")
    @GetMapping("openid")
    fun token2openid(
        @RequestHeader("Authorization") token: String,
    ): CommonResult<String> {
        val openid = JwtUtils.parse(token)?.subject
        return CommonResult.success(openid)
    }
}
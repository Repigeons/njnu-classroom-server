package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.client.PortalClient

class PortalClientFallback : PortalClient {
    override fun token2openid() = CommonResult.failed<String>("请求超时")
}
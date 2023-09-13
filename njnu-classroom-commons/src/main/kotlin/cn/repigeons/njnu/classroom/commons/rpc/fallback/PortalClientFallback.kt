package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.client.PortalClient
import reactor.core.publisher.Mono

class PortalClientFallback : PortalClient {
    override fun token2openid() = Mono.just(CommonResult.failed<String>("请求超时"))
}
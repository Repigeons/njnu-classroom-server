package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.rpc.client.PortalClient
import org.springframework.cloud.openfeign.FallbackFactory

class PortalClientFallbackFactory : FallbackFactory<PortalClient> {
    override fun create(cause: Throwable) = object : PortalClient {
        override suspend fun token2openid() = CommonResult.failed<String>("请求超时")
    }
}
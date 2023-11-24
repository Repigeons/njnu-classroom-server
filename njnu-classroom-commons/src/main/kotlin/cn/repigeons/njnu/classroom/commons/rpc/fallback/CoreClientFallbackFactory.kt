package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.rpc.client.CoreClient
import org.springframework.cloud.openfeign.FallbackFactory

class CoreClientFallbackFactory : FallbackFactory<CoreClient> {
    override fun create(cause: Throwable) = object : CoreClient {
        override suspend fun flushCache() = Unit
    }
}
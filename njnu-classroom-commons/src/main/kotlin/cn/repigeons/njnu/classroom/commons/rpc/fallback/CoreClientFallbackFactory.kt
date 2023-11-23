package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.rpc.client.CoreClient
import org.springframework.cloud.openfeign.FallbackFactory
import reactor.core.publisher.Mono

class CoreClientFallbackFactory : FallbackFactory<CoreClient> {
    override fun create(cause: Throwable) = object : CoreClient {
        override fun flushCache() = Mono.empty<Void>()
    }
}
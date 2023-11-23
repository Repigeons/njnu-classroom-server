package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.SpiderClient
import org.springframework.cloud.openfeign.FallbackFactory
import reactor.core.publisher.Mono

class SpiderClientFallbackFactory : FallbackFactory<SpiderClient> {
    override fun create(cause: Throwable) = object : SpiderClient {
        override fun run() = Mono.empty<Void>()
        override fun checkWithEhall(
            jasdm: String,
            weekday: Weekday,
            jc: Int,
            zylxdm: String
        ) = Mono.just(CommonResult.success(true))
    }
}
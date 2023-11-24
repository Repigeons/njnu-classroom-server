package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.SpiderClient
import org.springframework.cloud.openfeign.FallbackFactory

class SpiderClientFallbackFactory : FallbackFactory<SpiderClient> {
    override fun create(cause: Throwable) = object : SpiderClient {
        override suspend fun run() = Unit
        override suspend fun checkWithEhall(
            jasdm: String,
            weekday: Weekday,
            jc: Int,
            zylxdm: String
        ) = CommonResult.success(true)
    }
}
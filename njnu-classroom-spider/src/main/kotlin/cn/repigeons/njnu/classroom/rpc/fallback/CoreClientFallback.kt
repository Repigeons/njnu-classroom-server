package cn.repigeons.njnu.classroom.rpc.fallback

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.rpc.client.CoreClient

class CoreClientFallback : CoreClient {
    override fun flushCache() = CommonResponse.success()
}
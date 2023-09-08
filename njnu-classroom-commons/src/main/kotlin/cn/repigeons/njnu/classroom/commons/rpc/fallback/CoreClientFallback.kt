package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.rpc.client.CoreClient

class CoreClientFallback : CoreClient {
    override fun flushCache() = Unit
}
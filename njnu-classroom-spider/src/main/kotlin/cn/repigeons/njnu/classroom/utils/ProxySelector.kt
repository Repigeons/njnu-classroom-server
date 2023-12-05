package cn.repigeons.njnu.classroom.utils

import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.Proxy
import java.net.SocketAddress
import java.net.URI

class ProxySelector(
    private val proxies: List<Proxy>,
) : java.net.ProxySelector() {
    private val logger = LoggerFactory.getLogger(javaClass)
    override fun select(uri: URI): List<Proxy> = proxies.ifEmpty { listOf(Proxy.NO_PROXY) }

    override fun connectFailed(uri: URI, sa: SocketAddress, ioe: IOException) {
        logger.error("代理服务器连接失败：{}", sa, ioe)
    }
}
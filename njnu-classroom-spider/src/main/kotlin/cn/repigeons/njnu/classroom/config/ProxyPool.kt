package cn.repigeons.njnu.classroom.config

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.service.CookieService
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.*

class ProxyPool(
    private val cookieService: CookieService,
    private val allProxy: String,
) : ProxySelector() {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = OkHttpClient()
    private val poolRequest = Request.Builder()
        .url(allProxy)
        .build()
    private val testProxy = Request.Builder()
        .url("https://ehallapp.nnu.edu.cn/jwapp/")
        .build()

    override fun select(uri: URI): List<Proxy> {
        if (allProxy.isEmpty()) return mutableListOf()
        val list: List<ProxyItem> = client.newCall(poolRequest).execute().use { response ->
            val result = response.body?.string() ?: return mutableListOf()
            GsonUtils.fromJson(result)
        }
        val cookies = cookieService.getCookies()
        val proxies = list.mapNotNull { item ->
            try {
                val (ip, port) = item.proxy.split(':')
                val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port.toInt()))
                val client = cookieService.getHttpClient(cookies, proxy)
                client.newCall(testProxy).execute().use { response ->
                    proxy.takeIf { response.code == 200 }
                }
            } catch (e: Exception) {
                null
            }
        }
        logger.debug("有效代理数量: {}({})", proxies.size, uri)
        return proxies.ifEmpty {
            logger.error("无可用代理服务器")
            listOf(Proxy.NO_PROXY)
        }
    }

    override fun connectFailed(uri: URI, sa: SocketAddress, ioe: IOException) {
        logger.error("代理服务器连接失败：{}", sa, ioe)
    }

    data class ProxyItem(
        val https: Boolean,
        val proxy: String,
        val region: String,
        val source: String,
    )
}
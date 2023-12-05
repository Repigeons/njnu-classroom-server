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
    private val client = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .build()
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
//                logger.debug("代理服务器列表[{}]：{}", uri, result)
            GsonUtils.fromJson(result)
        }
        val cookies = cookieService.getCookies()
        val client = cookieService.getHttpClient(cookies, useProxy = false)
        val proxies = list.mapNotNull { item ->
            val (ip, port) = item.proxy.split(':')
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port.toInt()))
            try {
                client.newCall(testProxy).execute().use { response ->
                    proxy.takeIf { response.code == 200 }
                }
            } catch (e: SocketTimeoutException) {
                null
            }
        }
        logger.debug("有效代理数量: {}({})", proxies.size, uri)
        return proxies
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
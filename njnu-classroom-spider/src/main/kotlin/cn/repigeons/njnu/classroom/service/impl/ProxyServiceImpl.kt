package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import cn.repigeons.njnu.classroom.model.bo.ProxyItem
import cn.repigeons.njnu.classroom.service.CookieService
import cn.repigeons.njnu.classroom.service.ProxyService
import cn.repigeons.njnu.classroom.utils.ProxySelector
import jakarta.annotation.Resource
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import java.net.InetSocketAddress
import java.net.Proxy

@Service
class ProxyServiceImpl(
    @Value("\${proxy-pool.all:}")
    private val allProxy: String,
) : ProxyService {
    @Lazy
    @Resource
    private lateinit var cookieService: CookieService
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = OkHttpClient()
    private val poolRequest = Request.Builder()
        .url(allProxy)
        .build()
    private val testProxy = Request.Builder()
        .url("https://ehallapp.nnu.edu.cn/jwapp/")
        .build()

    override fun getProxySelector(): ProxySelector = ProxySelector(getProxies())
    private fun getProxies(): List<Proxy> {
        val list: List<ProxyItem> = client.newCall(poolRequest).execute().use { response ->
            val result = response.body?.string() ?: return emptyList()
            GsonUtils.fromJson(result)
        }
        val cookies = cookieService.getCookies()
        val proxies = list.mapNotNull { item ->
            val (ip, port) = item.proxy.split(':')
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port.toInt()))
            val client = cookieService.getHttpClient(cookies, proxy)
            try {
                client.newCall(testProxy).execute().use { response ->
                    proxy.takeIf { response.code == 200 }
                }
            } catch (e: Exception) {
                null
            }
        }
        logger.debug("有效代理: {}/{}", proxies.size, list.size)
        return proxies
    }
}
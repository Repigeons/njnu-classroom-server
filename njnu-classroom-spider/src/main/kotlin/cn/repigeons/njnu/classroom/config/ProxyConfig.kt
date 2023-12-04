package cn.repigeons.njnu.classroom.config

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.net.*

@Configuration
class ProxyConfig(
    @Value("\${proxy-pool.all:}")
    private val proxyPool: String,
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val client = OkHttpClient.Builder()
        .followRedirects(false)
        .followSslRedirects(false)
        .build()
    private val poolRequest = Request.Builder()
        .url(proxyPool)
        .build()
    private val testProxy = Request.Builder()
        .url("https://ehallapp.nnu.edu.cn/jwapp/")
        .build()

    @Bean
    fun proxyPool() = object : ProxySelector() {
        override fun select(uri: URI): List<Proxy> {
            if (proxyPool.isEmpty()) return mutableListOf()
            val list: List<ProxyItem> = client.newCall(poolRequest).execute().use { response ->
                val result = response.body?.string() ?: return mutableListOf()
//                logger.debug("代理服务器列表[{}]：{}", uri, result)
                GsonUtils.fromJson(result)
            }
            return list.mapNotNull { item ->
                val (ip, port) = item.proxy.split(':')
                val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(ip, port.toInt()))
                client.newCall(testProxy).execute().use { response ->
                    proxy.takeIf { response.code == 200 }
                }
            }
        }

        override fun connectFailed(uri: URI, sa: SocketAddress, ioe: IOException) {
            logger.error("代理服务器连接失败：{}", sa, ioe)
        }
    }

    data class ProxyItem(
        val https: Boolean,
        val proxy: String,
        val region: String,
        val source: String,
    )
}
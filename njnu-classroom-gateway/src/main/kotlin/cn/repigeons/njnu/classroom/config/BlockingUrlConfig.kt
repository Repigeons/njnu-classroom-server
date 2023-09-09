package cn.repigeons.njnu.classroom.config

import com.alibaba.cloud.nacos.NacosConfigProperties
import com.alibaba.nacos.api.NacosFactory
import com.alibaba.nacos.api.config.listener.Listener
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPatternRouteMatcher
import org.yaml.snakeyaml.Yaml
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Configuration
class BlockingUrlConfig(
    private val blockingUrlProperties: BlockingUrlProperties,
    private val nacosConfigProperties: NacosConfigProperties,
    @Value("\${spring.application.name}")
    private val application: String,
) : WebFilter {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val matcher = PathPatternRouteMatcher()
    private val forbiddenResponse = "{\"status\":403,\"message\":\"Blocking URL\",\"data\":null}".toByteArray()
    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val requestPath = exchange.request.uri.path
        val response = exchange.response
        blockingUrlProperties.path.forEach { path ->
            if (path == requestPath) {
                response.statusCode = HttpStatus.FORBIDDEN
                response.headers["Content-Type"] = "application/json"
                val buffer = response.bufferFactory().wrap(forbiddenResponse)
                return response.writeWith(Flux.just(buffer))
            }
        }
        blockingUrlProperties.pattern.forEach { pattern ->
            if (matcher.match(pattern, matcher.parseRoute(requestPath))) {
                response.statusCode = HttpStatus.FORBIDDEN
                response.headers["Content-Type"] = "application/json"
                val buffer = response.bufferFactory().wrap(forbiddenResponse)
                return response.writeWith(Flux.just(buffer))
            }
        }
        return chain.filter(exchange)
    }

    @PostConstruct
    fun autoRefresh() {
        val configService = NacosFactory.createConfigService(nacosConfigProperties.assembleConfigServiceProperties())
        configService.addListener(application, nacosConfigProperties.group, object : Listener {
            private val yaml = Yaml()
            override fun getExecutor() = null
            override fun receiveConfigInfo(configInfo: String?) {
                logger.info("更新配置:\n{}", configInfo)
                if (configInfo == null) return
                try {
                    val blocking = yaml.load<Map<String, *>>(configInfo)[BlockingUrlProperties.PREFIX]
                        ?.let { yaml.dump(it) }
                        ?: return
                    val properties = yaml.load<BlockingUrlProperties>(blocking)
                    blockingUrlProperties.path = properties.path
                    blockingUrlProperties.pattern = properties.pattern
                } catch (e: Exception) {
                    logger.error("更新失败: {}", e.message, e)
                }
            }
        })
    }
}
package cn.repigeons.njnu.classroom.config

import cn.dev33.satoken.context.SaHolder
import cn.dev33.satoken.`fun`.SaFunction
import cn.dev33.satoken.reactor.filter.SaReactorFilter
import cn.dev33.satoken.router.SaHttpMethod
import cn.dev33.satoken.router.SaRouter
import com.alibaba.cloud.nacos.NacosConfigProperties
import com.alibaba.nacos.api.NacosFactory
import com.alibaba.nacos.api.config.listener.Listener
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.yaml.snakeyaml.Yaml

@Component
class BlockingConfig(
    nacosConfigProperties: NacosConfigProperties,
    private val saReactorFilter: SaReactorFilter,
) {
    private val configService =
        NacosFactory.createConfigService(nacosConfigProperties.assembleConfigServiceProperties())

    init {
        configService.getConfig(DATA_ID, nacosConfigProperties.group, 5000).run(::autoRefresh)
        configService.addListener(DATA_ID, nacosConfigProperties.group, object : Listener {
            override fun getExecutor() = null
            override fun receiveConfigInfo(configInfo: String) = autoRefresh(configInfo)
        })
    }

    @Synchronized
    private fun autoRefresh(configInfo: String?) {
        if (configInfo == null) return
        val blockingStrategy = Yaml().loadAs(configInfo, BlockingStrategy::class.java)
        saReactorFilter.setAuth {
            blockingStrategy.strategy.forEach {
                SaRouter.match(it.method)
                    .match(it.path)
                    .check(SaFunction {
                        val token = SaHolder.getRequest().getHeader("Authorization", "")
                        if (it.token.isEmpty() || it.token != token)
                            throw HttpClientErrorException(HttpStatus.FORBIDDEN)
                    })
            }
        }
    }

    companion object {
        private const val DATA_ID = "blocking-strategy"
    }

    data class BlockingStrategy(
        var strategy: List<StrategyItem> = emptyList(),
    )

    data class StrategyItem(
        var method: SaHttpMethod = SaHttpMethod.ALL,
        var path: String = "/",
        var token: String = "",
    )
}
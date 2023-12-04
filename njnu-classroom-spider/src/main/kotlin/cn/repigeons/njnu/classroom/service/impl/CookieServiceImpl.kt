package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.service.CookieService
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.net.ProxySelector
import java.net.URL

@Service
class CookieServiceImpl(
    private val proxySelector: ProxySelector,
    @Value("\${account.username}")
    private val username: String,
    @Value("\${account.password}")
    private val password: String,
    @Value("\${account.gid}")
    private val gid: String,
) : CookieService {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val driver: WebDriver = run {
        val options = ChromeOptions()
        options.addArguments(
            "--headless",
            "--disable-gpu",
            "--no-sandbox",
            "--incognito",
        )
        ChromeDriver(options)
    }

    @Cacheable("cookies")
    override fun getCookies(): List<Cookie> {
        driver.get("https://authserver.nnu.edu.cn/authserver/login?service=http%3A%2F%2Fehallapp.nnu.edu.cn%2Fjwapp%2Fsys%2Fjsjy%2F*default%2Findex.do%3Famp_sec_version_%3D1%26gid_%3D${gid}")
        Thread.sleep(5000)
        if (URL(driver.currentUrl).host == "authserver.nnu.edu.cn") {
            logger.info("Login with user {}", username)
            driver.switchTo().defaultContent()
            try {
                driver.findElement(By.id("username")).sendKeys(username)
                driver.findElement(By.id("password")).sendKeys(password)
                driver.findElement(By.id("login_submit")).click()
            } catch (_: Exception) {
            }
            Thread.sleep(5000)
        }
        driver.get("http://ehallapp.nnu.edu.cn/jwapp/sys/jsjy/*default/index.do?amp_sec_version_=1&gid_=$gid")
        Thread.sleep(5000)
        return driver.manage().cookies
            .filter { it.name in listOf("MOD_AUTH_CAS", "_WEU") }
            .map {
                Cookie.Builder()
                    .name(it.name)
                    .value(it.value)
                    .domain(it.domain)
                    .path(it.path)
                    .build()
            }
    }

    override fun getHttpClient(cookies: List<Cookie>, useProxy: Boolean): OkHttpClient {
        return OkHttpClient.Builder()
            .cookieJar(object : CookieJar {
                override fun loadForRequest(url: HttpUrl) = cookies
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
            })
            .followRedirects(false)
            .followSslRedirects(false)
            .proxySelector(proxySelector)
            .build()
    }
}
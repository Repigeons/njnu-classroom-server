package cn.repigeons.njnu.classroom.service

import okhttp3.Cookie
import okhttp3.OkHttpClient
import java.net.Proxy

interface CookieService {
    fun getCookies(): List<Cookie>
    fun getHttpClient(cookies: List<Cookie>, proxy: Proxy? = null): OkHttpClient
}
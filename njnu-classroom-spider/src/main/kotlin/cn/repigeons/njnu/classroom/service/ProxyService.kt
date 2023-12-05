package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.utils.ProxySelector

interface ProxyService {
    fun getProxySelector(): ProxySelector
}
package cn.repigeons.njnu.classroom.commons.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.commons.rpc.client.SpiderClient

class SpiderClientFallback : SpiderClient {
    override fun run() = Unit
    override fun checkWithEhall(
        jasdm: String,
        weekday: Weekday,
        jc: Short,
        zylxdm: String
    ) = CommonResult.success(true)
}

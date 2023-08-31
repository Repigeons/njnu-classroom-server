package cn.repigeons.njnu.classroom.rpc.fallback

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.rpc.client.SpiderClient

class SpiderClientFallback : SpiderClient {
    override fun run() {}
    override fun checkWithEhall(
        jasdm: String,
        weekday: Weekday,
        jc: Short,
        zylxdm: String
    ) = CommonResult.success(true)
}

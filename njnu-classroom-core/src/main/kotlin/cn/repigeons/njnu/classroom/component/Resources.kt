package cn.repigeons.njnu.classroom.component

import cn.repigeons.commons.utils.GsonUtils

object Resources {
    val zylxdm: List<*> = javaClass.getResourceAsStream("/zylxdm.json")!!.reader().run {
        GsonUtils.fromJson(this)
    }
}
package cn.repigeons.njnu.classroom.component

import cn.repigeons.njnu.classroom.commons.utils.GsonUtils

object Resources {
    val zylxdm: List<*> = javaClass.getResourceAsStream("/zylxdm.json")!!.reader().run {
        GsonUtils.fromJson(this)
    }
}
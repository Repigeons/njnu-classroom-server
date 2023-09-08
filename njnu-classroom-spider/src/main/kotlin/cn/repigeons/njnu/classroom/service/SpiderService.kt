package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.mbg.model.Jas
import cn.repigeons.njnu.classroom.model.bo.JxlInfo
import cn.repigeons.njnu.classroom.model.bo.TimeInfo
import java.util.concurrent.Future

interface SpiderService {
    fun run(): Future<*>
    fun checkWithEhall(jasdm: String, weekday: Weekday, jc: Short, zylxdm: String): Boolean
    fun getJxlInfo(): List<JxlInfo>
    fun getClassrooms(jxldm: String): List<Jas>
    fun getTimeInfo(): TimeInfo
}
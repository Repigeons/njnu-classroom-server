package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.EmptyClassroomVO
import java.util.concurrent.Future

interface EmptyClassroomService {
    fun getEmptyClassrooms(jxlmc: String, weekday: Weekday, jc: Short): List<EmptyClassroomVO>
    fun feedback(
        jxlmc: String,
        weekday: Weekday,
        jc: Short,
        results: List<EmptyClassroomVO>,
        index: Int,
    ): Future<*>
}
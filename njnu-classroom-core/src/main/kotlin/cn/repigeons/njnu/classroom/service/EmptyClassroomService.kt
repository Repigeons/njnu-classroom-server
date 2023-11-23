package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import java.util.concurrent.Future

interface EmptyClassroomService {
    suspend fun getEmptyClassrooms(jxlmc: String, weekday: Weekday, jc: Int): List<EmptyClassroomVO>
    fun feedback(
        jxlmc: String,
        weekday: Weekday,
        jc: Int,
        results: List<EmptyClassroomVO>,
        index: Int,
    ): Future<*>
}
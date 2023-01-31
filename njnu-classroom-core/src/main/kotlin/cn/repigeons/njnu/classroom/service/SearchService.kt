package cn.repigeons.njnu.classroom.service

import cn.repigeons.commons.api.CommonPageable
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.TimetableVO

interface SearchService {
    fun search(
        jcKs: Short,
        jcJs: Short,
        weekday: Weekday?,
        jxl: String?,
        keyword: String?,
        page: Int,
        size: Int
    ): CommonPageable<TimetableVO>
}
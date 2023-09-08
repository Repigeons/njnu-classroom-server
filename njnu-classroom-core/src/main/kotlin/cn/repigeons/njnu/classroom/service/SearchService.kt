package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.commons.api.CommonPage
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.vo.TimetableVO

interface SearchService {
    fun search(
        jcKs: Short,
        jcJs: Short,
        weekday: Weekday?,
        jxlmc: String?,
        zylxdm: String?,
        keyword: String?,
        page: Int,
        size: Int
    ): CommonPage<TimetableVO>
}
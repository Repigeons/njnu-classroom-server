package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.api.CommonPage
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.mbg.dao.TimetableDAO
import cn.repigeons.njnu.classroom.model.vo.TimetableVO
import cn.repigeons.njnu.classroom.service.SearchService
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val timetableDAO: TimetableDAO,
) : SearchService {
    override fun search(
        jcKs: Short,
        jcJs: Short,
        weekday: Weekday?,
        jxlmc: String?,
        zylxdm: String?,
        keyword: String?,
        page: Int,
        size: Int,
    ): CommonPage<TimetableVO> {
        val commonPage = CommonPage.query(page, size) {
            timetableDAO.select(
                jcKs = jcKs,
                jcJs = jcJs,
                weekday = weekday?.name,
                jxlmc = jxlmc,
                zylxdm = zylxdm,
                keyword = keyword,
            )
        }
        return commonPage.map(::TimetableVO)
    }
}
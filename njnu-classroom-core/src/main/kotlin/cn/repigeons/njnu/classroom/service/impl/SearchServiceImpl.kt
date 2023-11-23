package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.commons.api.CommonPage
import cn.repigeons.njnu.classroom.commons.api.CommonPage.Companion.mapToCommonPage
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.vo.TimetableVO
import cn.repigeons.njnu.classroom.mybatis.service.ITimetableService
import cn.repigeons.njnu.classroom.service.SearchService
import com.mybatisflex.core.paginate.Page
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val timetableService: ITimetableService,
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
        return timetableService.query(
            jcKs, jcJs,
            weekday?.name, jxlmc, zylxdm, keyword,
            Page.of(page, size),
        ).mapToCommonPage(::TimetableVO)
    }
}
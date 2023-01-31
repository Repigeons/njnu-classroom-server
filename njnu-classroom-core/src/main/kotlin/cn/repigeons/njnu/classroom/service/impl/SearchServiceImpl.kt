package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.commons.api.CommonPageable
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.TimetableMapper
import cn.repigeons.njnu.classroom.mbg.model.Timetable
import cn.repigeons.njnu.classroom.model.TimetableVO
import cn.repigeons.njnu.classroom.service.SearchService
import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import org.mybatis.dynamic.sql.util.kotlin.elements.isEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isGreaterThanOrEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isLessThanOrEqualTo
import org.mybatis.dynamic.sql.util.kotlin.elements.isLike
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val timetableMapper: TimetableMapper
) : SearchService {
    override fun search(
        jcKs: Short,
        jcJs: Short,
        weekday: Weekday?,
        jxl: String?,
        keyword: String?,
        page: Int,
        size: Int,
    ): CommonPageable<TimetableVO> {
        PageHelper.startPage<Timetable>(page, size)
        val records = timetableMapper.select {
            val dsl = it.where(TimetableDynamicSqlSupport.jcKs, isGreaterThanOrEqualTo(jcKs))
                .and(TimetableDynamicSqlSupport.jcJs, isLessThanOrEqualTo(jcJs))
            weekday?.run {
                dsl.and(TimetableDynamicSqlSupport.weekday, isEqualTo(this.name))
            }
            jxl?.run {
                dsl.and(TimetableDynamicSqlSupport.jxlmc, isEqualTo(this))
            }
            keyword?.run {
                val value = "%$this%"
                dsl.and(TimetableDynamicSqlSupport.kcm, isLike(value))
                    .or(TimetableDynamicSqlSupport.jyytms, isLike(value))
                weekday?.run {
                    dsl.and(TimetableDynamicSqlSupport.weekday, isEqualTo(this.name))
                }
                jxl?.run {
                    dsl.and(TimetableDynamicSqlSupport.jxlmc, isEqualTo(this))
                }
                dsl.and(TimetableDynamicSqlSupport.jcJs, isLessThanOrEqualTo(jcJs))
                dsl.and(TimetableDynamicSqlSupport.jcKs, isGreaterThanOrEqualTo(jcKs))
            }
        }
        val pageInfo = PageInfo(records)
        val list = pageInfo.list.map {
            TimetableVO(it)
        }
        return CommonPageable(list, pageInfo)
    }
}
package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.component.Resources
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import cn.repigeons.njnu.classroom.service.OverviewService
import cn.repigeons.njnu.classroom.service.SearchService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("query")
class QueryController(
    private val cacheService: CacheService,
    private val emptyClassroomService: EmptyClassroomService,
    private val overviewService: OverviewService,
    private val searchService: SearchService
) {
    @GetMapping("emptyClassroom")
    fun getEmpty(
        @RequestParam jxlmc: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
    ): CommonResponse<*> {
        val result = emptyClassroomService.getEmptyClassrooms(jxlmc, weekday, jc)
        return CommonResponse.success(result)
    }

    @GetMapping("overview")
    fun getOverview(
        @RequestParam jasdm: String
    ): CommonResponse<*> {
        val result = overviewService.getOverview(jasdm)
        return CommonResponse.success(result)
    }

    @GetMapping("search")
    fun getSearch(
        @RequestParam(required = false) weekday: Weekday?,
        @RequestParam jcKs: Short,
        @RequestParam jcJs: Short,
        @RequestParam(defaultValue = "") jxlmc: String,
        @RequestParam(defaultValue = "") zylxdm: String,
        @RequestParam(defaultValue = "") keyword: String,
        @PageableDefault(page = 1) pageable: Pageable,
    ): CommonResponse<*> {
        val result = searchService.search(
            jcKs = jcKs,
            jcJs = jcJs,
            weekday = weekday,
            jxlmc = jxlmc.takeUnless { it.isEmpty() },
            keyword = keyword.takeUnless { it.isEmpty() },
            zylxdm = zylxdm.takeUnless { it.isEmpty() },
            page = pageable.pageNumber,
            size = pageable.pageSize,
        )
        return CommonResponse.success(result)
    }

    /**
     * 教室列表
     */
    @GetMapping("classrooms.json")
    fun getClassrooms() = CommonResponse.success(cacheService.getClassrooms())

    /**
     * 教学楼位置
     */
    @GetMapping("buildings.json")
    fun getPosition() = CommonResponse.success(cacheService.getBuildingPositions())

    /**
     * 资源类型代码
     */
    @GetMapping("zylxdm.json")
    fun getZylxdm() = CommonResponse.success(Resources.zylxdm)
}
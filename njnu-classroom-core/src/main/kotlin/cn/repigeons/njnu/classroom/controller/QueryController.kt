package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.component.Resources
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import cn.repigeons.njnu.classroom.service.OverviewService
import cn.repigeons.njnu.classroom.service.SearchService
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

    @GetMapping("classroomOverview")
    fun getOverview(
        @RequestParam jasdm: String
    ): CommonResponse<*> {
        val result = overviewService.getOverview(jasdm)
        return CommonResponse.success(result)
    }

    @GetMapping("search")
    fun getSearch(
        @RequestParam weekday: Weekday,
        @RequestParam jcKs: Short,
        @RequestParam jcJs: Short,
        @RequestParam jxlmc: String,
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): CommonResponse<*> {
        val result = searchService.search(
            jcKs = jcKs,
            jcJs = jcJs,
            weekday = weekday,
            jxlmc = if (jxlmc == "#" || jxlmc.isEmpty()) null else jxlmc,
            keyword = if (keyword == "#" || keyword.isBlank()) null else keyword,
            page = page,
            size = size
        )
        return CommonResponse.success(result)
    }

    @GetMapping("classrooms.json")
    fun getClassrooms() = CommonResponse.success(cacheService.getClassrooms())

    @GetMapping("buildings.json")
    fun getPosition() = CommonResponse.success(cacheService.getBuildingPositions())

    @GetMapping("zylxdm.json")
    fun getZylxdm() = CommonResponse.success(Resources.zylxdm)
}
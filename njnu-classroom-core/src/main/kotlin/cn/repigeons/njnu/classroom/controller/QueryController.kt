package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
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
    suspend fun getEmpty(
        @RequestParam jxlmc: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
    ): CommonResult<*> {
        val result = emptyClassroomService.getEmptyClassrooms(jxlmc, weekday, jc)
        return CommonResult.success(result)
    }

    @GetMapping("overview")
    suspend fun getOverview(
        @RequestParam jasdm: String
    ): CommonResult<*> {
        val result = overviewService.getOverview(jasdm)
        return CommonResult.success(result)
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
    ): CommonResult<*> {
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
        return CommonResult.success(result)
    }

    /**
     * 教室列表
     */
    @GetMapping("classrooms.json")
    fun getClassrooms() = CommonResult.success(cacheService.getClassrooms())

    /**
     * 教学楼位置
     */
    @GetMapping("buildings.json")
    fun getPosition() = CommonResult.success(cacheService.getBuildingPositions())

    /**
     * 资源类型代码
     */
    @GetMapping("zylxdm.json")
    fun getZylxdm() = CommonResult.success(Resources.zylxdm)
}
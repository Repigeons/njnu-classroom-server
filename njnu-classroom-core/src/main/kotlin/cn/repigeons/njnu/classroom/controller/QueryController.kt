package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonPage
import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.component.Resources
import cn.repigeons.njnu.classroom.model.dto.SearchDTO
import cn.repigeons.njnu.classroom.model.vo.EmptyClassroomVO
import cn.repigeons.njnu.classroom.model.vo.TimetableVO
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import cn.repigeons.njnu.classroom.service.OverviewService
import cn.repigeons.njnu.classroom.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "教室查询")
@RestController
@RequestMapping("query")
class QueryController(
    private val cacheService: CacheService,
    private val emptyClassroomService: EmptyClassroomService,
    private val overviewService: OverviewService,
    private val searchService: SearchService
) {
    @Operation(summary = "空教室列表")
    @Parameters(
        Parameter(name = "jxlmc", description = "教学楼名称"),
        Parameter(name = "weekday", description = "星期"),
        Parameter(name = "jc", description = "节次"),
    )
    @GetMapping("emptyClassroom")
    suspend fun getEmpty(
        @RequestParam jxlmc: String,
        @RequestParam weekday: Weekday,
        @RequestParam jc: Short,
    ): CommonResult<List<EmptyClassroomVO>> {
        val result = emptyClassroomService.getEmptyClassrooms(jxlmc, weekday, jc)
        return CommonResult.success(result)
    }

    @Operation(summary = "教室概览")
    @Parameter(name = "jasdm", description = "教室代码")
    @GetMapping("overview")
    suspend fun getOverview(
        @RequestParam jasdm: String
    ): CommonResult<List<TimetableVO>> {
        val result = overviewService.getOverview(jasdm)
        return CommonResult.success(result)
    }

    @Operation(summary = "更多搜索")
    @GetMapping("search")
    fun getSearch(searchDTO: SearchDTO): CommonResult<CommonPage<TimetableVO>> {
        val result = searchService.search(
            jcKs = searchDTO.jcKs,
            jcJs = searchDTO.jcJs,
            weekday = searchDTO.weekday,
            jxlmc = searchDTO.jxlmc.takeUnless { it.isEmpty() },
            keyword = searchDTO.keyword.takeUnless { it.isEmpty() },
            zylxdm = searchDTO.zylxdm.takeUnless { it.isEmpty() },
            page = searchDTO.page,
            size = searchDTO.size,
        )
        return CommonResult.success(result)
    }

    @Operation(summary = "教室列表")
    @GetMapping("classrooms.json")
    fun getClassrooms() = CommonResult.success(cacheService.getClassrooms())

    @Operation(summary = "教学楼位置")
    @GetMapping("buildings.json")
    fun getPosition() = CommonResult.success(cacheService.getBuildingPositions())

    @Operation(summary = "资源类型代码")
    @GetMapping("zylxdm.json")
    fun getZylxdm() = CommonResult.success(Resources.zylxdm)
}
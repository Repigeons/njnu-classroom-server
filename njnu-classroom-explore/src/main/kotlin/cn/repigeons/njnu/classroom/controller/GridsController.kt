package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.service.GridsService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "发现栏列表")
@RestController
class GridsController(
    private val gridsService: GridsService,
) {
    @Operation(summary = "发现栏")
    @GetMapping("grids.json")
    fun getGrids() = CommonResult.success(gridsService.getGrids())

    @Operation(summary = "刷新发现栏")
    @PostMapping("grids/flush")
    fun flushGrids(): CommonResult<Nothing> {
        gridsService.flushGrids()
        return CommonResult.success()
    }
}
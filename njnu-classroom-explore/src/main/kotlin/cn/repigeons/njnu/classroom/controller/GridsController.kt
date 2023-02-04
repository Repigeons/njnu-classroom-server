package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.service.GridsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 发现栏列表
 */
@RestController
class GridsController(
    private val gridsService: GridsService,
) {
    @GetMapping("grids.json")
    fun getGrids() = CommonResponse.success(gridsService.getGrids())

    @PostMapping("grids/flush")
    fun flushGrids(): CommonResponse<*> {
        gridsService.flushGrids()
        return CommonResponse.success()
    }
}
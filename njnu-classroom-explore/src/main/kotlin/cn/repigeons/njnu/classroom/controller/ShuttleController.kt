package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.model.ShuttleRoute
import cn.repigeons.njnu.classroom.service.ShuttleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "校车时刻表")
@RestController
@RequestMapping("shuttle")
class ShuttleController(
    private val shuttleService: ShuttleService
) {
    @Operation(summary = "查询校车站")
    @GetMapping("stations.json")
    fun getStations() = CommonResult.success(shuttleService.getStationPosition())

    @Operation(summary = "查询路线")
    @GetMapping("getRoutes")
    fun getShuttle(
        @RequestParam weekday: Weekday
    ): CommonResult<List<List<ShuttleRoute>>> {
        return CommonResult.success(
            listOf(
                shuttleService.getRoute(weekday, 1),
                shuttleService.getRoute(weekday, 2),
            )
        )
    }

    @Operation(summary = "上传图片")
    @PostMapping("uploadImage")
    suspend fun uploadShuttleImage(
        @RequestParam file: MultipartFile
    ): CommonResult<Nothing> {
        if (file.contentType?.startsWith("image/") == true) {
            shuttleService.sendShuttleImage(file)
            return CommonResult.success()
        }
        return CommonResult.failed("仅支持图片类型")
    }
}
package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.commons.enumerate.Weekday
import cn.repigeons.njnu.classroom.service.ShuttleService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * 校车时刻表
 */
@RestController
@RequestMapping("shuttle")
class ShuttleController(
    private val shuttleService: ShuttleService
) {
    @GetMapping("stations.json")
    fun getStations() = CommonResponse.success(shuttleService.getStationPosition())

    @GetMapping("getRoutes")
    fun getShuttle(
        @RequestParam weekday: Weekday
    ): CommonResponse<*> {
        return CommonResponse.success(
            listOf(
                shuttleService.getRoute(weekday, 1),
                shuttleService.getRoute(weekday, 2),
            )
        )
    }

    @PostMapping("uploadImage")
    fun uploadShuttleImage(
        @RequestParam file: MultipartFile
    ): CommonResponse<*> {
        if (file.contentType?.startsWith("image/") == true) {
            shuttleService.sendShuttleImage(file)
            return CommonResponse.success()
        }
        return CommonResponse.forbidden("仅支持图片类型")
    }
}
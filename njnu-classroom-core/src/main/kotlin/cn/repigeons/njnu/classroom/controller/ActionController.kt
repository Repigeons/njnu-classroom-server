package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.model.EmptyClassroomFeedbackDTO
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class ActionController(
    private val cacheService: CacheService,
    private val emptyClassroomService: EmptyClassroomService
) {
    init {
        cacheService.flushCache()
    }

    @PostMapping("flushCache")
    fun flushCache(): CommonResponse<*> {
        cacheService.flushCache()
        return CommonResponse.success()
    }

    @PostMapping("emptyClassroom/feedback")
    fun feedbackEmptyClassroom(@RequestBody dto: EmptyClassroomFeedbackDTO): CommonResponse<*> {
        emptyClassroomService.feedback(
            jxlmc = dto.jxlmc,
            weekday = dto.weekday,
            jc = dto.jc,
            results = dto.results,
            index = dto.index,
        )
        return CommonResponse.success()
    }

    @PostMapping("classrooms/flush")
    fun flushClassrooms(): CommonResponse<*> {
        cacheService.flushClassrooms()
        return CommonResponse.success()
    }

    @PostMapping("position/buildings/flush")
    fun flushBuildingPosition(): CommonResponse<*> {
        cacheService.flushBuildingPositions()
        return CommonResponse.success()
    }
}
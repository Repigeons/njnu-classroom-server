package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
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
    @PostMapping("flushCache")
    fun flushCache(): CommonResult<*> {
        cacheService.flushCache()
        return CommonResult.success()
    }

    @PostMapping("emptyClassroom/feedback")
    fun feedbackEmptyClassroom(@RequestBody dto: EmptyClassroomFeedbackDTO): CommonResult<*> {
        emptyClassroomService.feedback(
            jxlmc = dto.jxlmc,
            weekday = dto.weekday,
            jc = dto.jc,
            results = dto.results,
            index = dto.index,
        )
        return CommonResult.success()
    }

    @PostMapping("classrooms/flush")
    fun flushClassrooms(): CommonResult<*> {
        cacheService.flushClassrooms()
        return CommonResult.success()
    }
}
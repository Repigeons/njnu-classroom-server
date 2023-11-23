package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.model.dto.EmptyClassroomFeedbackDTO
import cn.repigeons.njnu.classroom.service.CacheService
import cn.repigeons.njnu.classroom.service.EmptyClassroomService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "教室数据")
@RestController
@RequestMapping("api")
class ActionController(
    private val cacheService: CacheService,
    private val emptyClassroomService: EmptyClassroomService
) {
    @Operation(summary = "刷新缓存")
    @PostMapping("flushCache")
    fun flushCache(): CommonResult<Nothing> {
        cacheService.flushCache()
        return CommonResult.success()
    }

    @Operation(summary = "空教室异常反馈")
    @PostMapping("emptyClassroom/feedback")
    suspend fun feedbackEmptyClassroom(@RequestBody dto: EmptyClassroomFeedbackDTO): CommonResult<Nothing> {
        withContext(Dispatchers.Default) {
            emptyClassroomService.feedback(
                jxlmc = dto.jxlmc,
                weekday = dto.weekday,
                jc = dto.jc,
                results = dto.results,
                index = dto.index,
            )
        }
        return CommonResult.success()
    }

    @Operation(summary = "刷新教室列表")
    @PostMapping("classrooms/flush")
    fun flushClassrooms(): CommonResult<Nothing> {
        cacheService.flushClassrooms()
        return CommonResult.success()
    }
}
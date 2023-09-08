package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.model.vo.NoticeVO
import cn.repigeons.njnu.classroom.service.NoticeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "公告栏")
@RestController
class NoticeController(
    private val noticeService: NoticeService
) {
    @Operation(summary = "发布新公告")
    @Parameter(name = "text", description = "公告内容")
    @PutMapping("notice")
    fun putNotice(
        @RequestParam text: String
    ): CommonResult<NoticeVO> {
        val data = noticeService.putNotice(text.replace("\\n", "\n"))
        return CommonResult.success(data)
    }

    @Operation(summary = "查询公告")
    @GetMapping("notice")
    fun getNotice(): CommonResult<NoticeVO> {
        val data = noticeService.getNotice()
        return CommonResult.success(data)
    }

    @Operation(summary = "设置公告")
    @Parameter(name = "id", description = "公告id")
    @PostMapping("notice")
    fun setNotice(
        @RequestParam id: Int
    ): CommonResult<NoticeVO> {
        val data = noticeService.setNotice(id)
        return CommonResult.success(data)
    }
}
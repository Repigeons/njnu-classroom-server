package cn.repigeons.njnu.classroom.controller

import cn.repigeons.njnu.classroom.commons.api.CommonResult
import cn.repigeons.njnu.classroom.service.NoticeService
import org.springframework.web.bind.annotation.*

@RestController
class NoticeController(
    private val noticeService: NoticeService
) {
    @PutMapping("notice")
    fun putNotice(
        @RequestParam text: String
    ): CommonResult<*> {
        val data = noticeService.putNotice(text.replace("\\n", "\n"))
        return CommonResult.success(data)
    }

    @GetMapping("notice")
    fun getNotice(): CommonResult<*> {
        val data = noticeService.getNotice()
        return CommonResult.success(data)
    }

    @PostMapping("notice")
    fun setNotice(
        @RequestParam id: Int
    ): CommonResult<*> {
        val data = noticeService.setNotice(id)
        return CommonResult.success(data)
    }
}
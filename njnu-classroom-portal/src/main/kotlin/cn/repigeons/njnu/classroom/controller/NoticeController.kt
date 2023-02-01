package cn.repigeons.njnu.classroom.controller

import cn.repigeons.commons.api.CommonResponse
import cn.repigeons.njnu.classroom.service.NoticeService
import org.springframework.web.bind.annotation.*

@RestController
class NoticeController(
    private val noticeService: NoticeService
) {

    @PutMapping("notice")
    fun putNotice(
        @RequestParam text: String
    ): CommonResponse<*> {
        val data = noticeService.putNotice(text.replace("\\n", "\n"))
        return CommonResponse.success(data)
    }

    @GetMapping("notice")
    fun getNotice(): CommonResponse<*> {
        val data = noticeService.getNotice()
        return CommonResponse.success(data)
    }

    @PostMapping("notice")
    fun setNotice(
        @RequestParam id: Int
    ): CommonResponse<*> {
        val data = noticeService.setNotice(id)
        return CommonResponse.success(data)
    }
}
package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.model.vo.NoticeVO

interface NoticeService {
    fun putNotice(text: String): NoticeVO
    fun getNotice(): NoticeVO?
    fun setNotice(id: Int): NoticeVO?
}
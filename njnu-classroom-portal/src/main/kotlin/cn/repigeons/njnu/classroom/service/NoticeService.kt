package cn.repigeons.njnu.classroom.service

import cn.repigeons.njnu.classroom.model.NoticeVO

interface NoticeService {
    fun putNotice(text: String): NoticeVO
    fun getNotice(): NoticeVO?
    fun setNotice(id: Int): NoticeVO?
}
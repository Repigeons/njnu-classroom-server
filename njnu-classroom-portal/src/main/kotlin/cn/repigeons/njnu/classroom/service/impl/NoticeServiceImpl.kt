package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.mbg.mapper.NoticeDynamicSqlSupport
import cn.repigeons.njnu.classroom.mbg.mapper.NoticeMapper
import cn.repigeons.njnu.classroom.mbg.model.Notice
import cn.repigeons.njnu.classroom.model.NoticeVO
import cn.repigeons.njnu.classroom.service.NoticeService
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
open class NoticeServiceImpl(
    private val noticeMapper: NoticeMapper
) : NoticeService {
    private val df = SimpleDateFormat("yyyy-MM-dd")

    @CachePut("notice", key = "''")
    override fun putNotice(text: String): NoticeVO {
        val notice = Notice()
        notice.time = Date()
        notice.text = text
        noticeMapper.insert(notice)
        return record2data(notice)
    }

    @Cacheable("notice", key = "''")
    override fun getNotice(): NoticeVO? {
        val record = noticeMapper.selectOne {
            it.orderBy(NoticeDynamicSqlSupport.time.descending())
                .limit(1)
        }
        if (record.isPresent)
            return record2data(record.get())
        return null
    }

    @CachePut("notice", key = "''")
    override fun setNotice(id: Int): NoticeVO? {
        val record = noticeMapper.selectByPrimaryKey(id)
        if (record.isPresent) {
            val notice = record.get()
            return record2data(notice)
        }
        return null
    }

    private fun record2data(record: Notice): NoticeVO {
        val timestamp = record.time.time / 1000
        val date = df.format(record.time)
        return NoticeVO(
            id = record.id,
            timestamp = timestamp,
            date = date,
            text = record.text
        )
    }
}
package cn.repigeons.njnu.classroom.service.impl

import cn.repigeons.njnu.classroom.model.vo.NoticeVO
import cn.repigeons.njnu.classroom.mybatis.model.Notice
import cn.repigeons.njnu.classroom.service.NoticeService
import com.mybatisflex.core.query.QueryWrapper
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class NoticeServiceImpl : NoticeService, cn.repigeons.njnu.classroom.mybatis.service.NoticeService() {
    @Cacheable("notice")
    override fun getNotice(): NoticeVO? {
        val notice = getOne(
            QueryWrapper()
                .orderBy(Notice::getTime)
                .desc()
                .limit(1)
        ) ?: return null
        return record2data(notice)
    }

    @CachePut("notice", key = "'SimpleKey []'")
    override fun putNotice(text: String): NoticeVO {
        val notice = Notice()
        notice.time = LocalDateTime.now()
        notice.text = text
        save(notice)
        return record2data(notice)
    }

    @CachePut("notice", key = "'SimpleKey []'")
    override fun setNotice(id: Int): NoticeVO? {
        val notice = getById(id)
            ?: return null
        return record2data(notice)
    }

    private fun record2data(record: Notice): NoticeVO {
        val timestamp = record.time.toEpochSecond(ZoneOffset.of("+8"))
        val date = record.time.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return NoticeVO(
            id = record.id,
            timestamp = timestamp,
            date = date,
            text = record.text
        )
    }
}
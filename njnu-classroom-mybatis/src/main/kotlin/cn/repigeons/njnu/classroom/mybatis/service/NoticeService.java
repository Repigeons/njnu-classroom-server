package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.mapper.NoticeMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Notice;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 公告记录 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
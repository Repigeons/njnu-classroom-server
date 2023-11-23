package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.dao.FeedbackDAO;
import cn.repigeons.njnu.classroom.mybatis.mapper.FeedbackMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Feedback;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户反馈 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {
    @Resource
    private FeedbackDAO dao;

    @NotNull
    public List<Long> statistic(@NotNull String jasdm, @NotNull Integer dayOfWeek, @NotNull Integer jc) {
        return dao.statistic(jasdm, dayOfWeek, jc);
    }
}
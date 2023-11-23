package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.model.Feedback;
import com.mybatisflex.core.service.IService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 用户反馈 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IFeedbackService extends IService<Feedback> {
    @NotNull
    List<Long> statistic(
            @NotNull String jasdm,
            @NotNull Integer dayOfWeek,
            @NotNull Integer jc
    );
}
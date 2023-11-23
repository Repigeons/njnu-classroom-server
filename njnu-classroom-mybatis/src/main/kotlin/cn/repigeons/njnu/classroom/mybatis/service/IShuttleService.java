package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.model.Shuttle;
import com.mybatisflex.core.service.IService;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 校车时刻表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IShuttleService extends IService<Shuttle> {
    @NotNull
    List<Shuttle> getRoute(
            @NotNull Integer weekday,
            @NotNull Short route
    );
}
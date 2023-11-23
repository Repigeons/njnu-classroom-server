package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.dao.ShuttleDAO;
import cn.repigeons.njnu.classroom.mybatis.mapper.ShuttleMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Shuttle;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 校车时刻表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class ShuttleService extends ServiceImpl<ShuttleMapper, Shuttle> implements IShuttleService {
    @Resource
    private ShuttleDAO dao;

    @NotNull
    public List<Shuttle> getRoute(@NotNull Integer weekday, @NotNull Short route) {
        return dao.selectRoute(weekday, route);
    }
}
package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.model.Kcb;
import com.mybatisflex.core.service.IService;

/**
 * 原始课程表 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IKcbService extends IService<Kcb> {
    void truncate();
}
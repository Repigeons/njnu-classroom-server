package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.dao.KcbDAO;
import cn.repigeons.njnu.classroom.mybatis.mapper.KcbMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Kcb;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 原始课程表 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class KcbService extends ServiceImpl<KcbMapper, Kcb> implements IKcbService {
    @Resource
    private KcbDAO dao;

    @Override
    public void truncate() {
        dao.truncate();
    }
}
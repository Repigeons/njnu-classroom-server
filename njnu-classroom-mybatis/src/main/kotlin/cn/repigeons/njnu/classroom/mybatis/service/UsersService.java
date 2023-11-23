package cn.repigeons.njnu.classroom.mybatis.service;


import cn.repigeons.njnu.classroom.mybatis.mapper.UsersMapper;
import cn.repigeons.njnu.classroom.mybatis.model.Users;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class UsersService extends ServiceImpl<UsersMapper, Users> implements IUsersService {

}
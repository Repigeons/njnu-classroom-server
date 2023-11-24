package cn.repigeons.njnu.classroom.mybatis.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan(basePackages = ["cn.repigeons.njnu.classroom.mybatis.mapper", "cn.repigeons.njnu.classroom.mybatis.dao"])
class MybatisMapperAutoConfig

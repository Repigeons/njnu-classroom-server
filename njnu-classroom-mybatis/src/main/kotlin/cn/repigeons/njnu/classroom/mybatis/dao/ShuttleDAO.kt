package cn.repigeons.njnu.classroom.mybatis.dao

import cn.repigeons.njnu.classroom.mybatis.model.Shuttle
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

@Mapper
interface ShuttleDAO {
    @Select("SELECT * FROM `shuttle` WHERE SUBSTR(working,#{weekday},1)='1' AND route=#{route}")
    fun selectRoute(
        @Param("weekday") weekday: Int,
        @Param("route") route: Short
    ): List<Shuttle>
}
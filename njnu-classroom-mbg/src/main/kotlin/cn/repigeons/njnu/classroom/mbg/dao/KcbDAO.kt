package cn.repigeons.njnu.classroom.mbg.dao

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Update

@Mapper
interface KcbDAO {
    @Update("TRUNCATE TABLE `kcb`")
    fun truncate()
}
package cn.repigeons.njnu.classroom.mbg.dao

import cn.repigeons.njnu.classroom.mbg.model.Timetable
import org.apache.ibatis.annotations.*
import org.apache.ibatis.type.JdbcType

@Mapper
interface TimetableDAO {
    @Update("TRUNCATE TABLE `timetable`")
    fun truncate()

    @Insert("INSERT INTO `timetable` SELECT * FROM `kcb`")
    fun cloneFromKcb()

    @Results(
        id = "TimetableResult",
        value = [
            Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
            Result(column = "JXLMC", property = "jxlmc", jdbcType = JdbcType.VARCHAR),
            Result(column = "jsmph", property = "jsmph", jdbcType = JdbcType.VARCHAR),
            Result(column = "JASDM", property = "jasdm", jdbcType = JdbcType.CHAR),
            Result(column = "SKZWS", property = "skzws", jdbcType = JdbcType.INTEGER),
            Result(column = "zylxdm", property = "zylxdm", jdbcType = JdbcType.CHAR),
            Result(column = "jc_ks", property = "jcKs", jdbcType = JdbcType.SMALLINT),
            Result(column = "jc_js", property = "jcJs", jdbcType = JdbcType.SMALLINT),
            Result(column = "weekday", property = "weekday", jdbcType = JdbcType.VARCHAR),
            Result(column = "SFYXZX", property = "sfyxzx", jdbcType = JdbcType.BIT),
            Result(column = "jyytms", property = "jyytms", jdbcType = JdbcType.LONGVARCHAR),
            Result(column = "kcm", property = "kcm", jdbcType = JdbcType.LONGVARCHAR),
        ],
    )
    @Select(
        """<script>
            select * from timetable
            where jc_ks &gt;= #{jcKs} and jc_js &lt;= #{jcJs}
            <if test='weekday != null'>
                and weekday = #{weekday}
            </if>
            <if test='jxlmc != null'>
                and jxlmc = #{jxlmc}
            </if>
            <if test='zylxdm != null'>
                and zylxdm = #{zylxdm}
            </if>
            <if test='keyword != null'>
                and (kcm like #{keyword} or jyytms like #{keyword})
            </if>
        </script>"""
    )
    fun select(
        @Param("jcKs") jcKs: Short,
        @Param("jcJs") jcJs: Short,
        @Param("weekday") weekday: String?,
        @Param("jxlmc") jxlmc: String?,
        @Param("zylxdm") zylxdm: String?,
        @Param("keyword") keyword: String?,
    ): List<Timetable>
}
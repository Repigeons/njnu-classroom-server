package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class TimetableDynamicSqlSupport {
    public static final Timetable timetable = new Timetable();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = timetable.id;

    /**
     * 教学楼名称
     */
    public static final SqlColumn<String> jxlmc = timetable.jxlmc;

    /**
     * 教室门牌号
     */
    public static final SqlColumn<String> jsmph = timetable.jsmph;

    /**
     * 教室代码
     */
    public static final SqlColumn<String> jasdm = timetable.jasdm;

    /**
     * 座位数
     */
    public static final SqlColumn<Integer> skzws = timetable.skzws;

    /**
     * 类型代码
     */
    public static final SqlColumn<String> zylxdm = timetable.zylxdm;

    /**
     * 节次_开始
     */
    public static final SqlColumn<Short> jcKs = timetable.jcKs;

    /**
     * 节次_结束
     */
    public static final SqlColumn<Short> jcJs = timetable.jcJs;

    /**
     * 星期
     */
    public static final SqlColumn<String> weekday = timetable.weekday;

    /**
     * 是否允许自习
     */
    public static final SqlColumn<Boolean> sfyxzx = timetable.sfyxzx;

    /**
     * 借用用途说明
     */
    public static final SqlColumn<String> jyytms = timetable.jyytms;

    /**
     * 课程名
     */
    public static final SqlColumn<String> kcm = timetable.kcm;

    public static final class Timetable extends AliasableSqlTable<Timetable> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> jxlmc = column("JXLMC", JDBCType.VARCHAR);

        public final SqlColumn<String> jsmph = column("jsmph", JDBCType.VARCHAR);

        public final SqlColumn<String> jasdm = column("JASDM", JDBCType.CHAR);

        public final SqlColumn<Integer> skzws = column("SKZWS", JDBCType.INTEGER);

        public final SqlColumn<String> zylxdm = column("zylxdm", JDBCType.CHAR);

        public final SqlColumn<Short> jcKs = column("jc_ks", JDBCType.SMALLINT);

        public final SqlColumn<Short> jcJs = column("jc_js", JDBCType.SMALLINT);

        public final SqlColumn<String> weekday = column("weekday", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfyxzx = column("SFYXZX", JDBCType.BIT);

        public final SqlColumn<String> jyytms = column("jyytms", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> kcm = column("kcm", JDBCType.LONGVARCHAR);

        public Timetable() {
            super("timetable", Timetable::new);
        }
    }
}
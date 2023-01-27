package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class UserTimetableDynamicSqlSupport {
    public static final UserTimetable userTimetable = new UserTimetable();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Long> id = userTimetable.id;

    /**
     * 用户id
     */
    public static final SqlColumn<String> openid = userTimetable.openid;

    /**
     * 星期
     */
    public static final SqlColumn<String> weekday = userTimetable.weekday;

    /**
     * 开始节次
     */
    public static final SqlColumn<Short> ksjc = userTimetable.ksjc;

    /**
     * 结束节次
     */
    public static final SqlColumn<Short> jsjc = userTimetable.jsjc;

    /**
     * 地点
     */
    public static final SqlColumn<String> place = userTimetable.place;

    /**
     * 备注
     */
    public static final SqlColumn<String> remark = userTimetable.remark;

    public static final class UserTimetable extends AliasableSqlTable<UserTimetable> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);

        public final SqlColumn<String> weekday = column("weekday", JDBCType.VARCHAR);

        public final SqlColumn<Short> ksjc = column("ksjc", JDBCType.SMALLINT);

        public final SqlColumn<Short> jsjc = column("jsjc", JDBCType.SMALLINT);

        public final SqlColumn<String> place = column("place", JDBCType.VARCHAR);

        public final SqlColumn<String> remark = column("remark", JDBCType.LONGVARCHAR);

        public UserTimetable() {
            super("user_timetable", UserTimetable::new);
        }
    }
}
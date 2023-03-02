package cn.repigeons.njnu.classroom.mbg.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;

public final class UserFavoritesDynamicSqlSupport {
    public static final UserFavorites userFavorites = new UserFavorites();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Long> id = userFavorites.id;

    /**
     * 用户id
     */
    public static final SqlColumn<String> openid = userFavorites.openid;

    /**
     * 星期
     */
    public static final SqlColumn<String> weekday = userFavorites.weekday;

    /**
     * 开始节次
     */
    public static final SqlColumn<Short> ksjc = userFavorites.ksjc;

    /**
     * 结束节次
     */
    public static final SqlColumn<Short> jsjc = userFavorites.jsjc;

    /**
     * 地点
     */
    public static final SqlColumn<String> place = userFavorites.place;

    /**
     * 颜色(#RGB)
     */
    public static final SqlColumn<String> color = userFavorites.color;

    /**
     * 备注
     */
    public static final SqlColumn<String> remark = userFavorites.remark;

    public static final class UserFavorites extends AliasableSqlTable<UserFavorites> {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);

        public final SqlColumn<String> weekday = column("weekday", JDBCType.VARCHAR);

        public final SqlColumn<Short> ksjc = column("ksjc", JDBCType.SMALLINT);

        public final SqlColumn<Short> jsjc = column("jsjc", JDBCType.SMALLINT);

        public final SqlColumn<String> place = column("place", JDBCType.VARCHAR);

        public final SqlColumn<String> color = column("color", JDBCType.CHAR);

        public final SqlColumn<String> remark = column("remark", JDBCType.LONGVARCHAR);

        public UserFavorites() {
            super("user_favorites", UserFavorites::new);
        }
    }
}
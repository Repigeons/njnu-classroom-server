package cn.repigeons.njnu.classroom.mbg.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;
import java.util.Date;

public final class UsersDynamicSqlSupport {
    public static final Users users = new Users();

    /**
     * 用户id
     */
    public static final SqlColumn<String> openid = users.openid;

    /**
     * 首次登录时间
     */
    public static final SqlColumn<Date> firstLoginTime = users.firstLoginTime;

    /**
     * 最新登录时间
     */
    public static final SqlColumn<Date> lastLoginTime = users.lastLoginTime;

    public static final class Users extends AliasableSqlTable<Users> {
        public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);

        public final SqlColumn<Date> firstLoginTime = column("first_login_time", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> lastLoginTime = column("last_login_time", JDBCType.TIMESTAMP);

        public Users() {
            super("users", Users::new);
        }
    }
}
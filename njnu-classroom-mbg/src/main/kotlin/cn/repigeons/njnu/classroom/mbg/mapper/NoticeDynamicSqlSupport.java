package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class NoticeDynamicSqlSupport {
    public static final Notice notice = new Notice();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = notice.id;

    /**
     * 发布时间
     */
    public static final SqlColumn<Date> time = notice.time;

    /**
     * 公告内容
     */
    public static final SqlColumn<String> text = notice.text;

    public static final class Notice extends AliasableSqlTable<Notice> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Date> time = column("time", JDBCType.TIMESTAMP);

        public final SqlColumn<String> text = column("text", JDBCType.VARCHAR);

        public Notice() {
            super("notice", Notice::new);
        }
    }
}
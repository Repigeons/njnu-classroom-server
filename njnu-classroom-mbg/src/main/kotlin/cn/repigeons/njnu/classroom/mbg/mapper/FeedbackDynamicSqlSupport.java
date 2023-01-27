package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import java.util.Date;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class FeedbackDynamicSqlSupport {
    public static final Feedback feedback = new Feedback();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = feedback.id;

    /**
     * 日期
     */
    public static final SqlColumn<Date> time = feedback.time;

    /**
     * 节次
     */
    public static final SqlColumn<Short> jc = feedback.jc;

    /**
     * 教室代码
     */
    public static final SqlColumn<String> jasdm = feedback.jasdm;

    public static final class Feedback extends AliasableSqlTable<Feedback> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Date> time = column("time", JDBCType.TIMESTAMP);

        public final SqlColumn<Short> jc = column("jc", JDBCType.SMALLINT);

        public final SqlColumn<String> jasdm = column("JASDM", JDBCType.CHAR);

        public Feedback() {
            super("feedback", Feedback::new);
        }
    }
}
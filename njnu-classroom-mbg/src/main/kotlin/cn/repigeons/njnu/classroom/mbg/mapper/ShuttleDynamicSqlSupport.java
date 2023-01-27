package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class ShuttleDynamicSqlSupport {
    public static final Shuttle shuttle = new Shuttle();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = shuttle.id;

    /**
     * 路线方向
     */
    public static final SqlColumn<Short> route = shuttle.route;

    /**
     * 发车时间
     */
    public static final SqlColumn<String> startTime = shuttle.startTime;

    /**
     * 起点站
     */
    public static final SqlColumn<String> startStation = shuttle.startStation;

    /**
     * 终点站
     */
    public static final SqlColumn<String> endStation = shuttle.endStation;

    /**
     * 发车数量
     */
    public static final SqlColumn<Integer> shuttleCount = shuttle.shuttleCount;

    /**
     * 工作日/双休日
     */
    public static final SqlColumn<String> working = shuttle.working;

    public static final class Shuttle extends AliasableSqlTable<Shuttle> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<Short> route = column("route", JDBCType.SMALLINT);

        public final SqlColumn<String> startTime = column("start_time", JDBCType.VARCHAR);

        public final SqlColumn<String> startStation = column("start_station", JDBCType.VARCHAR);

        public final SqlColumn<String> endStation = column("end_station", JDBCType.VARCHAR);

        public final SqlColumn<Integer> shuttleCount = column("shuttle_count", JDBCType.INTEGER);

        public final SqlColumn<String> working = column("working", JDBCType.CHAR);

        public Shuttle() {
            super("shuttle", Shuttle::new);
        }
    }
}
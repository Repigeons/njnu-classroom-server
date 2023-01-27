package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class PositionsDynamicSqlSupport {
    public static final Positions positions = new Positions();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = positions.id;

    /**
     * 名称
     */
    public static final SqlColumn<String> name = positions.name;

    /**
     * 纬度
     */
    public static final SqlColumn<Float> latitude = positions.latitude;

    /**
     * 经度
     */
    public static final SqlColumn<Float> longitude = positions.longitude;

    /**
     * 1=教学楼 2=校车站
     */
    public static final SqlColumn<Short> kind = positions.kind;

    public static final class Positions extends AliasableSqlTable<Positions> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> name = column("name", JDBCType.VARCHAR);

        public final SqlColumn<Float> latitude = column("latitude", JDBCType.REAL);

        public final SqlColumn<Float> longitude = column("longitude", JDBCType.REAL);

        public final SqlColumn<Short> kind = column("kind", JDBCType.SMALLINT);

        public Positions() {
            super("positions", Positions::new);
        }
    }
}
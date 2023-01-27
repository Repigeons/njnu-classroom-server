package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class GridsDynamicSqlSupport {
    public static final Grids grids = new Grids();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = grids.id;

    /**
     * 标题文字
     */
    public static final SqlColumn<String> text = grids.text;

    /**
     * 标题图片
     */
    public static final SqlColumn<String> imgUrl = grids.imgUrl;

    /**
     * 跳转页面
     */
    public static final SqlColumn<String> url = grids.url;

    /**
     * 执行方法
     */
    public static final SqlColumn<String> method = grids.method;

    /**
     * 按钮开放功能
     */
    public static final SqlColumn<String> button = grids.button;

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Boolean> active = grids.active;

    public static final class Grids extends AliasableSqlTable<Grids> {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> text = column("text", JDBCType.VARCHAR);

        public final SqlColumn<String> imgUrl = column("img_url", JDBCType.VARCHAR);

        public final SqlColumn<String> url = column("url", JDBCType.VARCHAR);

        public final SqlColumn<String> method = column("method", JDBCType.VARCHAR);

        public final SqlColumn<String> button = column("button", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> active = column("active", JDBCType.BIT);

        public Grids() {
            super("grids", Grids::new);
        }
    }
}
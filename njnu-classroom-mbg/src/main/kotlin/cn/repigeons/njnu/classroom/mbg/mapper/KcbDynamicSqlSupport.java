package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class KcbDynamicSqlSupport {
    public static final Kcb kcb = new Kcb();

    /**
     * @mbg.generated
     */
    public static final SqlColumn<Integer> id = kcb.id;

    /**
     * 教学楼名称
     */
    public static final SqlColumn<String> jxlmc = kcb.jxlmc;

    /**
     * 教室门牌号
     */
    public static final SqlColumn<String> jsmph = kcb.jsmph;

    /**
     * 教室代码
     */
    public static final SqlColumn<String> jasdm = kcb.jasdm;

    /**
     * 座位数
     */
    public static final SqlColumn<Integer> skzws = kcb.skzws;

    /**
     * 类型代码
     */
    public static final SqlColumn<String> zylxdm = kcb.zylxdm;

    /**
     * 节次_开始
     */
    public static final SqlColumn<Short> jcKs = kcb.jcKs;

    /**
     * 节次_结束
     */
    public static final SqlColumn<Short> jcJs = kcb.jcJs;

    /**
     * 星期
     */
    public static final SqlColumn<String> weekday = kcb.weekday;

    /**
     * 是否允许自习
     */
    public static final SqlColumn<Boolean> sfyxzx = kcb.sfyxzx;

    /**
     * 借用用途说明
     */
    public static final SqlColumn<String> jyytms = kcb.jyytms;

    /**
     * 课程名
     */
    public static final SqlColumn<String> kcm = kcb.kcm;

    public static final class Kcb extends AliasableSqlTable<Kcb> {
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

        public Kcb() {
            super("KCB", Kcb::new);
        }
    }
}
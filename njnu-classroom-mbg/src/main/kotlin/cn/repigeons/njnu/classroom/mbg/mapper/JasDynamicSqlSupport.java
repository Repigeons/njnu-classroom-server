package cn.repigeons.njnu.classroom.mbg.mapper;

import java.sql.JDBCType;
import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

public final class JasDynamicSqlSupport {
    public static final Jas jas = new Jas();

    /**
     * 教室代码
     */
    public static final SqlColumn<String> jasdm = jas.jasdm;

    /**
     * 教室名称
     */
    public static final SqlColumn<String> jasmc = jas.jasmc;

    /**
     * 教学楼代码
     */
    public static final SqlColumn<String> jxldm = jas.jxldm;

    /**
     * 教学楼名称
     */
    public static final SqlColumn<String> jxldmDisplay = jas.jxldmDisplay;

    /**
     * 学校校区代码
     */
    public static final SqlColumn<String> xxxqdm = jas.xxxqdm;

    /**
     * 学校校区名称
     */
    public static final SqlColumn<String> xxxqdmDisplay = jas.xxxqdmDisplay;

    /**
     * 教室类型代码
     */
    public static final SqlColumn<String> jaslxdm = jas.jaslxdm;

    /**
     * 教室类型名称
     */
    public static final SqlColumn<String> jaslxdmDisplay = jas.jaslxdmDisplay;

    /**
     * 状态
     */
    public static final SqlColumn<String> zt = jas.zt;

    /**
     * 楼层
     */
    public static final SqlColumn<Short> lc = jas.lc;

    /**
     * 上课座位数
     */
    public static final SqlColumn<Integer> skzws = jas.skzws;

    /**
     * 考试座位数
     */
    public static final SqlColumn<Integer> kszws = jas.kszws;

    /**
     * 学年学期代码
     */
    public static final SqlColumn<String> xnxqdm = jas.xnxqdm;

    /**
     * 学年学期代码2
     */
    public static final SqlColumn<String> xnxqdm2 = jas.xnxqdm2;

    /**
     * 管理单位代码
     */
    public static final SqlColumn<String> dwdm = jas.dwdm;

    /**
     * 管理单位名称
     */
    public static final SqlColumn<String> dwdmDisplay = jas.dwdmDisplay;

    /**
     * 座位属性代码
     */
    public static final SqlColumn<String> zwsxdm = jas.zwsxdm;

    /**
     * 使用日期
     */
    public static final SqlColumn<String> syrq = jas.syrq;

    /**
     * 使用时间
     */
    public static final SqlColumn<String> sysj = jas.sysj;

    /**
     * 实习类别
     */
    public static final SqlColumn<String> sxlb = jas.sxlb;

    /**
     * 是否已排课
     */
    public static final SqlColumn<Boolean> sfypk = jas.sfypk;

    /**
     * 是否允许排课
     */
    public static final SqlColumn<Boolean> sfyxpk = jas.sfyxpk;

    /**
     * 排课优先级
     */
    public static final SqlColumn<String> pkyxj = jas.pkyxj;

    /**
     * 是否考试维护
     */
    public static final SqlColumn<Boolean> sfkswh = jas.sfkswh;

    /**
     * 是否允许考试
     */
    public static final SqlColumn<Boolean> sfyxks = jas.sfyxks;

    /**
     * 考试优先级
     */
    public static final SqlColumn<String> ksyxj = jas.ksyxj;

    /**
     * 是否允许查询
     */
    public static final SqlColumn<Boolean> sfyxcx = jas.sfyxcx;

    /**
     * 是否允许借用
     */
    public static final SqlColumn<Boolean> sfyxjy = jas.sfyxjy;

    /**
     * 是否允许自习
     */
    public static final SqlColumn<Boolean> sfyxzx = jas.sfyxzx;

    /**
     * 教室用途
     */
    public static final SqlColumn<String> jsyt = jas.jsyt;

    /**
     * 相关地点
     */
    public static final SqlColumn<String> xgdd = jas.xgdd;

    /**
     * 备注
     */
    public static final SqlColumn<String> bz = jas.bz;

    public static final class Jas extends AliasableSqlTable<Jas> {
        public final SqlColumn<String> jasdm = column("JASDM", JDBCType.CHAR);

        public final SqlColumn<String> jasmc = column("JASMC", JDBCType.VARCHAR);

        public final SqlColumn<String> jxldm = column("JXLDM", JDBCType.VARCHAR);

        public final SqlColumn<String> jxldmDisplay = column("JXLDM_DISPLAY", JDBCType.VARCHAR);

        public final SqlColumn<String> xxxqdm = column("XXXQDM", JDBCType.VARCHAR);

        public final SqlColumn<String> xxxqdmDisplay = column("XXXQDM_DISPLAY", JDBCType.VARCHAR);

        public final SqlColumn<String> jaslxdm = column("JASLXDM", JDBCType.VARCHAR);

        public final SqlColumn<String> jaslxdmDisplay = column("JASLXDM_DISPLAY", JDBCType.VARCHAR);

        public final SqlColumn<String> zt = column("ZT", JDBCType.VARCHAR);

        public final SqlColumn<Short> lc = column("LC", JDBCType.SMALLINT);

        public final SqlColumn<Integer> skzws = column("SKZWS", JDBCType.INTEGER);

        public final SqlColumn<Integer> kszws = column("KSZWS", JDBCType.INTEGER);

        public final SqlColumn<String> xnxqdm = column("XNXQDM", JDBCType.VARCHAR);

        public final SqlColumn<String> xnxqdm2 = column("XNXQDM2", JDBCType.VARCHAR);

        public final SqlColumn<String> dwdm = column("DWDM", JDBCType.VARCHAR);

        public final SqlColumn<String> dwdmDisplay = column("DWDM_DISPLAY", JDBCType.VARCHAR);

        public final SqlColumn<String> zwsxdm = column("ZWSXDM", JDBCType.VARCHAR);

        public final SqlColumn<String> syrq = column("SYRQ", JDBCType.VARCHAR);

        public final SqlColumn<String> sysj = column("SYSJ", JDBCType.VARCHAR);

        public final SqlColumn<String> sxlb = column("SXLB", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfypk = column("SFYPK", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxpk = column("SFYXPK", JDBCType.BIT);

        public final SqlColumn<String> pkyxj = column("PKYXJ", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfkswh = column("SFKSWH", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxks = column("SFYXKS", JDBCType.BIT);

        public final SqlColumn<String> ksyxj = column("KSYXJ", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfyxcx = column("SFYXCX", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxjy = column("SFYXJY", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxzx = column("SFYXZX", JDBCType.BIT);

        public final SqlColumn<String> jsyt = column("JSYT", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> xgdd = column("XGDD", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> bz = column("BZ", JDBCType.LONGVARCHAR);

        public Jas() {
            super("JAS", Jas::new);
        }
    }
}
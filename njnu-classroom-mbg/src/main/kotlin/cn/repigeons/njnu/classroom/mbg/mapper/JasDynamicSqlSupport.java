package cn.repigeons.njnu.classroom.mbg.mapper;

import org.mybatis.dynamic.sql.AliasableSqlTable;
import org.mybatis.dynamic.sql.SqlColumn;

import java.sql.JDBCType;

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
        public final SqlColumn<String> jasdm = column("jasdm", JDBCType.CHAR);

        public final SqlColumn<String> jasmc = column("jasmc", JDBCType.VARCHAR);

        public final SqlColumn<String> jxldm = column("jxldm", JDBCType.VARCHAR);

        public final SqlColumn<String> jxldmDisplay = column("jxldm_display", JDBCType.VARCHAR);

        public final SqlColumn<String> xxxqdm = column("xxxqdm", JDBCType.VARCHAR);

        public final SqlColumn<String> xxxqdmDisplay = column("xxxqdm_display", JDBCType.VARCHAR);

        public final SqlColumn<String> jaslxdm = column("jaslxdm", JDBCType.VARCHAR);

        public final SqlColumn<String> jaslxdmDisplay = column("jaslxdm_display", JDBCType.VARCHAR);

        public final SqlColumn<String> zt = column("zt", JDBCType.VARCHAR);

        public final SqlColumn<Short> lc = column("lc", JDBCType.SMALLINT);

        public final SqlColumn<Integer> skzws = column("skzws", JDBCType.INTEGER);

        public final SqlColumn<Integer> kszws = column("kszws", JDBCType.INTEGER);

        public final SqlColumn<String> xnxqdm = column("xnxqdm", JDBCType.VARCHAR);

        public final SqlColumn<String> xnxqdm2 = column("xnxqdm2", JDBCType.VARCHAR);

        public final SqlColumn<String> dwdm = column("dwdm", JDBCType.VARCHAR);

        public final SqlColumn<String> dwdmDisplay = column("dwdm_display", JDBCType.VARCHAR);

        public final SqlColumn<String> zwsxdm = column("zwsxdm", JDBCType.VARCHAR);

        public final SqlColumn<String> syrq = column("syrq", JDBCType.VARCHAR);

        public final SqlColumn<String> sysj = column("sysj", JDBCType.VARCHAR);

        public final SqlColumn<String> sxlb = column("sxlb", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfypk = column("sfypk", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxpk = column("sfyxpk", JDBCType.BIT);

        public final SqlColumn<String> pkyxj = column("pkyxj", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfkswh = column("sfkswh", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxks = column("sfyxks", JDBCType.BIT);

        public final SqlColumn<String> ksyxj = column("ksyxj", JDBCType.VARCHAR);

        public final SqlColumn<Boolean> sfyxcx = column("sfyxcx", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxjy = column("sfyxjy", JDBCType.BIT);

        public final SqlColumn<Boolean> sfyxzx = column("sfyxzx", JDBCType.BIT);

        public final SqlColumn<String> jsyt = column("jsyt", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> xgdd = column("xgdd", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> bz = column("bz", JDBCType.LONGVARCHAR);

        public Jas() {
            super("jas", Jas::new);
        }
    }
}
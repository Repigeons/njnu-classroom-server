package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 教室列表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "jas")
public class Jas {

    /**
     * 教室代码
     */
    @Id(keyType = KeyType.Auto)
    private String jasdm;

    /**
     * 教室名称
     */
    @Column(value = "jasmc")
    private String jasmc;

    /**
     * 教学楼代码
     */
    @Column(value = "jxldm")
    private String jxldm;

    /**
     * 教学楼名称
     */
    @Column(value = "jxldm_display")
    private String jxldmDisplay;

    /**
     * 学校校区代码
     */
    @Column(value = "xxxqdm")
    private String xxxqdm;

    /**
     * 学校校区名称
     */
    @Column(value = "xxxqdm_display")
    private String xxxqdmDisplay;

    /**
     * 教室类型代码
     */
    @Column(value = "jaslxdm")
    private String jaslxdm;

    /**
     * 教室类型名称
     */
    @Column(value = "jaslxdm_display")
    private String jaslxdmDisplay;

    /**
     * 状态
     */
    @Column(value = "zt")
    private String zt;

    /**
     * 楼层
     */
    @Column(value = "lc")
    private Integer lc;

    /**
     * 教室用途
     */
    @Column(value = "jsyt")
    private String jsyt;

    /**
     * 上课座位数
     */
    @Column(value = "skzws")
    private Integer skzws;

    /**
     * 考试座位数
     */
    @Column(value = "kszws")
    private Integer kszws;

    /**
     * 学年学期代码
     */
    @Column(value = "xnxqdm")
    private String xnxqdm;

    /**
     * 学年学期代码2
     */
    @Column(value = "xnxqdm2")
    private String xnxqdm2;

    /**
     * 管理单位代码
     */
    @Column(value = "dwdm")
    private String dwdm;

    /**
     * 管理单位名称
     */
    @Column(value = "dwdm_display")
    private String dwdmDisplay;

    /**
     * 座位属性代码
     */
    @Column(value = "zwsxdm")
    private String zwsxdm;

    /**
     * 相关地点
     */
    @Column(value = "xgdd")
    private String xgdd;

    /**
     * 使用日期
     */
    @Column(value = "syrq")
    private String syrq;

    /**
     * 使用时间
     */
    @Column(value = "sysj")
    private String sysj;

    /**
     * 实习类别
     */
    @Column(value = "sxlb")
    private String sxlb;

    /**
     * 备注
     */
    @Column(value = "bz")
    private String bz;

    /**
     * 是否已排课
     */
    @Column(value = "sfypk")
    private Boolean sfypk;

    /**
     * 是否允许排课
     */
    @Column(value = "sfyxpk")
    private Boolean sfyxpk;

    /**
     * 排课优先级
     */
    @Column(value = "pkyxj")
    private String pkyxj;

    /**
     * 是否考试维护
     */
    @Column(value = "sfkswh")
    private Boolean sfkswh;

    /**
     * 是否允许考试
     */
    @Column(value = "sfyxks")
    private Boolean sfyxks;

    /**
     * 考试优先级
     */
    @Column(value = "ksyxj")
    private String ksyxj;

    /**
     * 是否允许查询
     */
    @Column(value = "sfyxcx")
    private Boolean sfyxcx;

    /**
     * 是否允许借用
     */
    @Column(value = "sfyxjy")
    private Boolean sfyxjy;

    /**
     * 是否允许自习
     */
    @Column(value = "sfyxzx")
    private Boolean sfyxzx;


    public String getJasdm() {
        return jasdm;
    }

    public void setJasdm(String jasdm) {
        this.jasdm = jasdm;
    }

    public String getJasmc() {
        return jasmc;
    }

    public void setJasmc(String jasmc) {
        this.jasmc = jasmc;
    }

    public String getJxldm() {
        return jxldm;
    }

    public void setJxldm(String jxldm) {
        this.jxldm = jxldm;
    }

    public String getJxldmDisplay() {
        return jxldmDisplay;
    }

    public void setJxldmDisplay(String jxldmDisplay) {
        this.jxldmDisplay = jxldmDisplay;
    }

    public String getXxxqdm() {
        return xxxqdm;
    }

    public void setXxxqdm(String xxxqdm) {
        this.xxxqdm = xxxqdm;
    }

    public String getXxxqdmDisplay() {
        return xxxqdmDisplay;
    }

    public void setXxxqdmDisplay(String xxxqdmDisplay) {
        this.xxxqdmDisplay = xxxqdmDisplay;
    }

    public String getJaslxdm() {
        return jaslxdm;
    }

    public void setJaslxdm(String jaslxdm) {
        this.jaslxdm = jaslxdm;
    }

    public String getJaslxdmDisplay() {
        return jaslxdmDisplay;
    }

    public void setJaslxdmDisplay(String jaslxdmDisplay) {
        this.jaslxdmDisplay = jaslxdmDisplay;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Integer getLc() {
        return lc;
    }

    public void setLc(Integer lc) {
        this.lc = lc;
    }

    public String getJsyt() {
        return jsyt;
    }

    public void setJsyt(String jsyt) {
        this.jsyt = jsyt;
    }

    public Integer getSkzws() {
        return skzws;
    }

    public void setSkzws(Integer skzws) {
        this.skzws = skzws;
    }

    public Integer getKszws() {
        return kszws;
    }

    public void setKszws(Integer kszws) {
        this.kszws = kszws;
    }

    public String getXnxqdm() {
        return xnxqdm;
    }

    public void setXnxqdm(String xnxqdm) {
        this.xnxqdm = xnxqdm;
    }

    public String getXnxqdm2() {
        return xnxqdm2;
    }

    public void setXnxqdm2(String xnxqdm2) {
        this.xnxqdm2 = xnxqdm2;
    }

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getDwdmDisplay() {
        return dwdmDisplay;
    }

    public void setDwdmDisplay(String dwdmDisplay) {
        this.dwdmDisplay = dwdmDisplay;
    }

    public String getZwsxdm() {
        return zwsxdm;
    }

    public void setZwsxdm(String zwsxdm) {
        this.zwsxdm = zwsxdm;
    }

    public String getXgdd() {
        return xgdd;
    }

    public void setXgdd(String xgdd) {
        this.xgdd = xgdd;
    }

    public String getSyrq() {
        return syrq;
    }

    public void setSyrq(String syrq) {
        this.syrq = syrq;
    }

    public String getSysj() {
        return sysj;
    }

    public void setSysj(String sysj) {
        this.sysj = sysj;
    }

    public String getSxlb() {
        return sxlb;
    }

    public void setSxlb(String sxlb) {
        this.sxlb = sxlb;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Boolean getSfypk() {
        return sfypk;
    }

    public void setSfypk(Boolean sfypk) {
        this.sfypk = sfypk;
    }

    public Boolean getSfyxpk() {
        return sfyxpk;
    }

    public void setSfyxpk(Boolean sfyxpk) {
        this.sfyxpk = sfyxpk;
    }

    public String getPkyxj() {
        return pkyxj;
    }

    public void setPkyxj(String pkyxj) {
        this.pkyxj = pkyxj;
    }

    public Boolean getSfkswh() {
        return sfkswh;
    }

    public void setSfkswh(Boolean sfkswh) {
        this.sfkswh = sfkswh;
    }

    public Boolean getSfyxks() {
        return sfyxks;
    }

    public void setSfyxks(Boolean sfyxks) {
        this.sfyxks = sfyxks;
    }

    public String getKsyxj() {
        return ksyxj;
    }

    public void setKsyxj(String ksyxj) {
        this.ksyxj = ksyxj;
    }

    public Boolean getSfyxcx() {
        return sfyxcx;
    }

    public void setSfyxcx(Boolean sfyxcx) {
        this.sfyxcx = sfyxcx;
    }

    public Boolean getSfyxjy() {
        return sfyxjy;
    }

    public void setSfyxjy(Boolean sfyxjy) {
        this.sfyxjy = sfyxjy;
    }

    public Boolean getSfyxzx() {
        return sfyxzx;
    }

    public void setSfyxzx(Boolean sfyxzx) {
        this.sfyxzx = sfyxzx;
    }
}

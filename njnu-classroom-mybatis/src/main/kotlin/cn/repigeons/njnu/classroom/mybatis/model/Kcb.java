package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 原始课程表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "kcb")
public class Kcb {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 教学楼名称
     */
    @Column(value = "jxlmc")
    private String jxlmc;

    /**
     * 教室门牌号
     */
    @Column(value = "jsmph")
    private String jsmph;

    /**
     * 教室代码
     */
    @Column(value = "jasdm")
    private String jasdm;

    /**
     * 座位数
     */
    @Column(value = "skzws")
    private Integer skzws;

    /**
     * 类型代码
     */
    @Column(value = "zylxdm")
    private String zylxdm;

    /**
     * 节次_开始
     */
    @Column(value = "jc_ks")
    private Integer jcKs;

    /**
     * 节次_结束
     */
    @Column(value = "jc_js")
    private Integer jcJs;

    /**
     * 借用用途说明
     */
    @Column(value = "jyytms")
    private String jyytms;

    /**
     * 课程名
     */
    @Column(value = "kcm")
    private String kcm;

    /**
     * 星期
     */
    @Column(value = "weekday")
    private String weekday;

    /**
     * 是否允许自习
     */
    @Column(value = "sfyxzx")
    private Boolean sfyxzx;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJxlmc() {
        return jxlmc;
    }

    public void setJxlmc(String jxlmc) {
        this.jxlmc = jxlmc;
    }

    public String getJsmph() {
        return jsmph;
    }

    public void setJsmph(String jsmph) {
        this.jsmph = jsmph;
    }

    public String getJasdm() {
        return jasdm;
    }

    public void setJasdm(String jasdm) {
        this.jasdm = jasdm;
    }

    public Integer getSkzws() {
        return skzws;
    }

    public void setSkzws(Integer skzws) {
        this.skzws = skzws;
    }

    public String getZylxdm() {
        return zylxdm;
    }

    public void setZylxdm(String zylxdm) {
        this.zylxdm = zylxdm;
    }

    public Integer getJcKs() {
        return jcKs;
    }

    public void setJcKs(Integer jcKs) {
        this.jcKs = jcKs;
    }

    public Integer getJcJs() {
        return jcJs;
    }

    public void setJcJs(Integer jcJs) {
        this.jcJs = jcJs;
    }

    public String getJyytms() {
        return jyytms;
    }

    public void setJyytms(String jyytms) {
        this.jyytms = jyytms;
    }

    public String getKcm() {
        return kcm;
    }

    public void setKcm(String kcm) {
        this.kcm = kcm;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Boolean getSfyxzx() {
        return sfyxzx;
    }

    public void setSfyxzx(Boolean sfyxzx) {
        this.sfyxzx = sfyxzx;
    }
}

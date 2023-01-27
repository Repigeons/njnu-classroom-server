package cn.repigeons.njnu.classroom.mbg.model;

/**
 * 校正表
 */
public class Correction {
    /**
     * @mbg.generated
     */
    private Integer id;

    /**
     * 星期
     */
    private String weekday;

    /**
     * 教学楼名称
     */
    private String jxlmc;

    /**
     * 教室门牌号
     */
    private String jsmph;

    /**
     * 教室代码
     */
    private String jasdm;

    /**
     * 座位数
     */
    private Integer skzws;

    /**
     * 类型代码
     */
    private String zylxdm;

    /**
     * 节次_开始
     */
    private Short jcKs;

    /**
     * 节次_结束
     */
    private Short jcJs;

    /**
     * 是否允许自习
     */
    private Boolean sfyxzx;

    /**
     * 借用用途说明
     */
    private String jyytms;

    /**
     * 课程名
     */
    private String kcm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
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

    public Short getJcKs() {
        return jcKs;
    }

    public void setJcKs(Short jcKs) {
        this.jcKs = jcKs;
    }

    public Short getJcJs() {
        return jcJs;
    }

    public void setJcJs(Short jcJs) {
        this.jcJs = jcJs;
    }

    public Boolean getSfyxzx() {
        return sfyxzx;
    }

    public void setSfyxzx(Boolean sfyxzx) {
        this.sfyxzx = sfyxzx;
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
}
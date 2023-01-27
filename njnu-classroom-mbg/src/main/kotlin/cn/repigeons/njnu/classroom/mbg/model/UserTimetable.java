package cn.repigeons.njnu.classroom.mbg.model;

/**
 * 用户定制时间表
 */
public class UserTimetable {
    /**
     * @mbg.generated
     */
    private Long id;

    /**
     * 用户id
     */
    private String openid;

    /**
     * 星期
     */
    private String weekday;

    /**
     * 开始节次
     */
    private Short ksjc;

    /**
     * 结束节次
     */
    private Short jsjc;

    /**
     * 地点
     */
    private String place;

    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Short getKsjc() {
        return ksjc;
    }

    public void setKsjc(Short ksjc) {
        this.ksjc = ksjc;
    }

    public Short getJsjc() {
        return jsjc;
    }

    public void setJsjc(Short jsjc) {
        this.jsjc = jsjc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
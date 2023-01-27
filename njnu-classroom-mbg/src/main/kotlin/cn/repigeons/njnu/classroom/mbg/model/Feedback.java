package cn.repigeons.njnu.classroom.mbg.model;

import java.util.Date;

/**
 * 用户反馈
 */
public class Feedback {
    /**
     * @mbg.generated
     */
    private Integer id;

    /**
     * 日期
     */
    private Date time;

    /**
     * 节次
     */
    private Short jc;

    /**
     * 教室代码
     */
    private String jasdm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Short getJc() {
        return jc;
    }

    public void setJc(Short jc) {
        this.jc = jc;
    }

    public String getJasdm() {
        return jasdm;
    }

    public void setJasdm(String jasdm) {
        this.jasdm = jasdm;
    }
}
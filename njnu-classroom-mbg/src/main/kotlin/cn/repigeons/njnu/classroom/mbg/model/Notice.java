package cn.repigeons.njnu.classroom.mbg.model;

import java.util.Date;

/**
 * 公告记录
 */
public class Notice {
    /**
     * @mbg.generated
     */
    private Integer id;

    /**
     * 发布时间
     */
    private Date time;

    /**
     * 公告内容
     */
    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
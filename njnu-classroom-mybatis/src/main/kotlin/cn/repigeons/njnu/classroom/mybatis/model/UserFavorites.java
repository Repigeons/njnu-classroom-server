package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 用户定制时间表 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "user_favorites")
public class UserFavorites {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 用户id
     */
    @Column(value = "openid")
    private String openid;

    /**
     * 标题
     */
    @Column(value = "title")
    private String title;

    /**
     * 星期
     */
    @Column(value = "weekday")
    private String weekday;

    /**
     * 开始节次
     */
    @Column(value = "ksjc")
    private Integer ksjc;

    /**
     * 结束节次
     */
    @Column(value = "jsjc")
    private Integer jsjc;

    /**
     * 地点
     */
    @Column(value = "place")
    private String place;

    /**
     * 颜色(#RGB)
     */
    @Column(value = "color")
    private String color;

    /**
     * 备注
     */
    @Column(value = "remark")
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public Integer getKsjc() {
        return ksjc;
    }

    public void setKsjc(Integer ksjc) {
        this.ksjc = ksjc;
    }

    public Integer getJsjc() {
        return jsjc;
    }

    public void setJsjc(Integer jsjc) {
        this.jsjc = jsjc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

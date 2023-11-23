package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 用户反馈 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "feedback")
public class Feedback {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 日期
     */
    @Column(value = "time")
    private java.time.LocalDateTime time;

    /**
     * 节次
     */
    @Column(value = "jc")
    private Integer jc;

    /**
     * 教室代码
     */
    @Column(value = "JASDM")
    private String jasdm;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public java.time.LocalDateTime getTime() {
        return time;
    }

    public void setTime(java.time.LocalDateTime time) {
        this.time = time;
    }

    public Integer getJc() {
        return jc;
    }

    public void setJc(Integer jc) {
        this.jc = jc;
    }

    public String getJasdm() {
        return jasdm;
    }

    public void setJasdm(String jasdm) {
        this.jasdm = jasdm;
    }
}

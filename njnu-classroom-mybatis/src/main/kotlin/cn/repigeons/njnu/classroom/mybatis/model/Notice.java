package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 公告记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "notice")
public class Notice {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 发布时间
     */
    @Column(value = "time")
    private java.time.LocalDateTime time;

    /**
     * 公告内容
     */
    @Column(value = "text")
    private String text;


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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

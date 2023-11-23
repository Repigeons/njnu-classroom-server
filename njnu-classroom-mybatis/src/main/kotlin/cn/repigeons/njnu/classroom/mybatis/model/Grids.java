package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 发现页面 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "grids")
public class Grids {

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 标题文字
     */
    @Column(value = "text")
    private String text;

    /**
     * 标题图片
     */
    @Column(value = "img_url")
    private String imgUrl;

    /**
     * 跳转页面
     */
    @Column(value = "url")
    private String url;

    /**
     * 执行方法
     */
    @Column(value = "method")
    private String method;

    /**
     * 按钮开放功能
     */
    @Column(value = "button")
    private String button;

    @Column(value = "active")
    private Boolean active;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

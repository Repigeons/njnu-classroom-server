package cn.repigeons.njnu.classroom.mbg.model;

/**
 * 发现页面
 */
public class Grids {
    /**
     * @mbg.generated
     */
    private Integer id;

    /**
     * 标题文字
     */
    private String text;

    /**
     * 标题图片
     */
    private String imgUrl;

    /**
     * 跳转页面
     */
    private String url;

    /**
     * 执行方法
     */
    private String method;

    /**
     * 按钮开放功能
     */
    private String button;

    /**
     * @mbg.generated
     */
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
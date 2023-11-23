package cn.repigeons.njnu.classroom.mybatis.model;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

/**
 * 用户信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Table(value = "users")
public class Users {

    /**
     * 用户id
     */
    @Id(keyType = KeyType.Auto)
    private String openid;

    /**
     * 首次登录时间
     */
    @Column(value = "first_login_time")
    private java.time.LocalDateTime firstLoginTime;

    /**
     * 最新登录时间
     */
    @Column(value = "last_login_time")
    private java.time.LocalDateTime lastLoginTime;


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public java.time.LocalDateTime getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(java.time.LocalDateTime firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    public java.time.LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(java.time.LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}

package cn.repigeons.njnu.classroom.mbg.model;

import java.util.Date;

/**
 * 用户信息
 */
public class Users {
    /**
     * 用户id
     */
    private String openid;

    /**
     * 首次登录时间
     */
    private Date firstLoginTime;

    /**
     * 最新登录时间
     */
    private Date lastLoginTime;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getFirstLoginTime() {
        return firstLoginTime;
    }

    public void setFirstLoginTime(Date firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
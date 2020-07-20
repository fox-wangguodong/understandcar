package com.fox.understandcaremperor.entity;

import java.time.LocalDateTime;

public class User {
    /**
     * 用户uuid
     */
    private String uuid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 账号
     */
    private String account;

    /**
     * 账户类型(默认"user"):“admin”，“user”
     */
    private String type;

    /**
     * 账号状态(默认"正常"):"正常","受限","禁用"
     */
    private String state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


    public User() {
    }

    public User(String uuid, String phone, String email, String account, String type, String state, LocalDateTime createTime, LocalDateTime updateTime) {
        this.uuid = uuid;
        this.phone = phone;
        this.email = email;
        this.account = account;
        this.type = type;
        this.state = state;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
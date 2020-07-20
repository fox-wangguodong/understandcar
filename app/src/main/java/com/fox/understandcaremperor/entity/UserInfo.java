package com.fox.understandcaremperor.entity;

public class UserInfo {
    /**
     * 用户uuid
     */
    private String uid;

    /**
     * 原图头像
     */
    private String face;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 性别: 女、男
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 个人简介
     */
    private String introduction;

    public UserInfo() {
    }

    public UserInfo(String uid, String face, String username, String sex, String email, String address, Integer age, String introduction) {
        this.uid = uid;
        this.face = face;
        this.username = username;
        this.sex = sex;
        this.email = email;
        this.address = address;
        this.age = age;
        this.introduction = introduction;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
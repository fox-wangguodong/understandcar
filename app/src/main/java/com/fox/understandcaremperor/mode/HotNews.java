package com.fox.understandcaremperor.mode;

public class HotNews {

    private String userFaceUrl;//用户头像url
    private String userName;//用户名
    private String content;//消息内容
    private String contentUrl;//消息配图url
    private String url;//详情url

    public HotNews() {
    }

    public HotNews(String userFaceUrl, String userName, String content, String contentUrl,String url) {
        this.userFaceUrl = userFaceUrl;
        this.userName = userName;
        this.content = content;
        this.contentUrl = contentUrl;
        this.url = url;
    }

    public String getUserFaceUrl() {
        return userFaceUrl;
    }

    public void setUserFaceUrl(String userFaceUrl) {
        this.userFaceUrl = userFaceUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

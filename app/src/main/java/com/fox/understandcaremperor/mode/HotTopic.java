package com.fox.understandcaremperor.mode;

public class HotTopic {
    private String title;
    private String content;
    private String url;//

    public HotTopic() {
    }

    public HotTopic(String title, String content,String url) {
        this.title = title;
        this.content = content;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

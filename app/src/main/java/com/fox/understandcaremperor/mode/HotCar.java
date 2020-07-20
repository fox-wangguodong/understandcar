package com.fox.understandcaremperor.mode;

/**
 * 热门车型
 */
public class HotCar {
    private String url;
    private String text;
    private String content;

    public HotCar() {
    }

    public HotCar(String url, String text, String content) {
        this.url = url;
        this.text = text;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

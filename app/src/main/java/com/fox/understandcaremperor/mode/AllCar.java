package com.fox.understandcaremperor.mode;

/**
 * 所有车型
 */
public class AllCar {
    private String url;
    private String text;
    private String price;
    private String content;

    public AllCar() {
    }

    public AllCar(String url, String text, String price, String content) {
        this.url = url;
        this.text = text;
        this.price = price;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

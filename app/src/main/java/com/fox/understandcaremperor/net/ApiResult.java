package com.fox.understandcaremperor.net;

public class ApiResult<T> {

    //响应码
    private int code;
    //描述消息
    private String message;
    //响应数据
    private T data;

    public ApiResult() {
    }
    public ApiResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}

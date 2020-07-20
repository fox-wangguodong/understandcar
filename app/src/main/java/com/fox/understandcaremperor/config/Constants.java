package com.fox.understandcaremperor.config;

import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Domain;

public class Constants {

    //设置非默认域名，name 可不传，不传默认为变量的名称
    public static String UPDATE_URL = "http://192.168.0.123:8080/checkupdate";

    @DefaultDomain() //设置为默认域名
    public static String BASE_URL = "http://192.168.0.123:8080/";


}

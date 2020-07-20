package com.fox.understandcaremperor.mode;

import java.util.List;

public class AllCarList {

    /**
     * reason : success
     * result : [{"id":"1","first_letter":"A","brand_name":"奥迪","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/1.png"},{"id":"2","first_letter":"A","brand_name":"阿尔法·罗密欧","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/2.png"},{"id":"3","first_letter":"A","brand_name":"阿斯顿·马丁","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/3.png"},{"id":"4","first_letter":"A","brand_name":"AC Schnitzer","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/4.png"},{"id":"5","first_letter":"A","brand_name":"ABT","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/5.png"},{"id":"6","first_letter":"A","brand_name":"安凯客车","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/6.png"},{"id":"7","first_letter":"A","brand_name":"Arash","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/7.png"},{"id":"8","first_letter":"A","brand_name":"Apollo","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/8.png"},{"id":"9","first_letter":"A","brand_name":"ARCFOX","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/9.png"},{"id":"10","first_letter":"A","brand_name":"艾康尼克","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/10.png"},{"id":"11","first_letter":"A","brand_name":"ALPINA","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/11.png"},{"id":"12","first_letter":"A","brand_name":"Agile Automotive","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/12.png"},{"id":"13","first_letter":"A","brand_name":"ATS","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/13.png"},{"id":"14","first_letter":"A","brand_name":"Aria","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/14.png"},{"id":"15","first_letter":"A","brand_name":"爱驰","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/15.png"},{"id":"16","first_letter":"A","brand_name":"Aurus","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/16.png"},{"id":"17","first_letter":"A","brand_name":"AUXUN傲旋","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/17.png"},{"id":"18","first_letter":"A","brand_name":"Aspark","brand_logo":"https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/18.png"}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 1
         * first_letter : A
         * brand_name : 奥迪
         * brand_logo : https://juhe.oss-cn-hangzhou.aliyuncs.com/api_image/538/brand/1.png
         */

        private String id;
        private String first_letter;
        private String brand_name;
        private String brand_logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFirst_letter() {
            return first_letter;
        }

        public void setFirst_letter(String first_letter) {
            this.first_letter = first_letter;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getBrand_logo() {
            return brand_logo;
        }

        public void setBrand_logo(String brand_logo) {
            this.brand_logo = brand_logo;
        }
    }
}

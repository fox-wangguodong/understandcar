package com.fox.understandcaremperor.net;


import com.fox.understandcaremperor.entity.User;
import com.fox.understandcaremperor.entity.UserInfo;
import com.fox.understandcaremperor.mode.AllCarList;
import com.fox.understandcaremperor.mode.HotNewsList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 检测APP更新
     * @return
     */
    @POST("checkVersion")
    @FormUrlEncoded
    Observable<ApiResult<String>> checkVersion(@Field("versionCode") int versionCode,
                                               @Field("appKey") String appKey);

    /**
     * 用户注册
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @POST("register")
    @FormUrlEncoded
    Observable<ApiResult<String>> register(@Field("username") String username,
                                           @Field("password") String password);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 用户密码
     * @return
     */
    @POST("login")
    @FormUrlEncoded
    Observable<ApiResult<String>> login(@Field("username") String username,
                                        @Field("password") String password);
    /**
     * 用户退出登录
     * @return
     */
    @POST("logout")
    Observable<ApiResult<String>> logout();

    /**
     * 获取用户信息
     * @return
     */
    @POST("getUserInfo")
    Observable<ApiResult<String>> getUserInfo();

    /**
     * 设置用户信息
     * @return
     */
    @POST("setUserInfo")
    Observable<ApiResult<String>> setUserInfo(@Field("userinfo") UserInfo userInfo);

    /**
     * 上传头像
     * @return
     */
    @POST("uploadFace")
    @Multipart
    Observable<ApiResult<String>> uploadFace(@Part MultipartBody.Part file);

    /**
     * 上传用户信息
     * @return
     */
    @POST("userinfo")
    Observable<String> userinfo(@Field("userinfo") User user);



    /**
     * 获取车款式型号
     * @return
     */
    @GET("brand")
    Observable<AllCarList> brand(@Query("first_letter") String first_letter, @Query("key") String key);


    /**
     * 热门话题
     * @return
     */
    @GET("index")
    Observable<HotNewsList> index(@Query("type") String type, @Query("key") String key);


}

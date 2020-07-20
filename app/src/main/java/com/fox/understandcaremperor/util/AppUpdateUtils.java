package com.fox.understandcaremperor.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


import com.fox.understandcaremperor.config.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppUpdateUtils {
    private static volatile AppUpdateUtils appUpdateUtils = null;
    public static AppUpdateUtils getInstance(Context context){
        if (appUpdateUtils == null){
            synchronized (AppUpdateUtils.class){
                if (appUpdateUtils == null){
                    appUpdateUtils = new AppUpdateUtils(context);
                }
            }
        }
        return appUpdateUtils;
    }

    private String url = "https://89e03ca66219bbe3cf072";
    private String ApkName = "";
    private int ApkVersionCode = 1;
    private String ApkVersionName = "1.0.0";
    private String ApkSize = "0";
    private String ApkMD5 = "";
    private String ApkDescription = "";
    private Context context = null;
    private AppUpdateUtils(Context context){
        this.context = context.getApplicationContext();
    }


    public void doUpdate(){
        checkUpdate();//检查更新
        if (ApkVersionCode > getPackageCode(context)){ //若有更新则提示更新

        }else { //没有更新不做操作

        }
    }





    /**
     * 请求服务器，检测版本信息
     */
    private void checkUpdate(){
        try {
            URL UpdateUrl = new URL(Constants.UPDATE_URL);
            HttpURLConnection connection = (HttpURLConnection) UpdateUrl.openConnection();//设置请求方式
            connection.setRequestMethod("GET");
            connection.connect();//连接
            int responseCode = connection.getResponseCode();//得到响应码
            if(responseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();//得到响应流
                String result = getStreamString(inputStream);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    url = jsonObject.getString("url");
                    ApkName = jsonObject.getString("ApkName");
                    ApkVersionCode = jsonObject.getInt("ApkVersionCode");
                    ApkVersionName = jsonObject.getString("ApkVersionName");
                    ApkSize = jsonObject.getString("ApkSize");
                    ApkMD5 = jsonObject.getString("ApkMD5");
                    ApkDescription = jsonObject.getString("ApkDescription");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取APP版本号
     * @param context
     * @return
     */
    public static int getPackageCode(Context context) {
        PackageManager manager = context.getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取APP版本名
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = null;
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getStreamString(InputStream tInputStream){
        if (tInputStream != null){
            try{
                BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
                StringBuffer tStringBuffer = new StringBuffer();
                String sTempOneLine = new String("");
                while ((sTempOneLine = tBufferedReader.readLine()) != null){
                    tStringBuffer.append(sTempOneLine);
                }
                return tStringBuffer.toString();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return "";
    }
}

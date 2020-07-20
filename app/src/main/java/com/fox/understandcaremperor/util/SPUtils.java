package com.fox.understandcaremperor.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class SPUtils {
    private static Application app = null;
    private static String FILE_NAME = "config";

    private static volatile SPUtils singleton = null;
    private SPUtils(Application app, String spName) {
        SPUtils.app = app;
        if (spName != null){
            SPUtils.FILE_NAME = spName;
        }
    }

    /**
     * 需要在application中初始化该方法
     * @param app 程序的application
     * @param spName SharedPreferences的文件名
     * @return
     */
    public static SPUtils init(Application app, String spName) {
        if (singleton == null) {
            synchronized (SPUtils.class) {
                if (singleton == null) {
                    singleton = new SPUtils(app,spName);
                }
            }
        }
        return singleton;
    }

    /**
     * 保存数据的方法，拿到数据保存数据的基本类型，然后根据类型调用不同的保存方法
     * @param key  KeyValue
     * @param value 数据值
     */
    public static void put(String key, String value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }
    public static void put(String key, int value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }
    public static void put(String key, boolean value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }
    public static void put(String key, float value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }
    public static void put(String key, long value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }
    public static void put(String key, Object value) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String JsonString = gson.toJson(value);//将对象转为JSON文本
        editor.putString(key, JsonString);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获取保存数据的方法，我们根据默认值的到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key 键值
     * @param defaultValue 数据默认值
     * @return
     */
    public static String get(String key, String defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }
    public static int get(String key, int defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }
    public static boolean get(String key, boolean defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }
    public static float get(String key, float defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }
    public static long get(String key, long defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }
    public static Object get(String key, Object defaultValue){
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String JsonString = gson.toJson(defaultValue);//将defaultValue对象转为JSON文本
        return sharedPreferences.getString(key, JsonString);
    }


    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有的数据
     */
    public static void clear() {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sharedPreferences = getApp().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }




    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();
        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }
        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }

    /**
     * 对application做判断，若为null则抛出异常
     * @return
     */
    private static Application getApp(){
        if (SPUtils.app == null){
            throw new NullPointerException("Application is null, please init SPUtils!");
        }
        return SPUtils.app;
    }
}

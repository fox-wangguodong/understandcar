package com.fox.understandcaremperor.util;


import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.config.Constants;
import com.fox.understandcaremperor.net.ApiService;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Okhttp网络请求工具类
 */
public class HttpManager {
    private static volatile HttpManager instance;
    public static HttpManager getInstance(){
        if (instance == null){
            synchronized (HttpManager.class){
                if (instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    //默认的okhttpclient
    private OkHttpClient client;

    //存放所有的service类和对应的retrofit
    private HashMap<Class, Retrofit> mServiceHashMap = new HashMap<>();

    //存放所有的service类实例对象
    private ConcurrentHashMap<Class, Object> cachedApis = new ConcurrentHashMap<>();

    private HttpManager() {
        // cookie缓存
        PersistentCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(App.getInstance()));

        // init okhttp 3 logger
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        //token拦截器，往请求头部添加 token 字段，实现了全局添加 token
        Interceptor tokenInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Response proceed = chain.proceed(request);//请求服务
                return proceed;
            }
        };

        // int okhttp
        client = new OkHttpClient.Builder()
//                .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())//使用https
//                .hostnameVerifier(new TrustAllHostnameVerifier())//https校验主机名
                .connectTimeout(5, TimeUnit.SECONDS) //连接超时时间
                .readTimeout(5, TimeUnit.SECONDS) //发送超时
                .writeTimeout(5, TimeUnit.SECONDS) //读取超时
                .retryOnConnectionFailure(false) // 失败重连
                .addInterceptor(logInterceptor) //日志拦截
                .addInterceptor(tokenInterceptor)//自动保持token
                .cookieJar(cookieJar) //添加本地cookie
                .build();
    }

    /**
     * 添加service类型
     * @param baseAddress
     * @param clz
     * @return
     */
    public HttpManager addService(String baseAddress,Class clz){
        // init retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseAddress)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //Gson对象转换
                .addConverterFactory(new StringConverterFactory()) // 当不能转换为Gson对象时执行字符串转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //rxjava支持
                .build();
        //将请求接口加入进来
        mServiceHashMap.put(clz, mRetrofit);
        return this;
    }

    /**
     * 获取service实例
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T getService(Class<T> clz) {
        Object obj = cachedApis.get(clz);//获取service实例
        if (obj != null){ // 若含有该实例则直接返回
            return (T) obj;
        }

        Retrofit retrofit = mServiceHashMap.get(clz);//根据service类获取对应的retrofit
        if (retrofit == null){
            throw new RuntimeException(clz.getSimpleName() + "not find");
        }
        T t = retrofit.create(clz);//使用retrofit创建service实例对象
        cachedApis.put(clz,t);//将service实例对象加入cachedApis
        return t;
    }

    /**
     * 临时创建service实例，并不会存入缓存
     * @param baseAddress
     * @param clz
     * @param <T>
     * @return
     */
    public <T> T getTemporaryService(String baseAddress,Class<T> clz) {
        // init retrofit
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(baseAddress)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //Gson对象转换
                .addConverterFactory(new StringConverterFactory()) // 当不能转换为Gson对象时执行字符串转换
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //rxjava支持
                .build();
        T t = mRetrofit.create(clz);//使用retrofit创建service实例对象
        return t;
    }

    /**
     * 将数据解析成最基本的String返回
     */
    class StringConverterFactory extends Converter.Factory {
        private final MediaType MEDIA_TYPE = MediaType.parse("text/plain");
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return (Converter<ResponseBody, String>) value -> value.string();
            }
            return null;
        }
        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            if (String.class.equals(type)) {
                return (Converter<String, RequestBody>) value -> RequestBody.create(MEDIA_TYPE, value);
            }
            return null;
        }
    }

    /*
     * 默认信任所有的证书,最好加上证书认证，主流App都有自己的证书
     */
    private static SSLSocketFactory createSSLSocketFactory() {
        try {
            String certificatesName = "这里设置App证书名字";
            //加载证书文件
            InputStream certificate = App.getInstance().getApplicationContext().getAssets().open(certificatesName);
            //读取证书信息
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            keyStore.setCertificateEntry("0", certificateFactory.generateCertificate(certificate));
            //关闭输入流
            certificate.close();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 信任所有证书
     */
    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
    }
    /**
     * 信任所有主机
     */
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;//信任所有主机
        }
    }
}
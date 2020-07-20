package com.fox.understandcaremperor;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.util.AppUpdateUtils;
import com.fox.understandcaremperor.util.SPUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {

    private static App instance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 开启multidex
        MultiDex.install(this);
    }

@Override
public void onCreate() {
    super.onCreate();

    instance = this;

    //若是debug模式
    if (isDebug(this)){

        Logger.clearLogAdapters();//清理所有Adapter后就不在进行日志记录了,清理后还可以添加就可以继续记录日志
        Logger.addLogAdapter(new AndroidLogAdapter());//输出到控制台
//            Logger.addLogAdapter(new DiskLogAdapter());//将日志保存到文件,默认保存到根目录下

        ARouter.openDebug();//(必须写在init之前)开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.openLog();  //(必须写在init之前)打印日志

        //内存泄漏检测
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }else {
            LeakCanary.install(this);
        }
    } else { ////若是release模式则进行日志记录

    }


    //bugly集成
    CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
    strategy.setAppPackageName(AppUpdateUtils.getPackageName(this));//包名
    strategy.setAppVersion(String.valueOf(AppUpdateUtils.getPackageCode(this)));//版本号
    strategy.setAppReportDelay(2000);//联网延迟
    CrashReport.initCrashReport(this, getString(R.string.bugly_app_id),false,strategy);

    SPUtils.init(this,"edog_controller.conf");//初始化SharedPreferences
    ARouter.init(this);//初始化ARouter
}

    public boolean isDebug(Context context){
        boolean isDebug = context.getApplicationInfo()!=null&& (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        return isDebug;
    }

    public static synchronized App getInstance() {
        return instance;
    }

}

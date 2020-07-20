package com.fox.understandcaremperor.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.ui.BaseActivity;
import com.fox.understandcaremperor.util.AppUpdateUtils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@Route(path = "/app/SplashActivity")
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_version_info)
    TextView tvVersionInfo;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Logger.d("开始");

        String packageName = AppUpdateUtils.getPackageName(App.getInstance().getApplicationContext());
        tvVersionInfo.setText("version:"+packageName);


        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            init();
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);//删除消息队列中所有的msg和runnable
    }

    public void init(){
        Logger.d("开始跳转");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance()
                        .build("/app/MainActivity")
                        .navigation();
                finish();
            }
        }, 2000);
    }
}

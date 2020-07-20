package com.fox.understandcaremperor.ui.activity;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.ui.BaseActivity;
import com.fox.understandcaremperor.ui.view.X5WebView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/app/WebViewActivity")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    X5WebView webView;

    @Autowired
    String web_url;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {//开启硬件加速
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setTitle(web_url);

        Logger.d("url:" + web_url);
        if (web_url != null) {
            ivStatus.setVisibility(View.GONE);
        }
        webView.loadUrl(web_url); //这个是加载的url
        webView.addJavascriptInterface(new JsInteraction(), "Android");//H5调用原生的方法
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class JsInteraction {
        @JavascriptInterface
        public void toastMessage(String message) {   //提供给js调用的方法
            Toast.makeText(App.getInstance().getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}

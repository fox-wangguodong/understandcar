package com.fox.understandcaremperor.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.net.ApiResult;
import com.fox.understandcaremperor.net.ApiService;
import com.fox.understandcaremperor.ui.BaseActivity;
import com.fox.understandcaremperor.util.AccountCheckUtil;
import com.fox.understandcaremperor.util.HttpManager;
import com.orhanobut.logger.Logger;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


@Route(path = "/app/RegisterActivity")
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.span3)
    View span3;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_login)
    TextView tvLoginin;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_auth_password)
    EditText etAuthPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.btn_register)
    public void onLoginClicked() {

        String username = this.etUsername.getText().toString();
        String password = this.etPassword.getText().toString();
        String authpassword = this.etAuthPassword.getText().toString();

        boolean email = AccountCheckUtil.isEmail(username);
        boolean phone = AccountCheckUtil.isPhone(username);
        boolean userAccount = AccountCheckUtil.isUserAccount(username);
        //若账号不符合以上条件则直接返回
        if (!(email || phone || userAccount)) {
            Toast.makeText(App.getInstance().getApplicationContext(), "请检查用户名格式!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(App.getInstance().getApplicationContext(), "密码不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!authpassword.equals(password)) {
            Toast.makeText(App.getInstance().getApplicationContext(), "密码不一致!", Toast.LENGTH_SHORT).show();
            return;
        }


        ApiService apiService = HttpManager.getInstance().getService(ApiService.class);
        apiService.register(username, password)
                .throttleFirst(3, TimeUnit.SECONDS)//防止重复点击
                .subscribeOn(Schedulers.io())//工作线程处理io
                .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))
                .subscribe(new Observer<ApiResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //此处做一些请求开始时的初始化事件,例如弹出一个dialog
                        Logger.d("订阅完成");
                        progressHUD.setLabel("正在注册").show();
                    }

                    @Override
                    public void onNext(ApiResult<String> apiResult) {
                        //此处处理请求成功业务(code == 200 )
                        if (apiResult.getCode() == 0) {
                            Toast.makeText(App.getInstance().getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();

                            ARouter.getInstance()
                                    .build("/app/LoginActivity")
                                    .withString("username", username)
                                    .withString("password", password)
                                    .navigation();
                        } else {
                            Toast.makeText(App.getInstance().getApplicationContext(), apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        Logger.d("网络请求成功:" + apiResult.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //此处处理请求失败业务(code != 200 )
                        Logger.d("网络请求失败:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //请求完成处理业务,关闭请求队列,关闭dialog等
                        progressHUD.dismiss();

                        finish();
                    }
                });
    }

    @OnClick(R.id.tv_login)
    public void onLoginInClicked() {

        String username = this.etUsername.getText().toString();
        String password = this.etPassword.getText().toString();

        ARouter.getInstance()
                .build("/app/LoginActivity")
                .withString("username", username)
                .withString("password", password)
                .navigation();

        finish();
    }
}

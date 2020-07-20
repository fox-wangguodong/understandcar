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
import com.fox.understandcaremperor.util.SPUtils;
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


@Route(path = "/app/LoginActivity")
public class LoginActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.span3)
    View span3;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        etUsername.setText(getIntent().getStringExtra("username"));
        etPassword.setText(getIntent().getStringExtra("password"));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        String username = this.etUsername.getText().toString();
        String password = this.etPassword.getText().toString();

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

        ApiService apiService = HttpManager.getInstance().getService(ApiService.class);//通过工具类创建请求接口
        apiService.login(username, password)//请求参数
                .throttleFirst(3, TimeUnit.SECONDS)//防止重复点击
                .subscribeOn(Schedulers.io())//工作线程处理io
                .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this)))//自动取消订阅,防止内存泄漏
                .subscribe(new Observer<ApiResult<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Logger.d("订阅完成");
                        progressHUD.setLabel("正在登陆").show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("请求失败:" + e.getMessage());
                    }
                    @Override
                    public void onNext(ApiResult<String> apiResult) {
                        Logger.d("请求完成:code:" + apiResult.getCode()
                                + " message:" + apiResult.getMessage()
                                + " data:" + apiResult.getData());
                        if (apiResult.getCode() == 200) {
                            ARouter.getInstance()
                                    .build("/app/MainActivity")
                                    .navigation();
                            SPUtils.put("username",username);
                            SPUtils.put("password",password);
                            SPUtils.put("logined",true);
                        } else {
                            Toast.makeText(App.getInstance().getApplicationContext(), apiResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onComplete() {
                        Logger.d("请求完毕");
                        progressHUD.dismiss();

                        finish();
                    }
                });
    }

    @OnClick(R.id.tv_register)
    public void onSignUpClicked() {
        ARouter.getInstance()
                .build("/app/RegisterActivity")
                .navigation();
        finish();
    }

}

package com.fox.understandcaremperor.ui.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.fox.understandcaremperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/app/DetailCarActivity")
public class DetailCarActivity extends AppCompatActivity {

    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;


    @Autowired
    String car_name;
    @Autowired
    String price;
    @Autowired
    String content;
    @Autowired
    String logo_url;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_car);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        tvCarName.setText(car_name);
        tvPrice.setText(price);
        tvContent.setText(content);
        Glide.with(this)
                .load(logo_url)
                .error(R.drawable.ic_launcher_foreground)
                .into(ivLogo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
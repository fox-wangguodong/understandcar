package com.fox.understandcaremperor.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.NewCar;

import java.util.List;

public class NewCarAdapter extends BaseQuickAdapter<NewCar, BaseViewHolder> {

    public NewCarAdapter(int layoutResId, @Nullable List<NewCar> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewCar item) {
        helper.setText(R.id.tv_newcar_title,item.getText());
        helper.setText(R.id.tv_newcar_price,item.getPrice());
        helper.setText(R.id.tv_newcar_content,item.getContent());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
                .fallback( R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .error(R.drawable.ic_launcher_background);//图片加载失败后，显示的图片
        Glide.with(App.getInstance().getApplicationContext())
                .load(item.getUrl())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_newcar_image));
    }
}

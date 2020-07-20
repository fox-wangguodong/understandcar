package com.fox.understandcaremperor.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.mode.HotNews;
import com.fox.understandcaremperor.mode.HotTopic;

import java.util.List;

public class HotNewsAdapter extends BaseQuickAdapter<HotNews, BaseViewHolder> {

    public HotNewsAdapter(int layoutResId, @Nullable List<HotNews> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotNews item) {

        helper.setText(R.id.tv_hot_news_user_name,item.getUserName());
        helper.setText(R.id.tv_hot_news_content,item.getContent());

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
                .fallback( R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                .error(R.drawable.ic_launcher_background);//图片加载失败后，显示的图片
        Glide.with(App.getInstance().getApplicationContext())
                .load(item.getUserFaceUrl())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_hot_news_user_face));
        Glide.with(App.getInstance().getApplicationContext())
                .load(item.getContentUrl())
                .apply(options)
                .into((ImageView) helper.getView(R.id.iv_hot_news_content_url));
    }
}

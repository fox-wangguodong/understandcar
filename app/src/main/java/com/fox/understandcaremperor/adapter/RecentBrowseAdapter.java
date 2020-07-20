package com.fox.understandcaremperor.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fox.understandcaremperor.App;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.mode.HotNews;
import com.fox.understandcaremperor.mode.HotTopic;
import com.fox.understandcaremperor.mode.RecentBrowse;
import com.orhanobut.logger.Logger;

import java.util.List;


public class RecentBrowseAdapter extends BaseMultiItemQuickAdapter<RecentBrowse, BaseViewHolder> {

    public RecentBrowseAdapter(List<RecentBrowse> data) {
        super(data);
        addItemType(RecentBrowse.HotNews, R.layout.item_hot_news);
        addItemType(RecentBrowse.HotTopic,R.layout.item_hot_topic);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecentBrowse item) {

        Logger.d("item type: "+item.getType());
        switch (item.getType()) {
            case RecentBrowse.HotNews:
                HotNews hotNews = (HotNews) item.getItem();

                helper.setText(R.id.tv_hot_news_user_name,hotNews.getUserName());
                helper.setText(R.id.tv_hot_news_content,hotNews.getContent());
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
                        .fallback( R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                        .error(R.drawable.ic_launcher_background);//图片加载失败后，显示的图片
                Glide.with(App.getInstance().getApplicationContext())
                        .load(hotNews.getUserFaceUrl())
                        .apply(options)
                        .into((ImageView) helper.getView(R.id.iv_hot_news_user_face));
                Glide.with(App.getInstance().getApplicationContext())
                        .load(hotNews.getContentUrl())
                        .apply(options)
                        .into((ImageView) helper.getView(R.id.iv_hot_news_content_url));
                break;
            case RecentBrowse.HotTopic:
                HotTopic hotTopic = (HotTopic) item.getItem();
                helper.setText(R.id.tv_hot_topic_title,hotTopic.getTitle());
                helper.setText(R.id.tv_hot_topic_content,hotTopic.getContent());
                break;
        }
    }
}

package com.fox.understandcaremperor.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.mode.HotTopic;

import java.util.List;

public class HotTopicAdapter extends BaseQuickAdapter<HotTopic, BaseViewHolder> {

    public HotTopicAdapter(int layoutResId, @Nullable List<HotTopic> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotTopic item) {
        helper.setText(R.id.tv_hot_topic_title,item.getTitle());
        helper.setText(R.id.tv_hot_topic_content,item.getContent());
    }
}

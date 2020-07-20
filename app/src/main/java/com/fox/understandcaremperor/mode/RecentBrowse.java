package com.fox.understandcaremperor.mode;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RecentBrowse implements MultiItemEntity {
    public static final int HotNews = 0;
    public static final int HotTopic = 1;
    private int type;//浏览类型, 0:HotNews  1:HotTopic
    private Object item;//类型

    public RecentBrowse() {
    }

    public RecentBrowse(int type, Object item) {
        this.type = type;
        this.item = item;
    }

    @Override
    public int getItemType() {
        return type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }
}

package com.fox.understandcaremperor.ui.fragment.carowner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.adapter.HotTopicAdapter;
import com.fox.understandcaremperor.mode.HotNews;
import com.fox.understandcaremperor.mode.HotNewsList;
import com.fox.understandcaremperor.mode.HotTopic;
import com.fox.understandcaremperor.net.ApiService;
import com.fox.understandcaremperor.util.HttpManager;
import com.orhanobut.logger.Logger;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * 热点话题
 */
public class HotTopicFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.rv_hot_topic)
    RecyclerView rvHotTopic;
    @BindView(R.id.swipefreshLayout)
    SwipeRefreshLayout swipefreshLayout;

    private List<HotTopic> hotTopicList;
    private HotTopicAdapter hotTopicAdapter;

    public HotTopicFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotTopicList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            HotTopic hotTopic = new HotTopic(
//                    "这个车企的灵魂车型是啥?",
//                    "灵魂的救赎，6到4的交接！每个汽车品牌，都有自己的灵魂车型，这个车型不一定级别最高，但一定能最代表这个品牌的调性。比如说3系宝马，S级至于奔驰，911至于保时捷。大家印象中最能代表奥迪的，可以称作为奥迪灵魂车型的，肯定是A6L。但是这一代的奥迪C8换代并不成功，更加时尚、年轻、运动的设计风格，已经完全脱离了原来的官车形象，奈何消费者并不买账，奥迪的这段困难时期，奥迪A4L站了起来，扛起了大旗，完成了救赎。");
//            hotTopicList.add(hotTopic);
//        }

        hotTopicAdapter = new HotTopicAdapter(R.layout.item_hot_topic, hotTopicList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_owner_hot_topic, container, false);
        bind = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvHotTopic.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局

        hotTopicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个Item");
                HotTopic hotTopic = hotTopicList.get(position);
                ARouter.getInstance()
                        .build("/app/WebViewActivity")
                        .withString("web_url",hotTopic.getUrl())
                        .navigation();
            }
        });
        // 设置上拉加载事件
        hotTopicAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpManager.getInstance()
                        .addService("http://v.juhe.cn/toutiao/", ApiService.class)
                        .getService(ApiService.class)
                        .index("top", "70553e2e8cacddebe60b46141620b72a")
                        .subscribeOn(Schedulers.io())//工作线程处理io
                        .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HotTopicFragment.this))) //自动取消订阅,防止内存泄漏
                        .subscribe(new Observer<HotNewsList>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(HotNewsList resp) {
                                if (resp.getError_code() == 0) {
                                    List<HotNewsList.ResultBean.DataBean> result = resp.getResult().getData();
                                    for (HotNewsList.ResultBean.DataBean bean : result) {
                                        HotTopic hotTopic = new HotTopic();
                                        hotTopic.setTitle(bean.getAuthor_name());
                                        hotTopic.setContent(bean.getTitle());
                                        hotTopic.setUrl(bean.getUrl());
                                        hotTopicList.add(hotTopic);
                                    }
                                    hotTopicAdapter.loadMoreEnd();//数据全部加载完毕
                                } else { //失败
                                    hotTopicAdapter.loadMoreEnd();//数据全部加载完毕
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                //主动调用加载失败来停止加载（该方法会提示加载失败）
                                hotTopicAdapter.loadMoreFail();
                            }

                            @Override
                            public void onComplete() {
                                hotTopicAdapter.loadMoreComplete();//主动调用加载完成，停止加载
                            }
                        });
            }
        }, rvHotTopic);
        hotTopicAdapter.setEnableLoadMore(true); // 开启或关闭上拉加载功能
        hotTopicAdapter.setPreLoadNumber(1);//预加载个数

        hotTopicAdapter.setEmptyView(R.layout.recyer_empty, rvHotTopic);
        hotTopicAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空

        rvHotTopic.setAdapter(hotTopicAdapter);//设置adapter
        rvHotTopic.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));//添加垂直分割线

        //下拉刷新事件
        swipefreshLayout.setOnRefreshListener(onRefreshListener);
        onRefreshListener.onRefresh();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            HttpManager.getInstance()
                    .addService("http://v.juhe.cn/toutiao/", ApiService.class)
                    .getService(ApiService.class)
                    .index("top", "70553e2e8cacddebe60b46141620b72a")
                    .subscribeOn(Schedulers.io())//工作线程处理io
                    .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HotTopicFragment.this))) //自动取消订阅,防止内存泄漏
                    .subscribe(new Observer<HotNewsList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(HotNewsList resp) {
                            if (resp.getError_code() == 0) {
                                hotTopicList.clear();

                                List<HotNewsList.ResultBean.DataBean> result = resp.getResult().getData();
                                for (HotNewsList.ResultBean.DataBean bean : result) {
                                    HotTopic hotTopic = new HotTopic();
                                    hotTopic.setTitle(bean.getAuthor_name());
                                    hotTopic.setContent(bean.getTitle());
                                    hotTopic.setUrl(bean.getUrl());
                                    hotTopicList.add(hotTopic);
                                }
                                hotTopicAdapter.notifyDataSetChanged();
                            } else { //失败

                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            swipefreshLayout.setRefreshing(false);//结束下拉刷新
                        }
                        @Override
                        public void onComplete() {
                            swipefreshLayout.setRefreshing(false);//结束下拉刷新
                        }
                    });
        }
    };
}

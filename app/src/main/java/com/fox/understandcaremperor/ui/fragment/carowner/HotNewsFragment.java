package com.fox.understandcaremperor.ui.fragment.carowner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.adapter.HotNewsAdapter;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.HotNews;
import com.fox.understandcaremperor.mode.HotNewsList;
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
 * 热点新闻
 */
public class HotNewsFragment extends Fragment {

    Unbinder bind;
    @BindView(R.id.rv_hot_news)
    RecyclerView rvHotNews;
    @BindView(R.id.swipefreshLayout)
    SwipeRefreshLayout swipefreshLayout;

    private List<HotNews> hotNewsList;
    private HotNewsAdapter hotNewsAdapter;

    public HotNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hotNewsList = new ArrayList<>();
        hotNewsAdapter = new HotNewsAdapter(R.layout.item_hot_news, hotNewsList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_news, container, false);
        bind = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvHotNews.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局

        // 点击监听事件
        hotNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个Item");
                HotNews hotNews = hotNewsList.get(position);
                ARouter.getInstance()
                        .build("/app/WebViewActivity")
                        .withString("web_url",hotNews.getUrl())
                        .navigation();
            }
        });

        // 设置上拉加载事件
        hotNewsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpManager.getInstance()
                        .addService("http://v.juhe.cn/toutiao/", ApiService.class)
                        .getService(ApiService.class)
                        .index("top", "70553e2e8cacddebe60b46141620b72a")
                        .subscribeOn(Schedulers.io())//工作线程处理io
                        .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HotNewsFragment.this))) //自动取消订阅,防止内存泄漏
                        .subscribe(new Observer<HotNewsList>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }
                            @Override
                            public void onNext(HotNewsList resp) {
                                if (resp.getError_code() == 0) {
                                    List<HotNewsList.ResultBean.DataBean> result = resp.getResult().getData();
                                    for (HotNewsList.ResultBean.DataBean bean : result) {
                                        HotNews hotNews = new HotNews();
                                        hotNews.setUserFaceUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1576845309,2943053611&fm=26&gp=0.jpg");
                                        hotNews.setUserName(bean.getCategory());
                                        hotNews.setContent(bean.getTitle());
                                        hotNews.setContentUrl(bean.getThumbnail_pic_s());
                                        hotNews.setUrl(bean.getUrl());
                                        hotNewsList.add(hotNews);
                                    }
                                    hotNewsAdapter.loadMoreEnd();//数据全部加载完毕
                                } else { //失败
                                    hotNewsAdapter.loadMoreEnd();//数据全部加载完毕
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                //主动调用加载失败来停止加载（该方法会提示加载失败）
                                hotNewsAdapter.loadMoreFail();
                            }
                            @Override
                            public void onComplete() {
                                hotNewsAdapter.loadMoreComplete();//主动调用加载完成，停止加载
                            }
                        });
            }
        }, rvHotNews);

        hotNewsAdapter.setEnableLoadMore(true); // 开启或关闭上拉加载功能
        hotNewsAdapter.setPreLoadNumber(1);//预加载个数

        hotNewsAdapter.setEmptyView(R.layout.recyer_empty, rvHotNews);
        hotNewsAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空

        rvHotNews.setAdapter(hotNewsAdapter);//设置adapter
        rvHotNews.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HotNewsFragment.this))) //自动取消订阅,防止内存泄漏
                    .subscribe(new Observer<HotNewsList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(HotNewsList resp) {
                            if (resp.getError_code() == 0) {
                                hotNewsList.clear();

                                List<HotNewsList.ResultBean.DataBean> result = resp.getResult().getData();
                                for (HotNewsList.ResultBean.DataBean bean : result) {
                                    HotNews hotNews = new HotNews();
                                    hotNews.setUserFaceUrl("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1576845309,2943053611&fm=26&gp=0.jpg");
                                    hotNews.setUserName(bean.getCategory());
                                    hotNews.setContent(bean.getTitle());
                                    hotNews.setContentUrl(bean.getThumbnail_pic_s());
                                    hotNews.setUrl(bean.getUrl());
                                    hotNewsList.add(hotNews);
                                }
                                hotNewsAdapter.notifyDataSetChanged();
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
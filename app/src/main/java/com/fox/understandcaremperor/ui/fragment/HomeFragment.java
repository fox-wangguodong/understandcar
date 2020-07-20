package com.fox.understandcaremperor.ui.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.adapter.AllCarAdapter;
import com.fox.understandcaremperor.adapter.HotCarAdapter;
import com.fox.understandcaremperor.config.Constants;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.AllCarList;
import com.fox.understandcaremperor.mode.HotCar;
import com.fox.understandcaremperor.net.ApiResult;
import com.fox.understandcaremperor.net.ApiService;
import com.fox.understandcaremperor.util.HttpManager;
import com.orhanobut.logger.Logger;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.rv_all_car)
    RecyclerView rvAllCar;
    @BindView(R.id.swipefreshLayout)
    SwipeRefreshLayout swipefreshLayout;

    public HomeFragment() {
        // Required empty public constructor
    }

    private ArrayList<String> images;
    private ArrayList<String> imageTitle;


    private HotCarAdapter hotCarAdapter;
    private List<HotCar> hotCarList;


    private AllCarAdapter allCarAdapter;
    private List<AllCar> allCarList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url_image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594713806900&di=eace4e1e4af79f7e2c000ec19b781add&imgtype=0&src=http%3A%2F%2Fcar0.autoimg.cn%2Fupload%2Fspec%2F11852%2Fu_20120127095328825264.jpg";
        String url_image1 = "https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00629-697.jpg";
        String url_image2 = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1850058553,3291038674&fm=26&gp=0.jpg";

        //设置图片资源:url或本地资源
        images = new ArrayList<>();
        images.add(url_image);
        images.add(url_image1);
        images.add(url_image2);
        //设置图片标题:自动对应
        imageTitle = new ArrayList<>();
        imageTitle.add("捷豹XF");
        imageTitle.add("吉利SUV");
        imageTitle.add("别克");

        hotCarList = new ArrayList<>();
        hotCarList.add(new HotCar(url_image, "捷豹XF", " 北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观 "));
        hotCarList.add(new HotCar(url_image1, "吉利SUV", " 北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观 "));
        hotCarList.add(new HotCar(url_image2, "别克", " 北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观 "));
        hotCarAdapter = new HotCarAdapter(R.layout.item_hotcar, hotCarList);


        allCarList = new ArrayList<>();
//        allCarList.add(new AllCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观"));

//        for (int i = 0; i < 5; i++) {
//            allCarList.add(new AllCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观"));
//        }
        allCarAdapter = new AllCarAdapter(R.layout.item_allcar, allCarList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        bind = ButterKnife.bind(this, view);


        LinearLayoutManager layoutManager_allcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvAllCar.setLayoutManager(layoutManager_allcar);//设置view视图为网格布局


        View headerView = getLayoutInflater().inflate(R.layout.view_allcar_header, rvAllCar, false);
        Banner bannerRecommend = headerView.findViewById(R.id.banner_recommend);
        RecyclerView rvHotCar = headerView.findViewById(R.id.rv_hot_car);


        bannerRecommend.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);//默认为:Banner.NOT_INDICATOR(不显示指示器和标题) 1. Banner.CIRCLE_INDICATOR    显示圆形指示器  2. Banner.NUM_INDICATOR   显示数字指示器  3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题  4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
        bannerRecommend.setImageLoader(new ImageLoader() {//设置图片加载器
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.ic_launcher_background)//图片加载出来前，显示的图片
                        .fallback(R.drawable.ic_launcher_background) //url为空的时候,显示的图片
                        .error(R.drawable.ic_launcher_background);//图片加载失败后，显示的图片
                Glide.with(context)
                        .load(path)
                        .apply(options)
                        .into(imageView);
            }
        });
        bannerRecommend.setBannerTitles(imageTitle);//设置标题集合（当banner样式有显示title时）
        bannerRecommend.setIndicatorGravity(BannerConfig.CENTER); //设置轮播样式.可选样式:Banner.LEFT 指示器居左 Banner.CENTER 指示器居中 Banner.RIGHT 指示器居右
        bannerRecommend.setViewPagerIsScroll(true);//设置是否允许手动滑动轮播图
        bannerRecommend.isAutoPlay(true);//设置是否自动轮播（不设置则默认自动）
        bannerRecommend.setDelayTime(1500); //设置轮播图片间隔时间（不设置默认为2000）
        //所有设置参数方法都放在此方法之前执行
        bannerRecommend.setImages(images);//设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        bannerRecommend.setOnBannerListener(new OnBannerListener() { // 轮播图点击事件
            @Override
            public void OnBannerClick(int position) {
            }
        });
        bannerRecommend.start();//开始轮播


        hotCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个HotCar");
            }
        });
        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false /*是否倒序*/);
        rvHotCar.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局
        rvHotCar.setAdapter(hotCarAdapter);//设置adapter
        rvHotCar.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//添加垂直分割线


        allCarAdapter.addHeaderView(headerView);//将头布局添加到header
        allCarAdapter.setEmptyView(R.layout.recyer_empty, rvAllCar);
        allCarAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空
        allCarAdapter.setPreLoadNumber(1);//预加载
        allCarAdapter.setEnableLoadMore(true); // 开启或关闭上拉加载功能
        allCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个AllCar");
                AllCar allCar = allCarList.get(position);
                ARouter.getInstance()
                        .build("/app/DetailCarActivity")
                        .withString("car_name",allCar.getText())
                        .withString("price",allCar.getPrice())
                        .withString("content",allCar.getContent())
                        .withString("logo_url",allCar.getUrl())
                        .navigation();
            }
        });
        //设置上拉加载事件
        allCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpManager.getInstance()
                        .getTemporaryService("http://apis.juhe.cn/cxdq/",ApiService.class)
                        .brand("A","739bfd0ff1bd80f762c6c9fd43134f0d")
                        .subscribeOn(Schedulers.io())//工作线程处理io
                        .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HomeFragment.this))) //自动取消订阅,防止内存泄漏
                        .subscribe(new Observer<AllCarList>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }
                            @Override
                            public void onNext(AllCarList resp) {

                                Logger.d("返回值:"+resp.getResult());
                                if (resp.getError_code() == 0){
                                    List<AllCarList.ResultBean> result = resp.getResult();
                                    for (AllCarList.ResultBean bean : result){
                                        AllCar allCar = new AllCar();
                                        allCar.setUrl(bean.getBrand_logo());
                                        allCar.setPrice("未报价");
                                        allCar.setText(bean.getBrand_name());
                                        allCar.setContent("无");
                                        allCarList.add(allCar);
                                    }
                                    allCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }else { //失败
                                    allCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                //主动调用加载失败来停止加载（该方法会提示加载失败）
                                allCarAdapter.loadMoreFail();
                            }
                            @Override
                            public void onComplete() {
                                allCarAdapter.loadMoreComplete();//主动调用加载完成，停止加载
                            }
                        });
            }
        },rvAllCar);
        rvAllCar.setAdapter(allCarAdapter);//设置adapter
        rvAllCar.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
                    .getTemporaryService("http://apis.juhe.cn/cxdq/",ApiService.class)
                    .brand("A","739bfd0ff1bd80f762c6c9fd43134f0d")
                    .subscribeOn(Schedulers.io())//工作线程处理io
                    .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(HomeFragment.this))) //自动取消订阅,防止内存泄漏
                    .subscribe(new Observer<AllCarList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(AllCarList resp) {
                            if (resp.getError_code() == 0){
                                allCarList.clear();

                                List<AllCarList.ResultBean> result = resp.getResult();
                                for (AllCarList.ResultBean bean : result){
                                    AllCar allCar = new AllCar();
                                    allCar.setUrl(bean.getBrand_logo());
                                    allCar.setPrice("未报价");
                                    allCar.setText(bean.getBrand_name());
                                    allCar.setContent("无");
                                    allCarList.add(allCar);
                                }
                                allCarAdapter.notifyDataSetChanged();
                            }else { //失败

                            }
                        }
                        @Override
                        public void onError(Throwable e) {
                            //主动调用加载失败来停止加载（该方法会提示加载失败）
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

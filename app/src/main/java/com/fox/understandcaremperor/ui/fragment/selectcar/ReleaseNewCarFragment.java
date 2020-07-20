package com.fox.understandcaremperor.ui.fragment.selectcar;

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
import com.fox.understandcaremperor.adapter.ReleaseNewCarAdapter;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.AllCarList;
import com.fox.understandcaremperor.mode.OldCar;
import com.fox.understandcaremperor.mode.ReleaseNewCar;
import com.fox.understandcaremperor.net.ApiService;
import com.fox.understandcaremperor.ui.fragment.HomeFragment;
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
 * 发布新车
 */
public class ReleaseNewCarFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.rv_release_newcar)
    RecyclerView rvReleaseNewcar;
    @BindView(R.id.swipefreshLayout)
    SwipeRefreshLayout swipefreshLayout;


    private List<ReleaseNewCar> releaseNewCars;
    private ReleaseNewCarAdapter releaseNewCarAdapter;

    public ReleaseNewCarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        releaseNewCars = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            String url_image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594713806900&di=eace4e1e4af79f7e2c000ec19b781add&imgtype=0&src=http%3A%2F%2Fcar0.autoimg.cn%2Fupload%2Fspec%2F11852%2Fu_20120127095328825264.jpg";
//            releaseNewCars.add(new ReleaseNewCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观"));
//        }
        releaseNewCarAdapter = new ReleaseNewCarAdapter(R.layout.item_releasenewcar, releaseNewCars);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_release_new_car, container, false);
        bind = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvReleaseNewcar.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局

        releaseNewCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个NewCar");
                ReleaseNewCar releaseNewCar = releaseNewCars.get(position);
                ARouter.getInstance()
                        .build("/app/DetailCarActivity")
                        .withString("car_name",releaseNewCar.getText())
                        .withString("price",releaseNewCar.getPrice())
                        .withString("content",releaseNewCar.getContent())
                        .withString("logo_url",releaseNewCar.getUrl())
                        .navigation();
            }
        });
        //设置上拉加载事件
        releaseNewCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpManager.getInstance()
                        .getTemporaryService("http://apis.juhe.cn/cxdq/", ApiService.class)
                        .brand("A","739bfd0ff1bd80f762c6c9fd43134f0d")
                        .subscribeOn(Schedulers.io())//工作线程处理io
                        .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ReleaseNewCarFragment.this))) //自动取消订阅,防止内存泄漏
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
                                        ReleaseNewCar allCar = new ReleaseNewCar();
                                        allCar.setUrl(bean.getBrand_logo());
                                        allCar.setPrice("未报价");
                                        allCar.setText(bean.getBrand_name());
                                        allCar.setContent("无");
                                        releaseNewCars.add(allCar);
                                    }
                                    releaseNewCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }else { //失败
                                    releaseNewCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                //主动调用加载失败来停止加载（该方法会提示加载失败）
                                releaseNewCarAdapter.loadMoreFail();
                            }
                            @Override
                            public void onComplete() {
                                releaseNewCarAdapter.loadMoreComplete();//主动调用加载完成，停止加载
                            }
                        });
            }
        },rvReleaseNewcar);

        releaseNewCarAdapter.setEmptyView(R.layout.recyer_empty, rvReleaseNewcar);
        releaseNewCarAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空
        releaseNewCarAdapter.setPreLoadNumber(1);//预加载
        releaseNewCarAdapter.setEnableLoadMore(true); // 开启或关闭上拉加载功能

        rvReleaseNewcar.setAdapter(releaseNewCarAdapter);//设置adapter
        rvReleaseNewcar.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(ReleaseNewCarFragment.this))) //自动取消订阅,防止内存泄漏
                    .subscribe(new Observer<AllCarList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(AllCarList resp) {
                            if (resp.getError_code() == 0){
                                releaseNewCars.clear();

                                List<AllCarList.ResultBean> result = resp.getResult();
                                for (AllCarList.ResultBean bean : result){
                                    ReleaseNewCar allCar = new ReleaseNewCar();
                                    allCar.setUrl(bean.getBrand_logo());
                                    allCar.setPrice("未报价");
                                    allCar.setText(bean.getBrand_name());
                                    allCar.setContent("无");
                                    releaseNewCars.add(allCar);
                                }
                                releaseNewCarAdapter.notifyDataSetChanged();
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
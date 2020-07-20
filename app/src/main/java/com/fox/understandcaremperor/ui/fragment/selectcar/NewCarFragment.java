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
import com.fox.understandcaremperor.adapter.NewCarAdapter;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.AllCarList;
import com.fox.understandcaremperor.mode.NewCar;
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
 * 新车
 */
public class NewCarFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.rv_new_car)
    RecyclerView rvNewCar;
    @BindView(R.id.swipefreshLayout)
    SwipeRefreshLayout swipefreshLayout;


    private List<NewCar> newCarList;
    private NewCarAdapter newCarAdapter;


    public NewCarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newCarList = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            String url_image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594713806900&di=eace4e1e4af79f7e2c000ec19b781add&imgtype=0&src=http%3A%2F%2Fcar0.autoimg.cn%2Fupload%2Fspec%2F11852%2Fu_20120127095328825264.jpg";
//            newCarList.add(new NewCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观"));
//        }
        newCarAdapter = new NewCarAdapter(R.layout.item_newcar, newCarList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_car, container, false);
        bind = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvNewCar.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局

        newCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个NewCar");
                NewCar newCar = newCarList.get(position);
                ARouter.getInstance()
                        .build("/app/DetailCarActivity")
                        .withString("car_name",newCar.getText())
                        .withString("price",newCar.getPrice())
                        .withString("content",newCar.getContent())
                        .withString("logo_url",newCar.getUrl())
                        .navigation();
            }
        });
        //设置上拉加载事件
        newCarAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                HttpManager.getInstance()
                        .getTemporaryService("http://apis.juhe.cn/cxdq/",ApiService.class)
                        .brand("A","739bfd0ff1bd80f762c6c9fd43134f0d")
                        .subscribeOn(Schedulers.io())//工作线程处理io
                        .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                        .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(NewCarFragment.this))) //自动取消订阅,防止内存泄漏
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
                                        NewCar newCar = new NewCar();
                                        newCar.setUrl(bean.getBrand_logo());
                                        newCar.setPrice("未报价");
                                        newCar.setText(bean.getBrand_name());
                                        newCar.setContent("无");
                                        newCarList.add(newCar);
                                    }
                                    newCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }else { //失败
                                    newCarAdapter.loadMoreEnd();//数据全部加载完毕
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                //主动调用加载失败来停止加载（该方法会提示加载失败）
                                newCarAdapter.loadMoreFail();
                            }
                            @Override
                            public void onComplete() {
                                newCarAdapter.loadMoreComplete();//主动调用加载完成，停止加载
                            }
                        });
            }
        },rvNewCar);

        newCarAdapter.setEmptyView(R.layout.recyer_empty, rvNewCar);
        newCarAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空
        newCarAdapter.setPreLoadNumber(1);//预加载
        newCarAdapter.setEnableLoadMore(true); // 开启或关闭上拉加载功能

        rvNewCar.setAdapter(newCarAdapter);//设置adapter
        rvNewCar.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
                    .getTemporaryService("http://apis.juhe.cn/cxdq/", ApiService.class)
                    .brand("A","739bfd0ff1bd80f762c6c9fd43134f0d")
                    .subscribeOn(Schedulers.io())//工作线程处理io
                    .observeOn(AndroidSchedulers.mainThread())//主线程处理结果
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(NewCarFragment.this))) //自动取消订阅,防止内存泄漏
                    .subscribe(new Observer<AllCarList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(AllCarList resp) {
                            if (resp.getError_code() == 0){
                                newCarList.clear();

                                List<AllCarList.ResultBean> result = resp.getResult();
                                for (AllCarList.ResultBean bean : result){
                                    NewCar newCar = new NewCar();
                                    newCar.setUrl(bean.getBrand_logo());
                                    newCar.setPrice("未报价");
                                    newCar.setText(bean.getBrand_name());
                                    newCar.setContent("无");
                                    newCarList.add(newCar);
                                }
                                newCarAdapter.notifyDataSetChanged();
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
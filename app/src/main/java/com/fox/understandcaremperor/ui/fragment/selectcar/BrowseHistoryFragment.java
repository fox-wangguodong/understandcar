package com.fox.understandcaremperor.ui.fragment.selectcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.adapter.AllCarAdapter;
import com.fox.understandcaremperor.adapter.RecentBrowseCarAdapter;
import com.fox.understandcaremperor.mode.AllCar;
import com.fox.understandcaremperor.mode.RecentBrowseCar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 浏览历史
 */
public class BrowseHistoryFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.rv_recent_browse_car)
    RecyclerView rvRecentBrowseCar;

    private List<RecentBrowseCar> recentBrowseCarList;
    private RecentBrowseCarAdapter recentBrowseCarAdapter;


    public BrowseHistoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recentBrowseCarList = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            String url_image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594713806900&di=eace4e1e4af79f7e2c000ec19b781add&imgtype=0&src=http%3A%2F%2Fcar0.autoimg.cn%2Fupload%2Fspec%2F11852%2Fu_20120127095328825264.jpg";
//            recentBrowseCarList.add(new RecentBrowseCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观"));
//        }
        recentBrowseCarAdapter = new RecentBrowseCarAdapter(R.layout.item_recent_browse_car, recentBrowseCarList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_history, container, false);
        bind = ButterKnife.bind(this, view);

        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvRecentBrowseCar.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局

        recentBrowseCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个HotCar");
            }
        });

        recentBrowseCarAdapter.setEmptyView(R.layout.recyer_empty, rvRecentBrowseCar);
        recentBrowseCarAdapter.setHeaderFooterEmpty(true, true);//两个参数，前面是允许头和空，后面是允许尾和空

        rvRecentBrowseCar.setAdapter(recentBrowseCarAdapter);//设置adapter
        rvRecentBrowseCar.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
}
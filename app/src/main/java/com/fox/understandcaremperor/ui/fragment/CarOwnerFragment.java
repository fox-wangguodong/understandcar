package com.fox.understandcaremperor.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.ui.fragment.carowner.HotNewsFragment;
import com.fox.understandcaremperor.ui.fragment.carowner.HotTopicFragment;
import com.fox.understandcaremperor.ui.fragment.carowner.RecentBrowseFragment;
import com.fox.understandcaremperor.ui.fragment.carowner.ReleaseNewsFragment;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarOwnerFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.tablayout_title)
    TabLayout tablayoutTitle;
    @BindView(R.id.pager)
    ViewPager fragmentPager;


    private List<String> titleList;
    private List<Fragment> fragmentList;
    private TabAdapter tabAdapter;

    public CarOwnerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        titleList = new ArrayList<>();
        titleList.add("热门话题");
        titleList.add("热门动态");
        titleList.add("发布动态");
        titleList.add("最近浏览");

        fragmentList = new ArrayList<>();
        fragmentList.add(new HotTopicFragment());
        fragmentList.add(new HotNewsFragment());
        fragmentList.add(new ReleaseNewsFragment());
        fragmentList.add(new RecentBrowseFragment());
        tabAdapter = new TabAdapter(getChildFragmentManager(), fragmentList, titleList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_owner, container, false);
        bind = ButterKnife.bind(this, view);

        fragmentPager.setOffscreenPageLimit(3);//预加载3个页面,总共有4个,为了防止重复加载fragment
        fragmentPager.setAdapter(tabAdapter);// 将适配器设置进ViewPager
        tablayoutTitle.setupWithViewPager(fragmentPager); // 将ViewPager与TabLayout相关联
        tablayoutTitle.getTabAt(0).select();//默认页面

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


    //自定义Adapter实现Fragment切换
    public static class TabAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<String> titleList;

        public TabAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleList) {
            super(fm);
            this.fragmentList = fragments;
            this.titleList = titleList;
        }

        @NotNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}

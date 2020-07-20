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
import com.fox.understandcaremperor.ui.fragment.selectcar.BrowseHistoryFragment;
import com.fox.understandcaremperor.ui.fragment.selectcar.ConditionalSelectFragment;
import com.fox.understandcaremperor.ui.fragment.selectcar.NewCarFragment;
import com.fox.understandcaremperor.ui.fragment.selectcar.OldCarFragment;
import com.fox.understandcaremperor.ui.fragment.selectcar.ReleaseNewCarFragment;
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
public class SelectCarFragment extends Fragment {

    Unbinder bind;
    @BindView(R.id.tablayout_title)
    TabLayout tablayoutTitle;
    @BindView(R.id.pager)
    ViewPager pager;


    private List<String> titleList;
    private List<Fragment> fragmentList;
    private CarOwnerFragment.TabAdapter tabAdapter;

    public SelectCarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化数据
        titleList = new ArrayList<>();
        titleList.add("新车");
        titleList.add("二手车");
        titleList.add("新车发布");
        titleList.add("条件选车");
        titleList.add("浏览历史");

        fragmentList = new ArrayList<>();
        fragmentList.add(new NewCarFragment());
        fragmentList.add(new OldCarFragment());
        fragmentList.add(new ReleaseNewCarFragment());
        fragmentList.add(new ConditionalSelectFragment());
        fragmentList.add(new BrowseHistoryFragment());
        tabAdapter = new CarOwnerFragment.TabAdapter(getChildFragmentManager(), fragmentList, titleList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_car, container, false);
        bind = ButterKnife.bind(this, view);

        pager.setOffscreenPageLimit(4);//预加载4个页面,总共有5个,为了防止重复加载fragment
        pager.setAdapter(tabAdapter);// 将适配器设置进ViewPager
        tablayoutTitle.setupWithViewPager(pager); // 将ViewPager与TabLayout相关联
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

package com.fox.understandcaremperor.ui.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.ui.BaseActivity;
import com.fox.understandcaremperor.ui.fragment.CarOwnerFragment;
import com.fox.understandcaremperor.ui.fragment.HomeFragment;
import com.fox.understandcaremperor.ui.fragment.MyFragment;
import com.fox.understandcaremperor.ui.fragment.SelectCarFragment;
import com.fox.understandcaremperor.ui.view.FragmentViewPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@Route(path = "/app/MainActivity")
public class MainActivity extends BaseActivity {

    @BindView(R.id.pager)
    FragmentViewPager fragmentViewPager;
    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new CarOwnerFragment());
        fragments.add(new SelectCarFragment());
        fragments.add(new MyFragment());
        fragmentViewPager.setAdapter(new FragmentViewPager.Adapter(getSupportFragmentManager(), fragments));
        fragmentViewPager.setScanScroll(false);//禁止通过滑动切换fragment
        fragmentViewPager.setCache(3);//预加载3个页面,总共有4个,为了防止重复加载fragment
        fragmentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                bottomNavigationView.setSelectedItemId(position);
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);//底部导航栏选中事件
        bottomNavigationView.setSelectedItemId(R.id.item_home);//默认项
    }

    // 底部导航栏监听实现
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    fragmentViewPager.setCurrentItem(0, true);
                    return true;
                case R.id.item_car_owner:
                    fragmentViewPager.setCurrentItem(1, true);
                    return true;
                case R.id.item_select_cat:
                    fragmentViewPager.setCurrentItem(2, true);
                    return true;
                case R.id.item_my:
                    fragmentViewPager.setCurrentItem(3, true);
                    return true;
            }
            return false;
        }
    };
}

package com.fox.understandcaremperor.ui.fragment.selectcar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.adapter.AllCarAdapter;
import com.fox.understandcaremperor.mode.AllCar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 条件选车
 */
public class ConditionalSelectFragment extends Fragment {

    Unbinder bind;


    @BindView(R.id.spinner_car_name)
    Spinner spinnerCarName;

    @BindView(R.id.spinner_car_price)
    Spinner spinnerCarPrice;

    @BindView(R.id.rv_selected_car)
    RecyclerView rvSelectedCar;

    private List<String> carList;//型号区间
    private ArrayAdapter<String> carAdapter;

    private List<String> priceList;//价格区间
    private ArrayAdapter<String> priceAdapter;


    private List<AllCar> allCarList;
    private AllCarAdapter allCarAdapter;


    public ConditionalSelectFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        carList = new ArrayList<>();
        carList.add("全部");
        carList.add("捷豹XF");
        carList.add("宝马");
        carList.add("奔驰");
        carList.add("奥迪");
        carAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_dropdown_item,carList);

        priceList = new ArrayList<>();
        priceList.add("全部");
        priceList.add("0-10万");
        priceList.add("10-20万");
        priceList.add("20-50万");
        priceList.add("50-100万");
        priceList.add("100万以上");
        priceAdapter = new ArrayAdapter<String>(Objects.requireNonNull(getContext()),android.R.layout.simple_spinner_dropdown_item,priceList);

        allCarList = new ArrayList<>();
//        for (int i = 0; i < 50; i++) {
//            String url_image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1594713806900&di=eace4e1e4af79f7e2c000ec19b781add&imgtype=0&src=http%3A%2F%2Fcar0.autoimg.cn%2Fupload%2Fspec%2F11852%2Fu_20120127095328825264.jpg";
//
//            AllCar car = new AllCar(url_image, "捷豹XF", "18万", "北京中进捷旺>捷豹>捷豹XF>2012款 Supercharged>车身外观");
//            car.setPrice(""+18+"万");
//            allCarList.add(car);
//        }
        allCarAdapter = new AllCarAdapter(R.layout.item_allcar, allCarList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conditional_select, container, false);
        bind = ButterKnife.bind(this, view);

        //型号选择监听事件
        spinnerCarName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String car = carList.get(position);
                Logger.d("car:"+car);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //价格选择监听事件
        spinnerCarPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String price = carList.get(position);
                Logger.d("price:"+price);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerCarName.setAdapter(carAdapter);
        spinnerCarName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("点击了:"+carAdapter.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerCarPrice.setAdapter(priceAdapter);
        spinnerCarPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Logger.d("点击了:"+priceAdapter.getItem(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        allCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("点击了第" + position + "个HotCar");
            }
        });
        LinearLayoutManager layoutManager_hotcar = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false /*是否倒序*/);
        rvSelectedCar.setLayoutManager(layoutManager_hotcar);//设置view视图为网格布局
        rvSelectedCar.setAdapter(allCarAdapter);//设置adapter
        rvSelectedCar.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));//添加垂直分割线

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
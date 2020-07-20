package com.fox.understandcaremperor.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.fox.understandcaremperor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {


    Unbinder bind;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_backup_car)
    TextView tvBackupCar;
    @BindView(R.id.tv_feedback)
    TextView tvFeedback;
    @BindView(R.id.tv_attention_car)
    TextView tvAttentionCar;
    @BindView(R.id.tv_user_agreement)
    TextView tvUserAgreement;
    @BindView(R.id.tv_privacy_policy)
    TextView tvPrivacyPolicy;
    @BindView(R.id.label)
    TextView label;

    public MyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        bind = ButterKnife.bind(this, view);


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

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        ARouter.getInstance()
                .build("/app/LoginActivity")
                .navigation();
    }


    @OnClick(R.id.iv_head)
    public void onIvHeadClicked() {
//        ArrayList<Uri> docPaths = new ArrayList<>();
//        FilePickerBuilder.getInstance()
//                .setMaxCount(10)
//                .setSelectedFiles(docPaths)
//                .setActivityTheme(R.style.AppTheme)
//                .pickFile(this);
    }

    @OnClick(R.id.tv_user_agreement)
    public void onTvUserAgreementClicked() {
        ARouter.getInstance()
                .build("/app/UserAgreementActivity")
                .navigation();
    }

    @OnClick(R.id.tv_privacy_policy)
    public void onTvPrivacyPolicyClicked() {
        ARouter.getInstance()
                .build("/app/PrivacyPolicyActivity")
                .navigation();
    }

    @OnClick(R.id.tv_feedback)
    public void onFeedbackClicked() {
        ARouter.getInstance()
                .build("/app/UserFeedbackActivity")
                .navigation();
    }

    @OnClick(R.id.tv_backup_car)
    public void onTvBackupCarClicked() {

    }

    @OnClick(R.id.tv_attention_car)
    public void onTvAttentionCarClicked() {
    }
}

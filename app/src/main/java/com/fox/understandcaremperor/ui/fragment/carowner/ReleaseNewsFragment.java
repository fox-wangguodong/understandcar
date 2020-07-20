package com.fox.understandcaremperor.ui.fragment.carowner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.fox.understandcaremperor.R;
import com.fox.understandcaremperor.util.GlideEngine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 发布动态
 */
public class ReleaseNewsFragment extends Fragment {

    Unbinder bind;

    @BindView(R.id.btn_release)
    FloatingActionButton btnRelease;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.iv_content_url)
    ImageView ivContentUrl;

    private String contentUrl;//记录上传的图片地址

    public ReleaseNewsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_release_news, container, false);
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

    @OnClick(R.id.iv_content_url)
    public void onContentUrlClicked() {
        //加载相册和相机
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .minSelectNum(1)
                .maxSelectNum(1)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        for (LocalMedia media : result) {
                            Logger.d("media:" + media.getPath());
                            Glide.with(getActivity())
                                    .load(media.getPath())
                                    .into(ivContentUrl);
                            contentUrl = media.getPath();
                        }
                    }
                    @Override
                    public void onCancel() {
                        Logger.d("已取消选择");
                    }
                });
    }

    @OnClick(R.id.btn_release)
    public void onBtnReleaseClicked() {
        String content = etContent.getText().toString();
        if (content.equals("")){
            Toast.makeText(getContext(), "消息不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contentUrl==null || contentUrl.equals("")){
            Toast.makeText(getContext(), "需要上传附图", Toast.LENGTH_SHORT).show();
            return;
        }

        Logger.d("发布消息:"+content);
        Toast.makeText(getContext(), "发布成功!", Toast.LENGTH_SHORT).show();


        Glide.with(getActivity())
                .load(R.drawable.add_image)
                .into(ivContentUrl);
        contentUrl = null;
        etContent.setText("");
    }

}
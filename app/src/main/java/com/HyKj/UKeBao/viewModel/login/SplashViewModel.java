package com.HyKj.UKeBao.viewModel.login;

import android.databinding.Bindable;
import android.text.TextUtils;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.login.SplashModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.SplashActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SplashViewModel extends BaseViewModel {

    private SplashModel mModel;

    @Bindable
    public String erroInfo;

    @Bindable
    public String splash_imagerUrl="";//图片地址

    public String text;

    @Bindable
    public boolean jump_flag = false;//跳转判断标记

    @Bindable
    public SplashActivity splashActivity;

    public SplashViewModel(SplashModel model, SplashActivity activity) {
        splashActivity=activity;

        mModel = model;

        mModel.setView(this);
    }

    //获取动态闪屏页背景图
    public void getBackground() {
        mModel.getBackground();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.Login_getSplashBackGround) {
            splash_imagerUrl = (String) data.t;

            jump_flag=true;

            notifyPropertyChanged(BR.splash_imagerUrl);

            notifyPropertyChanged(BR.splashActivity);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        erroInfo = erroinfo;

        LogUtil.d("erro" + erroInfo);

        notifyPropertyChanged(BR.erroInfo);
    }
}

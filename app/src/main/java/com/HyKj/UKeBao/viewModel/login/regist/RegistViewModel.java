package com.HyKj.UKeBao.viewModel.login.regist;

import android.util.Log;

import com.HyKj.UKeBao.model.login.baen.RegistInfo;
import com.HyKj.UKeBao.model.login.regist.RegistModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.regist.RegistActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;


/**
 * Created by Administrator on 2016/8/23.
 */
public class RegistViewModel extends BaseViewModel {

    public RegistModel mModel;

    public RegistActivity mActivity;

    public RegistInfo registInfo;//服务器返回注册结果

    public RegistViewModel(RegistActivity activity, RegistModel model) {
        mActivity = activity;
        mModel = model;
        mModel.setView(this);
    }

    //注册
    public void regist(String smsSecurityCode, long phone, String passWord) {
        mModel.regist(smsSecurityCode, phone, passWord);
    }
    //请求成功后回调该方法
    @Override
    public void onRequestSuccess(ModelAction data) {
        //回调成功后并且点击注册按钮
        if (data.action == Action.Login_Regist) {
            registInfo = (RegistInfo) data.t;

            mActivity.registSuccess(registInfo.msg);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast("注册界面网络请求失败");

        BufferCircleDialog.dialogcancel();

        Log.d("注册页面异常异常异常",erroinfo);
    }
}

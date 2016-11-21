package com.HyKj.UKeBao.viewModel.login.forgetPassword;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.HyKj.UKeBao.model.bean.BaseInfo;
import com.HyKj.UKeBao.model.login.forgetPassword.ForgetPasswordModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.forgetPassword.ForgetPasswordActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;


/**
 * Created by Administrator on 2016/8/26.
 */
public class ForgetPasswordViewModel extends BaseViewModel {

    private ForgetPasswordModel mModel;

    private ForgetPasswordActivity mActivity;

    private BaseInfo baseInfo;

    public ForgetPasswordViewModel(ForgetPasswordModel model, ForgetPasswordActivity activty) {
        mModel = model;
        mModel.setView(this);
        mActivity = activty;
    }

    //显示用户名
    public String displayUserName(SharedPreferences sp) {
        return sp.getString("lg_account", "");
    }

    //发送验证码
    public void isExistence(long phone) {
        mModel.isExistence(phone);
    }


    //找回密码
    public void forgetPassword(String code, String password, String phone,String confirm) {
        if (!TextUtils.isEmpty(code)&&!TextUtils.isEmpty(password)&&!TextUtils.isEmpty(phone)&&phone.length()>6) {
            BufferCircleDialog.show(mActivity,"提交中..",false,null);

            mModel.forgetPassword(code,password,phone);
        }else if(phone.length()<6){
            mActivity.toast("密码必须要大于6位数哦~");
        }else if (confirm.equals(password)){
            mActivity.toast("密码不一致~");
        }
        else {
            mActivity.toast("请填写完整的信息~");
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }
        if (data.action == Action.Login_ForgetPassword_getVerificationCode) {
            baseInfo = (BaseInfo) data.t;

            mActivity.toast(baseInfo.msg);
        }else if(data.action==Action.Login_ForgetPassword){
            String msg= (String) data.t;

            mActivity.toast(msg);
        }else if(data.action==Action.Login_ForgetPassword_isExistence){
            mActivity.getSecurityCode();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }
        mActivity.toast(erroinfo);
    }
}

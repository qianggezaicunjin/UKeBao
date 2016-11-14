package com.HyKj.UKeBao.viewModel.login;

import android.content.SharedPreferences;
import android.databinding.Bindable;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.login.LoginModel;
import com.HyKj.UKeBao.model.login.baen.LoginInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LoginViewModel extends BaseViewModel {

    public LoginModel mModel;

    @Bindable
    public LoginInfo loginInfo;

    public LoginActivity mActivity;

    public LoginViewModel(LoginModel model, LoginActivity activity) {
        mModel = model;

        mActivity = activity;

        mModel.setView(this);
    }

    //登陆验证
    public void userLogin(String account, String passwd, String identityId, int deviceType, String deviceNo) {
        mModel.userLogin(account, passwd, identityId, deviceType, deviceNo);
    }


    //当用户登陆成功时保存用户信息
    public void savaUserInfo(SharedPreferences.Editor editor, String passWord) {

        editor.putString("lg_account", loginInfo.rows.account);//存入账号

        editor.putString("lg_passwd", passWord);//存入密码

        editor.putString("token", loginInfo.rows.token);//token

        editor.putInt("isExamine", loginInfo.rows.isExamine);//审核状态

        editor.putString("businessStoreId", loginInfo.rows.businessStoreId);//店铺id

        editor.putString("businessStoreImage", loginInfo.rows.businessStoreImage);//商家店招

        editor.putString("businessName",loginInfo.rows.businessName);

        editor.putString("id", loginInfo.rows.id);//用户id

        editor.putString("ip", loginInfo.rows.ip);//ip地址

        editor.putString("name", loginInfo.rows.name);//用户昵称

        editor.putString("phone", loginInfo.rows.phone);//用户号码

        editor.putString("status", loginInfo.rows.status);

        editor.putString("companyTel", loginInfo.rows.companyTel);//客户电话

        editor.putString("integral", loginInfo.rows.integral);

        editor.putString("integralScale", loginInfo.rows.integralScale);

        editor.putString("recharge", loginInfo.rows.recharge);

        LogUtil.d("刷新token" + loginInfo.rows.token);

        MyApplication.token = loginInfo.rows.token;//把token赋值到全局

        editor.commit();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //用户登陆成功回调
        if (data.action == Action.Login_UserLogin) {

            loginInfo = (LoginInfo) data.t;

            //判断账号密码是否正确
            if (loginInfo.success) {
                mActivity.getData(loginInfo.msg, loginInfo.rows.isExamine);

                LogUtil.d("审核状态码" + loginInfo.rows.isExamine);
            } else {
                mActivity.getErroInfo(loginInfo.msg);
            }
            BufferCircleDialog.dialogcancel();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.getErroInfo(erroinfo);
    }


}

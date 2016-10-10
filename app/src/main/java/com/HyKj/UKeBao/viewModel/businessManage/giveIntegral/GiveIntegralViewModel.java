package com.HyKj.UKeBao.viewModel.businessManage.giveIntegral;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.businessManage.giveIntegral.GiveIntegralModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.giveIntegral.GiveIntegralActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;


/**
 * Created by Administrator on 2016/9/26.
 */
public class GiveIntegralViewModel extends BaseViewModel {
    private GiveIntegralActivity mActivity;

    private GiveIntegralModel mModel;

    public GiveIntegralViewModel(GiveIntegralModel model, GiveIntegralActivity activity) {
        mModel = model;

        mActivity = activity;

        mModel.setView(this);
    }

    //获取用户名
    public void getUserName(String phone) {
        mModel.getUserName(phone);
    }

    //赠送积分
    public void sendIntegral(String money, String phone, double cash, double integral, String businessName) {
        if (TextUtils.isEmpty(phone)) {

            mActivity.toast("请输入赠送账号", mActivity);

            return;

        }
        if (money.equals("") && money.length() == 0) {
            mActivity.toast("请输入金额", mActivity);

            return;
        }
        BufferCircleDialog.show(mActivity, "请稍候~", true, null);
        mModel.sendIntegral(cash, integral, phone, businessName);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.BusinessManage_GiveIntegral_getUserName) {
            String userName = (String) data.t;

            mActivity.setUserName(userName);
        } else if (data.action == Action.BusinessManage_SendIntegral) {
            String msg = (String) data.t;

            mActivity.sendIntegral();

            mActivity.toast(msg,mActivity);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo, mActivity);
    }
}

package com.HyKj.UKeBao.viewModel.userInfoManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.userInfoManage.MyBankCardModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.userInfoManage.MyBankCardActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MyBankCardViewModel extends BaseViewModel {
    private MyBankCardActivity mActivity;

    private MyBankCardModel mModel;

    @Bindable
    public BusinessInfo businessInfo;

    public MyBankCardViewModel(MyBankCardActivity activity, MyBankCardModel model) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //获取店铺信息
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.UserInfoManage_BankInfo) {
            if(data.t.equals("未绑定")){
                mActivity.notBinding();
            }else {
                mActivity.binding();

                businessInfo= (BusinessInfo) data.t;

                notifyPropertyChanged(BR.businessInfo);

                BufferCircleDialog.dialogcancel();
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if(BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }

        mActivity.toast(erroinfo, mActivity);
    }
}

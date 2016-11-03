package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.userInfoManage.WithdrawalsModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.userInfoManage.WithdrawalsActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;
import com.baidu.platform.comapi.map.A;

/**
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsViewModel extends BaseViewModel{
    private WithdrawalsActivity mActivity;

    private WithdrawalsModel mModel;

    public WithdrawalsViewModel(WithdrawalsActivity activity,WithdrawalsModel model){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //申请提现
    public void withdrawals(String businessStoreId, String demand_amount) {
        mModel.withdrawals(businessStoreId,demand_amount);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }

        if(data.action== Action.UserInfoManage_Withdrawals){
            mActivity.toast((String) data.t,mActivity);

            mActivity.finish();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if(BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }

        mActivity.toast(erroinfo,mActivity);
    }
}

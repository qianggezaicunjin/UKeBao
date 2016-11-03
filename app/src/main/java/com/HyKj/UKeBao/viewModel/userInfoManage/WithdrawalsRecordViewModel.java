package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.userInfoManage.WithdrawalsRecordModel;
import com.HyKj.UKeBao.model.userInfoManage.bean.WithdrawalsRecord;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.userInfoManage.WithdrawalsRecordActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsRecordViewModel extends BaseViewModel{
    private WithdrawalsRecordActivity mActivity;

    private WithdrawalsRecordModel mModel;

    public WithdrawalsRecordViewModel(WithdrawalsRecordModel model,WithdrawalsRecordActivity activity){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取提现记录
    public void getWithdrawlsRecord(String businessStoreId, int page, int rows) {
        mModel.getWithdrawlsRecord(businessStoreId,page,rows);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.UserInfoManage_getWithdrawalsRecord){
            if(BufferCircleDialog.isShowDialog()){
                BufferCircleDialog.dialogcancel();
            }

            mActivity.setRecordData((List<WithdrawalsRecord>)data.t);
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

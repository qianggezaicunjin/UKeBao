package com.HyKj.UKeBao.viewModel;

import com.HyKj.UKeBao.model.marketingManage.ExchangRecordModel;
import com.HyKj.UKeBao.model.marketingManage.bean.Rows;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.ExchangRecordActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ExchangRecordViewModel extends BaseViewModel{

    private ExchangRecordActivity mActivity;

    private ExchangRecordModel mModel;

    public ExchangRecordViewModel(ExchangRecordActivity activity,ExchangRecordModel model){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取兑换记录
    public void getExchangRecord(int page, int rows, int businessStoreId) {
        mModel.getExchangRecord(page,rows,businessStoreId);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.UserInfoManage_GetExchangRecord){
            mActivity.setExchangRecordData((List<Rows>) data.t);
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

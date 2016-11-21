package com.HyKj.UKeBao.viewModel.businessManage.giveIntegral;


import com.HyKj.UKeBao.model.businessManage.bean.CashRecordInfo;
import com.HyKj.UKeBao.model.businessManage.bean.IntegralRecordInfo;
import com.HyKj.UKeBao.model.businessManage.giveIntegral.IntegralRecordModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.giveIntegral.IntegralRecordActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/27.
 */
public class IntegralRecordViewModel extends BaseViewModel {
    private IntegralRecordModel mModel;

    private IntegralRecordActivity mActivity;

    private boolean isCashFlag;

    public IntegralRecordViewModel(IntegralRecordModel model, IntegralRecordActivity activity) {
        mModel = model;

        mActivity = activity;

        mModel.setView(this);
    }

    //获取积分记录数据
    public void getRecordData(int page, int rows, int businessStoreId, String type, String isSend) {
        if (businessStoreId != -1) {
            mModel.getRecordData(page, rows, businessStoreId, type, isSend);
        }
    }
    //获取现金记录数据
    public void getCashRecordData(int businessStoreId,int page, int rows,boolean isCashRecord) {
        isCashFlag=isCashRecord;

        mModel.getCashRecordData(businessStoreId,page,rows);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.BusinessManage_GiveIntegral_getRecordData) {
            BufferCircleDialog.dialogcancel();

            List<IntegralRecordInfo> mList = (List<IntegralRecordInfo>) data.t;

            mActivity.getRecordData(mList);
        }else if(data.action == Action.BusinessMange_getCashRecord){
            BufferCircleDialog.dialogcancel();

            List<CashRecordInfo> mList = (List<CashRecordInfo>) data.t;

            mActivity.getCashRecord(mList);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo, mActivity);
        if(!isCashFlag) {
            mActivity.getRecordData(null);
        }else {
            mActivity.getCashRecord(null);
        }
    }


}

package com.HyKj.UKeBao.viewModel.businessManage.financialManagement;


import com.HyKj.UKeBao.model.businessManage.FinancialManagementModel;
import com.HyKj.UKeBao.model.businessManage.bean.FinancialManage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.financialManagement.FinancialManagementActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/9/27.
 */
public class FinancialManagementViewModel extends BaseViewModel {
    private FinancialManagementModel mModel;

    private FinancialManagementActivity mActivity;

    public FinancialManagementViewModel(FinancialManagementModel model, FinancialManagementActivity activity) {
        mModel = model;

        mActivity = activity;

        mModel.setView(this);
    }

    //根据开始结束时间获取财务数据
    public void getFinancialData(String startDate, String stopDate) {

        LogUtil.d("FinancialManagementViewModel"+"startDate:" + startDate + "stopDate" + stopDate);

        mModel.getFinancialData(startDate, stopDate);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.BusinessManage_getFinanicalData) {
            FinancialManage financialMange = (FinancialManage) data.t;

            LogUtil.d("财务管理信息数据为:"+financialMange.getRows());

            if(financialMange.getStatus().equals("0")){
                mActivity.setData(financialMange.getRows());
            }else {
                mActivity.toast(financialMange.getMsg());
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo);
    }
}


package com.HyKj.UKeBao.viewModel.businessManage.financialManagement;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.businessManage.financial.RealIncomeDetailModel;
import com.HyKj.UKeBao.model.businessManage.financial.bean.RealMoneyDetail;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.financialManagement.RealIncomeDetailActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/10.
 */
public class RealIncomeDetailViewModel extends BaseViewModel{
    private RealIncomeDetailActivity mActivity;

    private RealIncomeDetailModel mModel;

    @Bindable
    public RealMoneyDetail realMoneyDetail;

    public RealIncomeDetailViewModel(RealIncomeDetailModel model,RealIncomeDetailActivity activity){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取实收详情
    public void getRealDetail(String startTime, String endTime) {
        mModel.getRealDetail(startTime,endTime);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.BusinessManage_GetRealMoneyDetail){
            realMoneyDetail= (RealMoneyDetail) data.t;

            notifyPropertyChanged(BR.realMoneyDetail);

            BufferCircleDialog.dialogcancel();
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

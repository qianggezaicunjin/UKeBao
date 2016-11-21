package com.HyKj.UKeBao.viewModel.marketingManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.userInfoManage.VipFunctionModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/14.
 */
public class VipFunctionViewModel extends BaseViewModel{
    private VipFunctionModel mModel;

    @Bindable
    public String office_phone;

    @Bindable
    public int orderId=0;

    public VipFunctionViewModel(VipFunctionModel model){
        mModel=model;

        mModel.setView(this);
    }

    //正在申请成为vip
    public void applyVip() {
        mModel.applyVip();
    }

    //获取客服电话
    public void getOfficePhone(){
        mModel.getCustomerPhone();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取客服电话
        if(data.action== Action.UserInfoManage_GetCustomerPhone){
            BufferCircleDialog.dialogcancel();

            office_phone= (String) data.t;

            notifyPropertyChanged(BR.office_phone);
        }
        //申请vip
        else if(data.action==Action.MarketingManage_ApplyVip){
            BufferCircleDialog.dialogcancel();

            orderId= (int) data.t;

            notifyPropertyChanged(BR.orderId);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        this.erroInfo=erroinfo;

        notifyPropertyChanged(BR.erroInfo);
    }


}

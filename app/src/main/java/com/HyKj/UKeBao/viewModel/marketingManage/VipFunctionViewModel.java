package com.HyKj.UKeBao.viewModel.marketingManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.userInfoManage.VipFunctionModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/14.
 */
public class VipFunctionViewModel extends BaseViewModel{
    private VipFunctionModel mModel;

    @Bindable
    public String office_phone;

    public VipFunctionViewModel(VipFunctionModel model){
        mModel=model;

        mModel.setView(this);
    }

    //获取客服电话
    public void getOfficePhone(){
        mModel.getCustomerPhone();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.UserInfoManage_GetCustomerPhone){
            office_phone= (String) data.t;

            notifyPropertyChanged(BR.office_phone);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        this.erroInfo=erroinfo;

        notifyPropertyChanged(BR.erroInfo);
    }
}

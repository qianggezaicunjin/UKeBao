package com.HyKj.UKeBao.viewModel.businessManage.businessSettings;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessSettingsModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.businessSettings.BusinessSettingsActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BusinessSettingsViewModel extends BaseViewModel{

    private BusinessSettingsActivity mActivity;

    private BusinessSettingsModel mModel;

    @Bindable
    public BusinessInfo businessInfo;

    @Bindable
    public String businessImage;

    public BusinessSettingsViewModel(BusinessSettingsActivity activity,BusinessSettingsModel model){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取店铺信息
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取店铺信息请求成功后回调
        if(data.action== Action.BusinessManage_businessSettings_getbusinessInfo){
            businessInfo= (BusinessInfo) data.t;

            businessImage=businessInfo.businessStoreImages.get(0);

            mActivity.setBusinessInfo(businessInfo);

            LogUtil.d("店铺设置获取数据回调成功:"+businessInfo.toString());

            notifyPropertyChanged(BR.businessInfo);

            notifyPropertyChanged(BR.businessImage);

            BufferCircleDialog.dialogcancel();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo,mActivity);
    }


}

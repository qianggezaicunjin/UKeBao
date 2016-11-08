package com.HyKj.UKeBao.viewModel.userInfoManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.userInfoManage.LeftMenuFragmentModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.fragment.userInfoManage.LeftMenuFragment;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/29.
 */
public class LeftMenuFragmentViewModel extends BaseViewModel {
    private LeftMenuFragment mFragment;

    private LeftMenuFragmentModel mModel;

    @Bindable
    public BusinessInfo businessInfo;//店铺信息

    @Bindable
    public String businessImage;//店铺头像

    @Bindable
    public String customer_phone;//客户电话

    public LeftMenuFragmentViewModel(LeftMenuFragmentModel model, LeftMenuFragment fragment) {
        mFragment = fragment;

        mModel = model;

        mModel.setView(this);
    }

    //获取店铺信息
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }

    //获取客服电话
    public void getCustomerPhone() {
        mModel.getCustomerPhone();
    }

    //注销
    public void cancellation() {
        mModel.cancellation();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取店铺数据
        if (data.action == Action.UserInfoManage_GetBusinessInfo) {
            businessInfo = (BusinessInfo) data.t;

            businessImage = businessInfo.getBusinessStoreImages().get(0);

            notifyPropertyChanged(BR.businessInfo);

            notifyPropertyChanged(BR.businessImage);
        }
        //获取客服电话
        else if (data.action == Action.UserInfoManage_GetCustomerPhone) {
            customer_phone = (String) data.t;

            notifyPropertyChanged(BR.customer_phone);
        }
        //注销
        else if(data.action==Action.UserInfoManage_Cancellation){
            BufferCircleDialog.dialogcancel();

            mFragment.cancellation((String)data.t);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if(erroinfo.equals("注销失败~请检查网络")){
            BufferCircleDialog.dialogcancel();
        }

        mFragment.toast(erroinfo, mFragment.getActivity());
    }


}

package com.HyKj.UKeBao.viewModel.UserInfoManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.UserInfoManage.LeftMenuFragmentModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.fragment.userInfoManage.LeftMenuFragment;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/29.
 */
public class LeftMenuFragmentViewModel extends BaseViewModel{
    private LeftMenuFragment mFragment;

    private LeftMenuFragmentModel mModel;

    @Bindable
    public BusinessInfo businessInfo;//店铺信息

    @Bindable
    public String businessImage;//店铺头像

    public LeftMenuFragmentViewModel(LeftMenuFragmentModel model,LeftMenuFragment fragment){
        mFragment=fragment;

        mModel=model;

        mModel.setView(this);
    }

    //获取店铺信息
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取店铺数据
        if(data.action== Action.UserInfoManage_GetBusinessInfo){
            businessInfo= (BusinessInfo) data.t;

            businessImage=businessInfo.getBusinessStoreImages().get(0);

            notifyPropertyChanged(BR.businessInfo);

            notifyPropertyChanged(BR.businessImage);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mFragment.toast(erroinfo,mFragment.getActivity());
    }
}

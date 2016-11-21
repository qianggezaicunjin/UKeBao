package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.userInfoManage.BalanceModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.fragment.userInfoManage.BalanceRechargeFragment;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/12.
 */
public class BalanceViewModel extends BaseViewModel{
    private BalanceModel mModel;

    private BalanceRechargeFragment mFragment;

    public BalanceViewModel(BalanceRechargeFragment fragment, BalanceModel model){
        mModel=model;

        mFragment=fragment;

        mModel.setView(this);
    }

    //获取店铺信息
    public void getBusinessInfo(){
        mModel.getBusinessInfo();
    }

    //确认余额充值
    public void confirmRecharge(String integral, String cash) {
        mModel.confirmRecharge(integral,cash);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action == Action.UserInfoManage_GetBusinessInfo){

            mFragment.setCashData((BusinessInfo) data.t);
        }else if(data.action==Action.UserInfoManage_ConfirmRecharge_Balance){
            mFragment.confirmRecharge((String)data.t);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }

        mFragment.toast(erroinfo,mFragment.getContext());
    }


}

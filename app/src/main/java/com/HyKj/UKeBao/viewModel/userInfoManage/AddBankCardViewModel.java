package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.login.regist.RegistModel;
import com.HyKj.UKeBao.model.userInfoManage.AddBankCardModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.regist.RegistActivity;
import com.HyKj.UKeBao.view.activity.userInfoManage.AddBankActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;
import com.HyKj.UKeBao.viewModel.login.regist.RegistViewModel;

/**
 * Created by Administrator on 2016/11/9.
 */
public class AddBankCardViewModel extends BaseViewModel{
    private AddBankActivity mActivity;

    private AddBankCardModel mModel;

    public AddBankCardViewModel(AddBankCardModel model,AddBankActivity activity){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //添加银行卡
    public void addBankCard(String bankName, String bankNo, String name, String code) {
        mModel.addBankCard(bankName,bankNo,name,code);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action==Action.UserInfoManage_AddBankCard){
            BufferCircleDialog.dialogcancel();

            mActivity.toast((String) data.t,mActivity);

            mActivity.finish();
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

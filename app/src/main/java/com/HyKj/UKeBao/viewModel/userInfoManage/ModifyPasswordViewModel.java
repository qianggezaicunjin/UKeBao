package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.userInfoManage.ModifyPasswordModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.userInfoManage.ModifyPasswordActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ModifyPasswordViewModel extends BaseViewModel{
    private ModifyPasswordActivity mActivity;

    private ModifyPasswordModel mModel;

    public ModifyPasswordViewModel(ModifyPasswordActivity activity,ModifyPasswordModel model){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //提交修改
    public void commitNewPassword(String oldPassword, String newPassword, String confirmPassword) {
        mModel.commitNewPassword(oldPassword,newPassword,confirmPassword);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.UserInfoManage_ModifyPassword){
            BufferCircleDialog.dialogcancel();

            mActivity.toast((String) data.t,mActivity);

            mActivity.jump();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }

        mActivity.toast(erroinfo,mActivity);
    }
}

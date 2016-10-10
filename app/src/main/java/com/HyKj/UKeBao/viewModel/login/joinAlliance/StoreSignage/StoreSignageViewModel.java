package com.HyKj.UKeBao.viewModel.login.joinAlliance.StoreSignage;


import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.login.joinAlliance.StoreSignage.StoreSignageModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreSignage.StoreSignageActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.io.File;

/**
 * Created by Administrator on 2016/9/3.
 */
public class StoreSignageViewModel extends BaseViewModel {

    public StoreSignageModel mModel;

    public StoreSignageActivity mActivity;

    public StoreSignage storeSignage;

    public StoreSignageViewModel(StoreSignageModel model, StoreSignageActivity activity) {

        mModel = model;

        mActivity = activity;

        mModel.setView(this);

    }
    //提交图片到服务器
    public void uploadPictures(File file,int modelType){
        mModel.uploadPictures(file,modelType);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //上传图片
        if(data.action== Action.Login_SettledAlliance_uploadPictures){

            storeSignage= (StoreSignage) data.t;

            LogUtil.d("回调成功数据为"+storeSignage.getMsg());

            mActivity.toast(storeSignage.getMsg());

            mActivity.setBackResult();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo);
    }
}

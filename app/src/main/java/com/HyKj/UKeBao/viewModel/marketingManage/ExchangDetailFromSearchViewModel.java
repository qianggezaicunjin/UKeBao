package com.HyKj.UKeBao.viewModel.marketingManage;

import com.HyKj.UKeBao.model.marketingManage.ExchangDetailFromSearchModel;
import com.HyKj.UKeBao.model.marketingManage.bean.ExchangeInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.ExchangDetaliFromSearchActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ExchangDetailFromSearchViewModel extends BaseViewModel{

    private ExchangDetailFromSearchModel mModel;

    private ExchangDetaliFromSearchActivity mActivity;

    public ExchangDetailFromSearchViewModel(ExchangDetailFromSearchModel model,ExchangDetaliFromSearchActivity activity){
        mModel=model;

        mActivity=activity;

        mModel.setView(this);
    }
    //获取兑换信息
    public void getCodeInfo(int code) {
        mModel.getCodeInfo(code);
    }
    //确认收款
    public void confirmReceipt(int code) {
        mModel.confirmReceipt(code);
    }
    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action== Action.UserInfoManage_GetExchangInfo){
            mActivity.setExchangInfo((ExchangeInfo) data.t);
        }else if(data.action==Action.UserInfoManage_ConfirmReceipt){
            mActivity.confirmReceipt((String)data.t);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo,mActivity);
    }


}

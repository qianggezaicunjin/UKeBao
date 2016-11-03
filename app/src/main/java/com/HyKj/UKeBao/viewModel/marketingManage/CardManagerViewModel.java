package com.HyKj.UKeBao.viewModel.marketingManage;

import com.HyKj.UKeBao.model.marketingManage.CardManagerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardListInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.CardManagerActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class CardManagerViewModel extends BaseViewModel{

    private CardManagerActivity mActivity;

    private CardManagerModel mModel;


    public CardManagerViewModel(CardManagerModel model,CardManagerActivity activity){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取卡劵列表全部信息
    public void getAllCardInfo(int page, int rows) {
        mModel.getAllCardInfo(page,rows);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.MarketingManage_getCardListInfo){
            List<CardListInfo> cardListInfos= (List<CardListInfo>) data.t;

            mActivity.setData(cardListInfos);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo,mActivity);
    }
}

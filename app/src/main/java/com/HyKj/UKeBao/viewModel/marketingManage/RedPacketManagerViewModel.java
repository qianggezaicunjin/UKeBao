package com.HyKj.UKeBao.viewModel.marketingManage;

import com.HyKj.UKeBao.model.marketingManage.RedPacketManagerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketListInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.MarketingManage.RedPacketManagerActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class RedPacketManagerViewModel extends BaseViewModel{

    private RedPacketManagerActivity mActivity;

    private RedPacketManagerModel mModel;


    public RedPacketManagerViewModel(RedPacketManagerModel model, RedPacketManagerActivity activity){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //获取卡劵列表全部信息
    public void getAllRedPacketInfo(int page, int rows,int id) {
        mModel.getAllRedPacketInfo(page,rows,id);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.MarketingManage_getRedPacketListInfo){
            List<RedPacketListInfo> redListInfo= (List<RedPacketListInfo>) data.t;

            mActivity.setData(redListInfo);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo,mActivity);
    }
}

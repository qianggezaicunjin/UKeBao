package com.HyKj.UKeBao.viewModel.marketingManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.marketingManage.RedPacketDetailModel;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetail;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacket_collect_record;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.RedPacketDetailActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/27.
 */
public class RedPacketDetailViewModel extends BaseViewModel {

    private RedPacketDetailActivity mActivity;

    private RedPacketDetailModel mModel;

    @Bindable
    public RedPacketDetail redPacketDetail;

    @Bindable
    public RedPacket_collect_record recordList;

    public RedPacketDetailViewModel(RedPacketDetailModel model, RedPacketDetailActivity activity) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //获取单个红包详情
    public void getSingRedPacketDetail(int integer) {
        mModel.getSingRedPacketDetail(integer);
    }

    //获取用户领取红包记录
    public void collect_records(int id, int page, int rows) {
        mModel.collect_records(id, page, rows);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.MarketingManage_GetSingRedPacketDetail) {
            redPacketDetail = (RedPacketDetail) data.t;

            notifyPropertyChanged(BR.redPacketDetail);

        } else if (data.action == Action.MarketingManage_RedPacket_Collect_Record) {
            recordList = (RedPacket_collect_record) data.t;

            LogUtil.d("红包领取记录回调:" + recordList.toString());

            notifyPropertyChanged(BR.recordList);

            mActivity.setDataList(recordList);

        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo, mActivity);
    }
}

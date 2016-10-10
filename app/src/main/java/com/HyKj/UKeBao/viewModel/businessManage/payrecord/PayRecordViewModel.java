package com.HyKj.UKeBao.viewModel.businessManage.payrecord;

import com.HyKj.UKeBao.model.businessManage.payrecord.PayRecordModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.util.TimeCount;
import com.HyKj.UKeBao.view.activity.businessManage.payrecord.PayRecordActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/9/29.
 */
public class PayRecordViewModel extends BaseViewModel {
    private PayRecordActivity mActivity;

    private PayRecordModel mModel;

    public PayRecordViewModel(PayRecordModel model, PayRecordActivity activity) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    public void requireDataFromWeb(int statuss, int page, int rows) {
        TimeCount count = new TimeCount(6000, 1000);

        count.start();

        BufferCircleDialog.show(mActivity, "请稍候...", false, null);

        if (statuss == -1) {
            mModel.getPayRecord(page, rows);
        } else {
            mModel.getPayRecord(statuss, page, rows);
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        BufferCircleDialog.dialogcancel();

        //获取买单信息
        if (data.action == Action.BusinessManage_getPayRecord) {
            mActivity.getData(data.t);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivity.toast(erroinfo, mActivity);
    }
}

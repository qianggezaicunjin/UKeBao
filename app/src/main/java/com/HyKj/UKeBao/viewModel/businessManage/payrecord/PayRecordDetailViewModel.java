package com.HyKj.UKeBao.viewModel.businessManage.payrecord;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.businessManage.bean.OrderRecord;
import com.HyKj.UKeBao.model.businessManage.payrecord.PayDetailsModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.payrecord.PayDetailsActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/7.
 */
public class PayRecordDetailViewModel extends BaseViewModel {
    private PayDetailsActivity mActivity;

    private PayDetailsModel mModel;

    public PayRecordDetailViewModel(PayDetailsActivity activity, PayDetailsModel model) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //判断是否可退款
    public boolean isDisplayRefund(OrderRecord orderRecord) {
        if (orderRecord.isCanCancel()) {
            return true;
        } else {
            return false;
        }
    }

    //退款操作
    public void refund(String sercet,String orderId) {
        if (TextUtils.isEmpty(sercet)) {
            mActivity.toast("请输入密码~", mActivity);
        }else {
            mModel.refund(sercet,orderId);
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //退款
        if(data.action== Action.BusinessManage_refund){
            mActivity.toast((String) data.t,mActivity);

            mActivity.refundSuccess();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo,mActivity);
    }
}

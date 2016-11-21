package com.HyKj.UKeBao.viewModel.userInfoManage;

import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.model.userInfoManage.CashChargeModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.customView.BufferCircleDialog;
import com.HyKj.UKeBao.view.fragment.userInfoManage.CashChargeFragment;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/12.
 */
public class CashChargeViewModel extends BaseViewModel {
    private CashChargeModel mModel;

    private CashChargeFragment mFragment;

    public CashChargeViewModel(CashChargeFragment fragment, CashChargeModel model) {
        mModel = model;

        mFragment = fragment;

        mModel.setView(this);
    }

    //生成支付信息
    public void createPayment(String recharge_integral, int payType) {
        mModel.createPayment(recharge_integral, payType);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        LogUtil.d("现金充值viewModel，回调成功" + data.t.toString());

        //支付宝支付
        if (data.action == Action.UserInfoManage_CashCharge_Alipay) {
            BufferCircleDialog.dialogcancel();

            mFragment.pay((PayResult) data.t);
        } else if (data.action == Action.UserInfoManage_CashCharge_WxPay) {
            BufferCircleDialog.dialogcancel();

            mFragment.wxPay((WXPayResult) data.t);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }

        mFragment.toast(erroinfo, mFragment.getContext());
    }
}

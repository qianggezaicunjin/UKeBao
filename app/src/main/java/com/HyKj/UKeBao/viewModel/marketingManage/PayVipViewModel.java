package com.HyKj.UKeBao.viewModel.marketingManage;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.marketingManage.PayVipModel;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.VipPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.PayVipActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/11/14.
 */
public class PayVipViewModel extends BaseViewModel{
    private PayVipModel mModel;

    private PayVipActivity mActivity;

    @Bindable
    public VipPayInfo vipPayInfo;

    public PayVipViewModel(PayVipActivity activity,PayVipModel model){
        mActivity=activity;

        mModel=model;

        mModel.setView(this);
    }

    //充值vip
    public void rechargeVip(int payType, int vipPayId) {
        mModel.rechargeVip(payType,vipPayId);
    }

    //获取支付信息
    public void getPayInfo() {
        mModel.getPayInfo();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //现金支付
        if(data.action== Action.MarketingManage_ApplyVip_CashPay){
            BufferCircleDialog.dialogcancel();

            mActivity.paySuccessDialog();
        }
        //微信支付
        else if(data.action==Action.MarketingManage_ApplyVip_WxPay){
            BufferCircleDialog.dialogcancel();

            mActivity.wxPay((WXPayResult) data.t);
        }
        //支付宝支付
        else if(data.action==Action.MarketingManage_ApplyVip_AliPay){
            BufferCircleDialog.dialogcancel();

            mActivity.pay((PayResult) data.t);
        }
        //获取升级vip所需信息
        else if(data.action==Action.MarketingManage_GetUpgradeVipInfo){
            BufferCircleDialog.dialogcancel();

            vipPayInfo = (VipPayInfo) data.t;

            notifyPropertyChanged(BR.vipPayInfo);

            double cash=Double.valueOf(vipPayInfo.getCash());

            double money=Double.valueOf(vipPayInfo.getMoney());

            if(cash<money) {
                mActivity.btFlase();

                onRequestErroInfo("抱歉，您的现金余额不足，无法使用现金支付~");
            }
        }


    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }

        this.erroInfo=erroinfo;

        notifyPropertyChanged(BR.erroInfo);
    }


}

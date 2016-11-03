package com.HyKj.UKeBao.viewModel.marketingManage;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.CardCustomerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.marketingManage.CardCustomerActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/10/26.
 */
public class CardCustomerViewModel extends BaseViewModel {
    private CardCustomerModel mModel;

    private CardCustomerActivity mActivity;

    private CardInfo cardInfo;

    public CardCustomerViewModel(CardCustomerModel model, CardCustomerActivity activity) {
        mModel = model;

        mActivity = activity;

        mModel.setView(this);
    }

    //验证输入信息
    public void verification(String full_cut_money, String discount_money, String card_num,
                             String card_limit, String startTimesrc, String endTimesrc, BusinessInfo businessInfo) {

        if (TextUtils.isEmpty(full_cut_money) || Integer.valueOf(full_cut_money) < 1) {
            mActivity.toast("请输入正确的满减金额~", mActivity);

            return;
        } else if (TextUtils.isEmpty(discount_money)) {
            mActivity.toast("请输入正确的卡劵面额~", mActivity);

            return;
        } else if (!((Integer.valueOf(discount_money) >= Integer.valueOf(full_cut_money) * 0.1) && (Integer.valueOf(discount_money) < Integer.valueOf(full_cut_money)))) {
            mActivity.toast("(满减金额*0.1)≤优惠金额<满减金额", mActivity);

            return;
        } else if (TextUtils.isEmpty(card_num) || Integer.valueOf(card_num) < 1) {
            mActivity.toast("请输入正确的卡劵数量~", mActivity);

            return;
        } else if (TextUtils.isEmpty(card_limit) || !(Integer.valueOf(card_limit) > 0 && Integer.valueOf(card_limit) <= Integer.valueOf(card_num))) {
            mActivity.toast("请输入正确的限领张数~", mActivity);

            return;
        } else if (TextUtils.isEmpty(startTimesrc) || TextUtils.isEmpty(endTimesrc)) {
            mActivity.toast("请输入卡劵有效期~", mActivity);

            return;
        } else if (Integer.valueOf(endTimesrc) < Integer.valueOf(startTimesrc)) {
            mActivity.toast("结束时间不能小于开始时间~", mActivity);

            return;
        } else if (businessInfo == null) {
            mActivity.toast("获取会员信息失败，请检查网络~", mActivity);

            return;
        }

        mActivity.initDialog();
    }

    public void sendCard(String startTime, String endTime,
                         int card_limit, String card_num,
                         String discount_money, String full_cut_money,
                         String rule,
                         double currryentLongtitude, double curryentLatitude) {
        mModel.sendCard(startTime,endTime,card_limit,card_num,discount_money,full_cut_money,rule,currryentLongtitude,curryentLatitude);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //发送卡劵
        if (data.action== Action.MarketingManage_SendCard) {
            cardInfo= (CardInfo) data.t;

            mActivity.jump(cardInfo);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo,mActivity);
    }
}

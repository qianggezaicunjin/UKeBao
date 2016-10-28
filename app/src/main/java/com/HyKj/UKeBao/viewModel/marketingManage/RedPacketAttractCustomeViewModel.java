package com.HyKj.UKeBao.viewModel.marketingManage;

import android.text.TextUtils;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.marketingManage.RedPacketAttractCustomeModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CashOrIntegralPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.PayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.MarketingManage.RedPacketAttractCustomeActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.File;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RedPacketAttractCustomeViewModel extends BaseViewModel {

    private RedPacketAttractCustomeActivity mActivity;

    private RedPacketAttractCustomeModel mModel;

    private String advertisement_context;

    private String intagral;

    private String count;

    private BusinessInfo businessInfo;

    private short payType;//支付类型

    public WXPayInfo wxPayInfo;

    public RedPacketAttractCustomeViewModel(RedPacketAttractCustomeModel model, RedPacketAttractCustomeActivity activity) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //是否发送红包请求
    public void isSend(String redPacketImage, String advertisement_context, String intagral, String count) {
        if (TextUtils.isEmpty(redPacketImage)) {
            BufferCircleDialog.dialogcancel();

            toast("请添加一张图片~");

            return;
        } else if (TextUtils.isEmpty(advertisement_context)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写广告语~");

            return;
        } else if (TextUtils.isEmpty(intagral)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写总积分~");

            return;
        } else if (TextUtils.isEmpty(count)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写红包个数~");

            return;
        }
        //1≤红包个数≤总积分
        else if (!(1 <= Integer.valueOf(count) && Integer.valueOf(count) <= Integer.valueOf(intagral))) {
            BufferCircleDialog.dialogcancel();

            toast("1≤红包个数≤总积分");

            return;
        }

        this.advertisement_context = advertisement_context;

        this.intagral = intagral;

        this.count = count;

        //模块类型 1:产品图2:商家图3:临时图片4:广告图片
        mModel.getImagePath(new File(redPacketImage), 4);
    }

    //发送揽客红包
    public void sendDataToWeb(String count, double integralQuota, int distance,
                              String redPacketImage, String context, double curryentLatitude,
                              double currryentLongtitude, short payType) {
        this.payType = payType;

        mModel.sendDataToWeb(count, integralQuota, distance, redPacketImage, context, curryentLatitude, currryentLongtitude, payType);
    }

    public void toast(String context) {
        mActivity.toast(context, mActivity);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //添加图片
        if (data.action == Action.MarketingManage_RedPacketAttractCustome_AddImage) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String path = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            BufferCircleDialog.dialogcancel();

            mActivity.initPopupWindow(path);
        }
        //发送揽客红包
        else if (data.action == Action.MarketingManage_RedPacketAttractCustome_sendRedPacket) {

            BufferCircleDialog.dialogcancel();

            if (payType == 1) {
                PayInfo payInfo = (PayInfo) data.t;

                mActivity.alipay(payInfo);
            } else if (payType == 2) {
                wxPayInfo= (WXPayInfo) data.t;

                WXPayResult result=wxPayInfo.getPayResult();

                PayReq req=new PayReq();

                req.appId=result.getAppid();

                req.partnerId=result.getMch_id();

                req.nonceStr=result.getNoncestr();

                req.packageValue=result.getPackages();

                req.prepayId=result.getPrepayid();

                req.sign=result.getSign();

                req.timeStamp=result.getTimestamp();

                wxPayInfo.getPayResult().getAppid();

                IWXAPI api= WXAPIFactory.createWXAPI(mActivity,req.appId);

                api.registerApp(req.appId);

                api.sendReq(req);
            }else if (payType==0||payType==3){
                CashOrIntegralPayInfo cashOrIntegralPayInfo= (CashOrIntegralPayInfo) data.t;

                mActivity.jump(cashOrIntegralPayInfo,3);
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo, mActivity);

        BufferCircleDialog.dialogcancel();

        mActivity.isShow();
    }

}

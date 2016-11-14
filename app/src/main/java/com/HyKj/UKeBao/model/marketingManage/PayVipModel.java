package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.VipPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.AliPayResult;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/14.
 */
public class PayVipModel extends BaseModel {
    //充值vip
    public void rechargeVip(final int payType, int vipPayId) {
        Observable<JSONObject> observable=mDataManager.rechargeVip(vipPayId, MyApplication.token,payType,1);//默认：0，使用web方式支付，1使用app方式支付
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("充值vip异常:"+e.toString());

                        mRequestView.onRequestErroInfo("充值vip失败，请检查您的网络设置~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("充值vip成功，回调信息为:"+jsonObject.toString());

                        ModelAction action=new ModelAction();

                        //现金支付
                        if(jsonObject.getIntValue("status")==0){
                            action.action= Action.MarketingManage_ApplyVip_CashPay;

                            mRequestView.onRequestSuccess(action);
                        }
                        //微信支付
                        else if(jsonObject.getIntValue("status")==1&&payType==1){
                            action.action=Action.MarketingManage_ApplyVip_WxPay;

                            action.t=JSON.parseObject(jsonObject.getJSONObject("rows").toString(), WXPayResult.class);

                            mRequestView.onRequestSuccess(action);
                        }
                        //支付宝支付
                        else if(jsonObject.getIntValue("status")==1&&payType==2){
                            action.action=Action.MarketingManage_ApplyVip_AliPay;

                            action.t=JSON.parseObject(jsonObject.getJSONObject("rows").toString(), PayResult.class);

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }

    //获取支付信息
    public void getPayInfo() {
        Observable<JSONObject> observable=mDataManager.getPayInfo(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取支付信息异常："+e.toString());

                        mRequestView.onRequestErroInfo("获取支付信息失败~请检查网络!");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取支付信息成功："+jsonObject.toString());

                        if(jsonObject.getIntValue("status")==0){
                            ModelAction action=new ModelAction();

                            action.action=Action.MarketingManage_GetUpgradeVipInfo;

                            action.t=JSON.parseObject(jsonObject.getJSONObject("rows").toString(),VipPayInfo.class);

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取支付信息失败~请重试!");
                        }
                    }
                });
    }
}

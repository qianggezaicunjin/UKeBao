package com.HyKj.UKeBao.model.userInfoManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/12.
 */
public class CashChargeModel extends BaseModel {
    //生成支付信息
    public void createPayment(String recharge_integral, final int payType) {
        Observable<JSONObject> observable = mDataManager.cashCharge(recharge_integral, payType, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("现金充值异常:" + e.toString());

                        mRequestView.onRequestErroInfo("充值失败~请检查网络!");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("现金充值成功，回调数据为:" + jsonObject.toString());

                        ModelAction action = new ModelAction();

                        //支付宝
                        if (payType == 2) {
                            action.action = Action.UserInfoManage_CashCharge_Alipay;

                            JSONObject obj = jsonObject.getJSONObject("rows").getJSONObject("payResult");

                            action.t = JSON.parseObject(obj.toString(), PayResult.class);

                            LogUtil.d("现金充值支付宝"+payType);

                            mRequestView.onRequestSuccess(action);
                        } else if (payType == 1) {
                            action.action =Action.UserInfoManage_CashCharge_WxPay;

                            LogUtil.d("现金充值微信"+payType);

                            JSONObject obj = jsonObject.getJSONObject("rows").getJSONObject("payResult");

                            action.t = JSON.parseObject(obj.toString(), WXPayResult.class);

                            mRequestView.onRequestSuccess(action);
                        }
                    }
                });
    }
}

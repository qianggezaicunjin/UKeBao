package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardInfo;
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
 * Created by Administrator on 2016/10/26.
 */
public class CardCustomerModel extends BaseModel {
    public void sendCard(String startTime, String endTime,
                         int card_limit, String card_num,
                         String discount_money, String full_cut_money,
                         String rule, double currryentLongtitude, double curryentLatitude) {
        Observable<JSONObject> observable = mDataManager.sendCard(startTime, endTime, card_limit, card_num,
                discount_money, full_cut_money, rule, currryentLongtitude, curryentLatitude, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("发送卡劵异常:"+e.toString());

                        mRequestView.onRequestErroInfo("发送卡劵失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("发送卡劵成功，返回数据为:"+jsonObject);

                        if(jsonObject.getIntValue("status")==0) {
                            JSONObject obj = jsonObject.getJSONObject("rows");

                            CardInfo cardInfo = JSON.parseObject(obj.toString(), CardInfo.class);

                            ModelAction action = new ModelAction();

                            action.action = Action.MarketingManage_SendCard;

                            action.t = cardInfo;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

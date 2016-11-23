package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.ExchangeInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ExchangDetailFromSearchModel extends BaseModel {

    //获取兑换信息
    public void getCodeInfo(int code) {
        Observable<JSONObject> observable = mDataManager.getCodeInfo(code, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取兑换信息异常:" + e.toString());

                        mRequestView.onRequestErroInfo("获取兑换信息失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取兑换信息成功,返回信息为:" + jsonObject.toString());

                        if (jsonObject.getString("msg").equals("success")) {
                            JSONObject obj = jsonObject.getJSONObject("rows");

                            ExchangeInfo info = JSON.parseObject(obj.toString(), ExchangeInfo.class);

                            LogUtil.d("兑换信息解析集合为:" + info.toString());

                            ModelAction action = new ModelAction();

                            action.t = info;

                            action.action = Action.UserInfoManage_GetExchangInfo;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("抱歉，暂无该验证信息~");
                        }
                    }
                });
    }

    //确认收款
    public void confirmReceipt(int code) {
        Observable<JSONObject> observable = mDataManager.confirmReceipt(code, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("确认收款异常:" + e.toString());

                        mRequestView.onRequestErroInfo("网络连接失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("确认收款成功" + jsonObject.toString());

                        if (jsonObject.getBoolean("success")) {
                            ModelAction action=new ModelAction();

                            action.t=jsonObject.getString("msg");

                            action.action=Action.UserInfoManage_ConfirmReceipt;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("收款失败~请重试");
                        }
                    }
                });
    }
}

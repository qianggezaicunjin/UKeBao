package com.HyKj.UKeBao.model.businessManage.giveIntegral;

import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GiveIntegralModel extends BaseModel {
    //获取用户名
    public void getUserName(String phone) {
        Observable<JSONObject> observable = mDataManager.getUserName(phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取用户名失败" + e.toString());

                        mRequestView.onRequestErroInfo("获取用户名失败~请检查网络");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action = new ModelAction();

                        boolean success = jsonObject.getBoolean("success");

                        LogUtil.d("获取用户名数据结果" + success);

                        if (success) {
                            String userName = jsonObject.getString("msg");

                            action.t = userName;

                            action.action = Action.BusinessManage_GiveIntegral_getUserName;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取失败，请确认用户名是否正确~");
                        }
                    }
                });
    }

    //赠送积分
    public void sendIntegral(double cash, double integral, String phone, String businessName) {
        Observable<JSONObject> observable = mDataManager.sendIntegral(cash, integral, businessName, phone, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("赠送积分失败" + e.toString());

                        mRequestView.onRequestErroInfo("赠送失败~请核对信息");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("赠送积分请求成功，返回结果为:" + jsonObject.toString());

                        boolean success = jsonObject.getBoolean("success");

                        String msg = jsonObject.getString("msg");

                        ModelAction action=new ModelAction();

                        if(success){
                            action.t=msg;

                            action.action=Action.BusinessManage_SendIntegral;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(msg);
                        }

                    }
                });
    }
}

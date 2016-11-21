package com.HyKj.UKeBao.model.login;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/11.
 */
public class SplashModel extends BaseModel {
    //获取动态闪屏页背景图
    public void getBackground() {
        Observable<JSONObject> observable = mDataManager.getBackgrounp(35);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取动态闪屏页异常：" + e.toString());

                        mRequestView.onRequestErroInfo("网络异常，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取动态闪屏页请求成功，返回数据为:" + jsonObject.toString());

                        if (jsonObject.getIntValue("status") == 0) {
                            ModelAction action = new ModelAction();

                            action.action = Action.Login_getSplashBackGround;
                            try {
                                action.t = jsonObject.getJSONArray("rows").getJSONObject(0).getString("content");
                            }catch (Exception e){
                                action.t = "noting";
                            }
                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("网络异常，请重试~");
                        }
                    }
                });
    }
}

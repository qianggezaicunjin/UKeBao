package com.HyKj.UKeBao.model;

import com.HyKj.UKeBao.MyApplication;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.model.bean.ToUpDateInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MainModel extends BaseModel {
    //请求网络获取当前版本号
    public void whetheToOpDate() {
        Observable<JSONObject> observable = mDataManager.whetheToUpDate("3");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("MainModel获取更新版本信息" + e.toString());

                        mRequestView.onRequestErroInfo(e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action = new ModelAction();

                        action.action = Action.Main_getVersion_num;

                        ToUpDateInfo toUpDateInfo = JSON.parseObject(jsonObject.toString(), ToUpDateInfo.class);

                        LogUtil.d("MainModel获取更新版本信息" + toUpDateInfo.toString());

                        action.t = toUpDateInfo;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //判断是否为vip  status 	int 	0:已开通 2:未开通
    public void isVip() {
        Observable<JSONObject> observable = mDataManager.isVip(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取用户vip信息异常:" + e.toString());

                        mRequestView.onRequestErroInfo("获取vip数据失败~请检查网络!");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取用户vip信息成功:" + jsonObject.toString());

                        ModelAction action = new ModelAction();

                        action.action = Action.MarketingManage_isVip;

                        //status 	int 	0:已开通 2:未开通
                        if (jsonObject.getIntValue("status") == 0) {
                            action.t = jsonObject.getIntValue("status");

                            mRequestView.onRequestSuccess(action);
                        } else if (jsonObject.getIntValue("status") == 2) {
                            action.t = jsonObject.getIntValue("status");

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取vip数据异常，请重试~");
                        }

                    }
                });
    }

    //申请成为vip
    public void applyVip() {
        Observable<JSONObject> observable=mDataManager.applyVip(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("申请VIP异常:"+e.toString());

                        mRequestView.onRequestErroInfo("申请vip失败~请检查网络设置！");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("申请VIP成功，回调数据为:"+jsonObject.toString());

                        if (jsonObject.getBoolean("success")) {
                            ModelAction action=new ModelAction();

                            action.action=Action.MarketingManage_ApplyVip;

                            action.t=jsonObject.getIntValue("rows");

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("申请VIP失败~请重试！");
                        }
                    }
                });
    }
}

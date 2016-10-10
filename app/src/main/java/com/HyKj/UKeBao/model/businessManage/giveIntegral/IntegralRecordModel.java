package com.HyKj.UKeBao.model.businessManage.giveIntegral;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.businessManage.bean.CashRecordInfo;
import com.HyKj.UKeBao.model.businessManage.bean.IntegralRecordInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/27.
 */
public class IntegralRecordModel extends BaseModel {
    //获取记录
    public void getRecordData(int page, int rows, int businessStoreId, String type, String isSend) {
        Observable<JSONObject> observable = mDataManager.getRecordData(page, rows, businessStoreId, isSend, type, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取积分记录失败，异常信息：" + e.toString());

                        mRequestView.onRequestErroInfo("获取积分记录失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取积分记录成功，获取数据为:" + jsonObject.toString());

                        ModelAction action = new ModelAction();

                        int total = jsonObject.getIntValue("total");

                        if (total != -1) {
                            JSONArray arr = jsonObject.getJSONArray("rows");

                            List<IntegralRecordInfo> integralRecordInfoList = JSON.parseArray(arr.toString(), IntegralRecordInfo.class);

                            action.t = integralRecordInfoList;

                            action.action = Action.BusinessManage_GiveIntegral_getRecordData;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取失败~");
                        }
                    }
                });
    }

    //获取现金记录
    public void getCashRecordData(int businessStoreId, int page, int rows) {
        Observable<JSONObject> observablb = mDataManager.getCashRecord(businessStoreId, page, rows);
        observablb.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取现金记录失败，异常信息为：" + e.toString());

                        mRequestView.onRequestErroInfo("获取现金记录失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取现金记录成功，返回数据为:"+jsonObject.toString());

                        if (jsonObject.getIntValue("total") != -1){

                            JSONArray arr=jsonObject.getJSONArray("rows");

                            List<CashRecordInfo> cashList=new ArrayList<CashRecordInfo>();

                            cashList=JSON.parseArray(arr.toString(),CashRecordInfo.class);

                            ModelAction action=new ModelAction();

                            action.action=Action.BusinessMange_getCashRecord;

                            action.t=cashList;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取现金记录失败,请重试~");
                        }
                    }
                });
    }
}

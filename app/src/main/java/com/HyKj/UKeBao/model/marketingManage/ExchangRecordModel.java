package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.Rows;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.platform.comapi.map.A;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ExchangRecordModel extends BaseModel {

    //获取兑换记录
    public void getExchangRecord(int page, int rows, int businessStoreId) {
        Observable<JSONObject> observable = mDataManager.getExchangRecord(businessStoreId, page, rows, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取兑换记录异常" + e.toString());

                        mRequestView.onRequestErroInfo("获取兑换记录失败~请检查网络");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取兑换记录成功" + jsonObject.toString());

                        if (jsonObject.getString("msg").equals("success")) {

                            ModelAction action = new ModelAction();

                            action.action = Action.UserInfoManage_GetExchangRecord;

                            action.t= JSONArray.parseArray(jsonObject.getJSONArray("rows").toString(), Rows.class);

                            mRequestView.onRequestSuccess(action);

                        }else {
                            mRequestView.onRequestErroInfo("获取兑换记录失败~请重试");
                        }
                    }
                });
    }
}

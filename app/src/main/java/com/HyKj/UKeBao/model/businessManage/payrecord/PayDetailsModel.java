package com.HyKj.UKeBao.model.businessManage.payrecord;

import com.HyKj.UKeBao.MyApplication;
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
 * Created by Administrator on 2016/10/7.
 */
public class PayDetailsModel extends BaseModel {
    //退款操作
    public void refund(String sercet, String orderId) {
        Observable<JSONObject> observable = mDataManager.refund(Integer.valueOf(orderId), sercet, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("退款操作失败，异常信息为:" + e.toString());

                        mRequestView.onRequestErroInfo("退款失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("退款操作请求成功，返回信息为:" + jsonObject.toString());
                        if(jsonObject.getBoolean("success")){
                            ModelAction action=new ModelAction();

                            action.t=jsonObject.getString("msg");

                            action.action= Action.BusinessManage_refund;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

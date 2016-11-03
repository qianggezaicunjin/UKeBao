package com.HyKj.UKeBao.model.userInfoManage;

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
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsModel extends BaseModel{
    //申请提现
    public void withdrawals(String businessStoreId, String demand_amount) {
        Observable<JSONObject> observable=mDataManager.withdrawals(Integer.valueOf(businessStoreId),Float.valueOf(demand_amount), MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("提现异常:"+e.toString());

                        mRequestView.onRequestErroInfo("申请提现失败,请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("提现成功，返回数据为:"+jsonObject.toString());

                        if(jsonObject.getBoolean("success")){
                            ModelAction action=new ModelAction();

                            action.t=jsonObject.getString("msg");

                            action.action= Action.UserInfoManage_Withdrawals;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

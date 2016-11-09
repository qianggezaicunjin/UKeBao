package com.HyKj.UKeBao.model.userInfoManage;

import android.text.TextUtils;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/9.
 */
public class MyBankCardModel extends BaseModel {

    //获取店铺信息
    public void getBusinessInfo() {
        Observable<JSONObject> observable = mDataManager.getBusinessStore(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("提现账户获取银行卡信息异常" + e.toString());

                        mRequestView.onRequestErroInfo("获取信息失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("提现账户获取银行卡信息成功:" + jsonObject.toString());

                        ModelAction action=new ModelAction();

                        action.action= Action.UserInfoManage_BankInfo;

                        if (jsonObject.getIntValue("status") == 0 &&
                                !TextUtils.isEmpty(jsonObject.getJSONObject("rows").getString("bankName"))) {

                            JSONObject obj = jsonObject.getJSONObject("rows");

                            BusinessInfo businessInfo = new BusinessInfo();

                            businessInfo.setBankName(obj.getString("bankName"));

                            businessInfo.setBankNo(obj.getString("bankNo"));

                            businessInfo.setBankUserName(obj.getString("bankUserName"));

                            action.t=businessInfo;
                        }else {
                            action.t="未绑定";
                        }
                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

package com.HyKj.UKeBao.model.login.regist;

import android.util.Log;

import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.RegistInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/23.
 */
public class RegistModel extends BaseModel{

    //注册
    public void regist(String smsSecurityCode,long phone,String passWord){
        Observable<RegistInfo> observable=mDataManager.regist(smsSecurityCode,phone,phone,passWord);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegistInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());

                        Log.d("注册注册注册注册",e.toString());
                    }

                    @Override
                    public void onNext(RegistInfo registInfo) {
                        LogUtil.d("注册请求成功，回调结果为:"+registInfo.toString());

                        ModelAction action=new ModelAction();

                        action.t=registInfo;

                        action.action= Action.Login_Regist;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //判断手机号是否存在
    public void isExistence(long phone) {
        Observable<JSONObject> observable=mDataManager.isExistence(phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("验证手机号回调异常:"+e.toString());

                        mRequestView.onRequestErroInfo("验证手机号回调失败~请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("验证手机号回调成功，返回信息为:"+jsonObject.toString());

                        if(jsonObject.getIntValue("status")==1){
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }else {
                            ModelAction action=new ModelAction();

                            action.action=Action.Login_Regist_isExistence;

                            mRequestView.onRequestSuccess(action);
                        }
                    }
                });

    }
}

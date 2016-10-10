package com.HyKj.UKeBao.model.login.regist;

import android.util.Log;

import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.RegistInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;

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
                        ModelAction action=new ModelAction();
                        action.t=registInfo;
                        action.action= Action.Login_Regist;
                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

}

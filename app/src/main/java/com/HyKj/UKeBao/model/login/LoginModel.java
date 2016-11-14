package com.HyKj.UKeBao.model.login;

import android.util.Log;

import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.LoginInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LoginModel extends BaseModel{

    //获取登陆信息
    public void userLogin(String account,String passwd,String identityId,int deviceType,String deviceNo){
        Observable<LoginInfo> observable=mDataManager.userLogin(account,passwd,identityId,deviceType,deviceNo);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("登陆信息获取网络失败","登陆失败"+e.toString());

                        mRequestView.onRequestErroInfo("网络连接失败~请检查网络");
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        ModelAction action=new ModelAction();
                        action.t=loginInfo;
                        action.action= Action.Login_UserLogin;
                        Log.d("登陆信息获取网络成功",loginInfo.toString());
                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

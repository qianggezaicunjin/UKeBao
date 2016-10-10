package com.HyKj.UKeBao.model.login.examine;

import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/19.
 */
public class ExamineModel extends BaseModel{

    //注销登陆
    public void loginOut() {
        Observable<JSONObject> observable=mDataManager.loginOut(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        action.action= Action.Login_Examine_loginout;

                        boolean success= jsonObject.getBoolean("success");

                        action.t=success;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
    //获取客服电话
    public void getCostomerService() {
        Observable<JSONObject> observable=mDataManager.getCostomerService();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        action.action= Action.Login_Examine_getCostomerService;

                        String phone=jsonObject.getString("rows");

                        action.t=phone;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

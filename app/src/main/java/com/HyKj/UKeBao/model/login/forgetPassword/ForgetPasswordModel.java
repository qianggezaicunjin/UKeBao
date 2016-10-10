package com.HyKj.UKeBao.model.login.forgetPassword;

import android.util.Log;

import com.google.gson.JsonObject;
import com.HyKj.UKeBao.model.bean.BaseInfo;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/26.
 */
public class ForgetPasswordModel extends BaseModel{

    /**
     * 发送验证码
     * @param phone  手机号
     * */
    public void sendCode(long phone) {
        Observable<BaseInfo> observable=mDataManager.getVerificationCode(phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(BaseInfo baseInfo) {
                        ModelAction action=new ModelAction();
                        action.action= Action.Login_ForgetPassword_getVerificationCode;
                        action.t=baseInfo;
                        mRequestView.onRequestSuccess(action);
                        Log.d("找回密码页面：","获取验证码结果："+baseInfo.msg);
                    }
                });
    }

    /**
     * 找回密码
     * 验证码
     * 密码
     * 手机号
     * */
    public void forgetPassword(String code, String password, String phone) {
        Observable<JsonObject> observable=mDataManager.forgetPassword(password,code,phone);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());
                        LogUtil.d("找回密码网络请求失败，返回状态："+e.toString());
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        ModelAction action=new ModelAction();
                        action.action=Action.Login_ForgetPassword;
                        LogUtil.d("解析结果为"+jsonObject.toString());
                        String msg= null;
                        Boolean success = false;
                        try {
                            msg = jsonObject.get("msg").getAsString();
                            success= jsonObject.get("success").getAsBoolean();
                        } catch (Exception e) {
                            LogUtil.d("找回密码模块出现异常了......");
                            e.printStackTrace();
                        }
                        action.t=msg;
                        mRequestView.onRequestSuccess(action);
                        LogUtil.d("找回密码网络请求成功，返回状态："+msg+success);
                    }
                });
    }
}

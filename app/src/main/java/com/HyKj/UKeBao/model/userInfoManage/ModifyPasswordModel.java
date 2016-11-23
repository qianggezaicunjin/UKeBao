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
 * Created by Administrator on 2016/10/31.
 */
public class ModifyPasswordModel extends BaseModel{

    //提交修改后密码
    public void commitNewPassword(String oldPassword, String newPassword, String confirmPassword) {
        Observable<JSONObject> observable=mDataManager.commitNewPassword(oldPassword,newPassword,confirmPassword, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("修改密码异常，信息为:"+e.toString());

                        mRequestView.onRequestErroInfo("修改失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("修改成功，返回信息为:"+jsonObject.toString());
                        if(jsonObject.getBoolean("success")) {
                            ModelAction action = new ModelAction();

                            action.t = jsonObject.getString("msg");

                            action.action = Action.UserInfoManage_ModifyPassword;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });

    }
}

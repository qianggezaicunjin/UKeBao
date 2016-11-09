package com.HyKj.UKeBao.model.userInfoManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.login.regist.RegistModel;
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
public class AddBankCardModel extends RegistModel {

    //添加银行卡
    public void addBankCard(String bankName, String bankNo, String name, String code) {
        Observable<JSONObject> observable = mDataManager.addBankCard(bankNo, code, bankName, name, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("添加银行卡异常:" + e.toString());

                        mRequestView.onRequestErroInfo("添加银行卡失败，请检查网络设置~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action = new ModelAction();

                        action.action = Action.UserInfoManage_AddBankCard;

                        if (jsonObject.getIntValue("status") == 0) {
                            action.t = jsonObject.getString("msg");

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

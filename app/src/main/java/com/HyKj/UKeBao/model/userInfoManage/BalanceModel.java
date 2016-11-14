package com.HyKj.UKeBao.model.userInfoManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/12.
 */
public class BalanceModel extends LeftMenuFragmentModel{

    //确认充值(余额)
    public void confirmRecharge(String integral, String cash) {
        Observable<JSONObject> observable=mDataManager.confirmRecharge(cash,integral, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("余额充值积分出现异常:"+e.toString());

                        mRequestView.onRequestErroInfo("充值失败~请检查网络！");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("积分充值成功~返回信息为:"+jsonObject.toString());

                        if(jsonObject.getString("success").equals("true")){
                            ModelAction action=new ModelAction();

                            action.t=jsonObject.getString("msg");

                            action.action= Action.UserInfoManage_ConfirmRecharge_Balance;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

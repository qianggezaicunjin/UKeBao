package com.HyKj.UKeBao.model.login.joinAlliance;

import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/8/30.
 */
public class SettledAllianceModel extends BaseModel {

    //获取店铺数据
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
                        LogUtil.d("请求失败" + e.toString());

                        mRequestView.onRequestErroInfo("请求失败");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {

                        if(jsonObject.getIntValue("status")==0) {
                            ModelAction action = new ModelAction();

                            JSONObject obj = jsonObject.getJSONObject("rows");

                            LogUtil.d("请求店铺数据成功，返回结果为：" + obj);

                            action.t = JSON.parseObject(obj.toString(), BusinessInfo.class);

                            action.action = Action.Login_SettledAlliance_getBusinessInfo;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取店铺数据失败~请重试！");
                        }
                    }
                });
    }
}

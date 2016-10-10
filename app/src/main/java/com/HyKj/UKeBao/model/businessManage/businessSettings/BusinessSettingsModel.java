package com.HyKj.UKeBao.model.businessManage.businessSettings;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.bluelinelabs.logansquare.LoganSquare;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BusinessSettingsModel extends BaseModel {
    //获取店铺设置
    public void getBusinessInfo() {
        Observable<JsonObject> observable = mDataManager.getBusinessInfo(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JsonObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getBusinessInfo Exception:" + e.toString());

                        mRequestView.onRequestErroInfo("获取店铺数据失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JsonObject jsonObject) {
                        LogUtil.d("settings_businessInfo"+jsonObject.toString());

                        ModelAction action=new ModelAction();

                        BusinessInfo businessInfo=new BusinessInfo();
                        try {
                            businessInfo = LoganSquare.parse(jsonObject.toString(), BusinessInfo.class);
                        }catch (Exception e){
                            LogUtil.d("loganSquare解析异常:"+e.toString());
                        }
                        LogUtil.d("loganSquare解析成功，返回数据为:"+businessInfo.toString());
//                        action.action=Action.BusinessManage_businessSettings_getbusinessInfo;
//
//                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

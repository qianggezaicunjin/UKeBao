package com.HyKj.UKeBao.model.businessManage.businessSettings;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.util.ArrayList;
import java.util.List;

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
        Observable<JSONObject> observable = mDataManager.getBusinessStore(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getBusinessInfo Exception:" + e.toString());

                        mRequestView.onRequestErroInfo("获取店铺数据失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("settings_businessInfo" + jsonObject.toString());
                        if (jsonObject.getBoolean("success")) {
                            ModelAction action = new ModelAction();

                            action.action = Action.BusinessManage_businessSettings_getbusinessInfo;

                            JSONObject obj = jsonObject.getJSONObject("rows");

                            List<String> mList = new ArrayList<String>();

                            JSONArray arr = obj.getJSONArray("businessStoreImages");

                            mList = JSON.parseArray(arr.toString(), String.class);

                            BusinessInfo businessInfo = new BusinessInfo();

                            businessInfo.businessStoreImages = mList;

                            businessInfo.tel = obj.getString("tel");

                            businessInfo.businessName = obj.getString("businessName");

                            businessInfo.name = obj.getString("name");

                            businessInfo.ptype = obj.getString("ptype");

                            businessInfo.stype = obj.getString("stype");

                            businessInfo.businessDiscount = obj.getDouble("businessDiscount");

                            List<String> pictureList=new ArrayList<String>();

                            businessInfo.pictures=JSON.parseArray(obj.getJSONArray("pictures").toString(),String.class);

                            action.t = businessInfo;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取店铺数据失败，请稍候再试~");
                        }
                    }
                });
    }
}

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

                            businessInfo.city = obj.getString("city");

                            businessInfo.area = obj.getString("area");

                            businessInfo.setLongitude(Double.valueOf(obj.getString("longitude")));

                            businessInfo.setLatitude(Double.valueOf(obj.getString("latitude")));

                            businessInfo.address = obj.getString("address");

                            businessInfo.province = obj.getString("province");

                            businessInfo.businessDiscount = obj.getDouble("businessDiscount");

                            businessInfo.pictures = JSON.parseArray(obj.getJSONArray("pictures").toString(), String.class);

                            action.t = businessInfo;

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取店铺数据失败，请稍候再试~");
                        }
                    }
                });
    }

    //提交店铺设置信息
    public void commit(String tel, String name, List<String> pictures, String address,
                       String province, String city, String area, double longitude, double latitude) {
        Observable<JSONObject> observable = mDataManager.commitBusinessSettings(tel, name, pictures, address, province, city, area, longitude, latitude, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("提交店铺设置信息异常："+e.toString());

                        mRequestView.onRequestErroInfo("提交店铺设置失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("提交店铺设置信息成功，返回信息为:"+jsonObject.toString());

                        ModelAction action=new ModelAction();

                        action.action=Action.BusinessManage_businessSettings_commit;

                        action.t=jsonObject.getString("msg");

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

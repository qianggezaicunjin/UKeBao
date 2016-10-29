package com.HyKj.UKeBao.model.UserInfoManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/29.
 */
public class LeftMenuFragmentModel extends BaseModel{
    //获取商家信息
    public void getBusinessInfo() {
        Observable<JSONObject> observable=mDataManager.getBusinessStore(MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("侧边栏页面获取店铺数据异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取店铺数据失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("侧边栏页面获取店铺数据成功~");

                        JSONObject obj=jsonObject.getJSONObject("rows");

                        BusinessInfo businessInfo=new BusinessInfo();

                        businessInfo.setCash(obj.getDouble("cash"));

                        businessInfo.setIntegral(obj.getDouble("integral"));

                        businessInfo.setBusinessName(obj.getString("businessName"));

                        JSONArray arr=obj.getJSONArray("businessStoreImages");

                        businessInfo.setBusinessStoreImages(JSON.parseArray(arr.toString(),String.class));

                        LogUtil.d("揽页面获取店铺数据成功，回调数据为:"+businessInfo.toString());

                        ModelAction action=new ModelAction();

                        action.t=businessInfo;

                        action.action= Action.UserInfoManage_GetBusinessInfo;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

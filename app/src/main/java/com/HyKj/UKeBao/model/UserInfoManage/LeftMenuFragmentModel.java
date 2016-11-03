package com.HyKj.UKeBao.model.userInfoManage;

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

                        businessInfo.setBusinessDiscount(obj.getDouble("businessDiscount"));

                        businessInfo.setFreezeCash(obj.getDouble("freezeCash"));

                        businessInfo.setTel(obj.getString("tel"));

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

    //获取客服电话
    public void getCustomerPhone() {
        Observable<JSONObject> observable=mDataManager.getCustomerPhone();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取客户电话异常，信息为:"+e.toString());

                        mRequestView.onRequestErroInfo("网络连接异常，请稍候再试");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取客电话成功，信息为:"+jsonObject.toString());

                        ModelAction action=new ModelAction();

                        action.action=Action.UserInfoManage_GetCustomerPhone;

                        action.t=jsonObject.getString("rows");

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

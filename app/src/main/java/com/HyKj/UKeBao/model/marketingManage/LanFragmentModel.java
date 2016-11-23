package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.CardAndRedPacketInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.CardDetail;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetail;
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
 * Created by Administrator on 2016/10/17.
 */
public class LanFragmentModel extends BaseModel {
    //获取会员数量
    public void getMemberCount(int discount, double longitude, double latitude) {
        Observable<JSONObject> observable = mDataManager.getMemberCount(discount, longitude, latitude, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取会员数量异常:" + e.toString());

                        mRequestView.onRequestErroInfo("获取会员数量失败~请检查网络");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取会员数量成功，返回数据为:" + jsonObject.toString());

                        JSONObject obj = jsonObject.getJSONObject("rows");

                        int memberCount = obj.getIntValue("menberCount");

                        ModelAction action=new ModelAction();

                        action.t=memberCount;

                        action.action= Action.MarketingManage_GetMemberCount;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

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
                        LogUtil.d("揽页面获取店铺数据异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取店铺数据失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("揽页面获取店铺数据成功~"+jsonObject.toString());

                        JSONObject obj=jsonObject.getJSONObject("rows");

                        BusinessInfo businessInfo=new BusinessInfo();

                        businessInfo.setLatitude(obj.getDouble("latitude"));

                        businessInfo.setLongitude(obj.getDouble("longitude"));

                        businessInfo.setCash(obj.getDouble("cash"));

                        businessInfo.setIntegral(obj.getDouble("integral"));

                        businessInfo.setBusinessName(obj.getString("businessName"));

                        JSONArray arr=obj.getJSONArray("businessStoreImages");

                        businessInfo.setBusinessStoreImages(JSON.parseArray(arr.toString(),String.class));

                        LogUtil.d("揽页面获取店铺数据成功，回调数据为:"+businessInfo.toString());

                        ModelAction action=new ModelAction();

                        action.t=businessInfo;

                        action.action=Action.MarketingManage_GetBusinessInfo;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //获取红包和卡劵信息
    public void getRedPacketsAndCardInfo(){
        Observable<JSONObject> observable=mDataManager.getRedPacketsAndCardInfo(1,40,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取红包卡劵信息异常："+e.toString());

                        mRequestView.onRequestErroInfo("获取红包和卡券信息失败，请稍候重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取红包卡券信息成功，回调函数为:"+jsonObject.toString());

                        List<CardAndRedPacketInfo> list=new ArrayList<CardAndRedPacketInfo>();

                        JSONArray arr=jsonObject.getJSONArray("rows");

                        list= JSON.parseArray(arr.toString(),CardAndRedPacketInfo.class);

                        LogUtil.d("红包卡劵解析数组为:"+list.toString());

                        ModelAction action=new ModelAction();

                        action.t=list;

                        action.action=Action.MarketingManage_GetRedPacketsAndCard;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //获取单个卡劵的详情
    public void getSingCardDetail(int id) {
        Observable<JSONObject> observable=mDataManager.getSingCardDetail(id,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取单个卡劵详情出现异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取卡劵详情失败，请稍候再试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取单个卡劵详情成功,allData"+jsonObject.toString());

                        ModelAction action=new ModelAction();

                        JSONObject obj=jsonObject.getJSONObject("rows");

                        CardDetail cardDetail=JSON.parseObject(obj.toString(),CardDetail.class);

                        LogUtil.d("解析单个卡劵详情成功，回调数据为:"+cardDetail.toString());

                        action.t=cardDetail;

                        action.action=Action.MarketingManage_GetSingCardDetail;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //获取单个红包详情
    public void getSingRedPacketDetail(Integer integer) {
        Observable<JSONObject> observable=mDataManager.getSingRedPacketDetail(integer,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取单个红包详情出现异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取卡劵详情失败，请稍候再试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取单个红包详情成功");

                        ModelAction action=new ModelAction();

                        JSONObject obj=jsonObject.getJSONObject("rows");

                        RedPacketDetail redPacketDetail=JSON.parseObject(obj.toString(),RedPacketDetail.class);

                        LogUtil.d("解析单个红包详情成功，回调数据为:"+redPacketDetail.toString());

                        action.t=redPacketDetail;

                        action.action=Action.MarketingManage_GetSingRedPacketDetail;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

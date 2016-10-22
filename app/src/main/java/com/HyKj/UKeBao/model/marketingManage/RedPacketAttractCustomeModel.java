package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RedPacketAttractCustomeModel extends BaseModel{
    //获取图片路径
    public void getImagePath(File file,int modelType) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        Observable<JSONObject> observable=mDataManager.uploadPictures(requestBody,modelType,"广告图");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("揽客红包页面获取图片路径异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取图片路径失败~请检查网络连接");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("揽客红包页面获取图片路径成功:"+jsonObject.toString());

                        StoreSignage storeSignage= JSON.parseObject(jsonObject.toString(),StoreSignage.class);

                        ModelAction action=new ModelAction();

                        action.action= Action.MarketingMange_RedPacketAttractCustome_AddImage;

                        action.t=storeSignage;

                        mRequestView.onRequestSuccess(action);
                    }
                });

    }

    //发送揽客红包
    public void sendDataToWeb(String count, double integralQuota, int distance,
                              String redPacketImage, String context, double curryentLatitude,
                              double currryentLongtitude, short payType) {
        Observable<JSONObject> observable=mDataManager.sendDataToWeb(count,integralQuota,distance, redPacketImage,
                context,curryentLatitude,currryentLongtitude,payType,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("sendRedpacket_Exception"+e.toString());

                        mRequestView.onRequestErroInfo("发送揽客红包失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("发送揽客红包成功，返回数据为:"+jsonObject.toString());

                    }
                });
    }
}

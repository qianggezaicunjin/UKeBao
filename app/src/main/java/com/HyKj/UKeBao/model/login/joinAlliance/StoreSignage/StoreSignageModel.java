package com.HyKj.UKeBao.model.login.joinAlliance.StoreSignage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/3.
 */
public class StoreSignageModel extends BaseModel{
    //上传照片到服务器
    public void uploadPictures(File file,int modelType){
        //模块类型 1:产品图2:商家图3:临时图片4:广告图片
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        Observable<JSONObject> observable=mDataManager.uploadPictures(requestBody,modelType,"店招");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("网络连接失败"+e.toString());
                        mRequestView.onRequestErroInfo("网络连接失败");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        StoreSignage storeSignage= JSON.parseObject(jsonObject.toString(),StoreSignage.class);

                        LogUtil.d("上传图片成功，返回数据为:"+storeSignage.getRows().toString());

                        action.action= Action.Login_SettledAlliance_uploadPictures;

                        action.t=storeSignage;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

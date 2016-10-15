package com.HyKj.UKeBao.model.businessManage.businessSettings;

import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.login.joinAlliance.StoreSignage.StoreSignageModel;
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
 * Created by Administrator on 2016/10/11.
 */
public class BusinessStoreImageModel extends BaseModel {
    //上传图片(图片达到最大)
    public void updataIamge(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        Observable<JSONObject> observable=mDataManager.uploadPictures(requestBody,2,"店铺相册");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("店铺相册上传图片网络请求错误，异常信息为"+e.toString());

                        mRequestView.onRequestSuccess("网络请求失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("上传图片获取数据成功:"+jsonObject.toString());

                        StoreSignage storeSignage= JSON.parseObject(jsonObject.toString(),StoreSignage.class);

                        ModelAction action=new ModelAction();

                        action.action= Action.BusinessManage_businessSettings_updataImage;

                        action.t=storeSignage;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //上传图片（图片可添加）
    public void updataIamgeVacancy(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file);

        Observable<JSONObject> observable=mDataManager.uploadPictures(requestBody,2,"店铺相册");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("店铺相册上传图片网络请求错误，异常信息为"+e.toString());

                        mRequestView.onRequestSuccess("网络请求失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("上传图片获取数据成功:"+jsonObject.toString());

                        StoreSignage storeSignage= JSON.parseObject(jsonObject.toString(),StoreSignage.class);

                        ModelAction action=new ModelAction();

                        action.action= Action.BusinessManage_businessSettings_updataImageVacancy;

                        action.t=storeSignage;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

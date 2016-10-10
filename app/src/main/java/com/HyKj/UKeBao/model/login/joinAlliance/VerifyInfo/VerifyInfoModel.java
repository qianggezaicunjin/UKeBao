package com.HyKj.UKeBao.model.login.joinAlliance.VerifyInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/18.
 */
public class VerifyInfoModel extends BaseModel {

    //上传营业执照、身份信息、身份证背面信息
    public void uploadImage(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        Observable<JSONObject> observable = mDataManager.uploadPictures(requestBody, 3, "店铺信息");

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("上传店铺营业信息照片失败" + e.toString());
                        mRequestView.onRequestErroInfo("网络连接失败");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action = new ModelAction();

                        action.action = Action.Login_SettledAlliance_Commit_uploadImage;

                        StoreSignage storeInfo = JSON.parseObject(jsonObject.toString(), StoreSignage.class);

                        action.t = storeInfo;

                        LogUtil.d("提交申请界面上传店铺信息成功" + storeInfo.toString());

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }


    //第一次提交数据
    public void first_commit(String businessName, String name, String tel, String businessRegistrationNo, int category, String province,
                             String city, String area, String address, double longitude, double latitude, String businessStoreImages, List<String> list) {
        Observable<JSONObject> observable = mDataManager.first_commit(businessName, name, tel, businessRegistrationNo, category,
                province, city, area, address, longitude, latitude, businessStoreImages, list, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("网络连接失败"+e.toString());
                        mRequestView.onRequestErroInfo("上传失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        action.action=Action.Login_SettledAlliance_Commit_userInfo;

                        String msg = jsonObject.getString("msg");

                        int status = jsonObject.getIntValue("status");

                        LogUtil.d("第一次提交数据拿到结果为"+msg+status);

                        action.t=status;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //审核失败修改后提交数据
    public void commit(String businessName, String name, String tel, String businessRegistrationNo, int category, String province,
                               String city, String area, String address, double longitude, double latitude, String businessStoreImages, List<String> list, String id) {
        Observable<JSONObject> observable = mDataManager.commit(businessName, name, tel, businessRegistrationNo, category,
                province, city, area, address, longitude, latitude, businessStoreImages, list, id,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("网络连接失败"+e.toString());
                        mRequestView.onRequestErroInfo("上传失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        action.action=Action.Login_SettledAlliance_Commit_userInfo;

                        String msg = jsonObject.getString("msg");

                        int status = jsonObject.getIntValue("status");

                        LogUtil.d("第一次提交数据拿到结果为"+msg+status);

                        action.t=status;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

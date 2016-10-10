package com.HyKj.UKeBao.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.model.bean.ToUpDateInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MainModel extends BaseModel{
    //请求网络获取当前版本号
    public void whetheToOpDate() {
        Observable<JSONObject> observable = mDataManager.whetheToUpDate("3");
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ModelAction action=new ModelAction();

                        action.action= Action.Main_getVersion_num;

                        ToUpDateInfo toUpDateInfo= JSON.parseObject(jsonObject.toString(),ToUpDateInfo.class);

                        LogUtil.d("MainModel获取更新版本信息"+toUpDateInfo.toString());

                        action.t=toUpDateInfo;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

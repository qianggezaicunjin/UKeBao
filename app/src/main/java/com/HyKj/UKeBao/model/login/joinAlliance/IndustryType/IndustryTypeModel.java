package com.HyKj.UKeBao.model.login.joinAlliance.IndustryType;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.ProductCategory;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;



import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/1.
 */
public class IndustryTypeModel extends BaseModel{

    public ProductCategory productCategory=null;

    //获取行业类型数据
    public void getIndustryTypeData() {
        Observable<JSONObject> observable=mDataManager.industryTypeData();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo(e.toString());

                        LogUtil.d("网络请求错误"+e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {

                        ModelAction action=new ModelAction();

                        action.action= Action.Login_SettledAlliance_industryTypeData;

                        productCategory=JSON.parseObject(jsonObject.toString(),ProductCategory.class);

                        action.t=productCategory;

                        LogUtil.d("拿到行业类型数据"+productCategory.toString());

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

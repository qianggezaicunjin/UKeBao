package com.HyKj.UKeBao.model.businessManage.financial;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.businessManage.bean.FinancialManage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/27.
 */
public class FinancialManagementModel extends BaseModel{
    //获取财务数据
    public void getFinancialData(String startDate, String stopDate) {
        Observable<JSONObject> observable=mDataManager.getFinancialData(startDate,stopDate, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("getFinancialData Exception"+e.toString());

                        mRequestView.onRequestErroInfo("获取财务数据失败~请重试");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("请求财务数据成功，回调结果为:"+jsonObject.toString());

                        if(jsonObject.getIntValue("satatus")==0) {

                            ModelAction action = new ModelAction();

                            FinancialManage finacialManage = JSON.parseObject(jsonObject.toString(), FinancialManage.class);

                            LogUtil.d("获取财务数据成功,数据为：" + finacialManage.toString());

                            action.action = Action.BusinessManage_getFinanicalData;

                            action.t = finacialManage;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取财务数据失败~请重试！");
                        }
                    }
                });
    }
}

package com.HyKj.UKeBao.model.businessManage.financial;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.businessManage.financial.bean.RealMoneyDetail;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/10.
 */
public class RealIncomeDetailModel extends BaseModel {

    //获取实收详情
    public void getRealDetail(String startTime, String endTime) {
        Observable<JSONObject> observable = mDataManager.getRealMoneyDetail(startTime, endTime, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取实收详情异常:" + e.toString());

                        mRequestView.onRequestErroInfo("获取实收详情信息失败，请检查网络~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if (jsonObject.getIntValue("status") == 0) {
                            ModelAction action = new ModelAction();

                            action.action = Action.BusinessManage_GetRealMoneyDetail;

                            action.t = JSON.parseObject(jsonObject.getJSONObject("rows").toString(), RealMoneyDetail.class);

                            mRequestView.onRequestSuccess(action);
                        } else {
                            mRequestView.onRequestErroInfo("获取实收详情失败~请重试！");
                        }
                    }
                });
    }
}

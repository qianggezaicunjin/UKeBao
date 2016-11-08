package com.HyKj.UKeBao.model.userInfoManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.userInfoManage.bean.WithdrawalsRecord;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsRecordModel extends BaseModel {
    //获取提现记录
    public void getWithdrawlsRecord(String businessStoreId, int page, int rows) {
        Observable<JSONObject> observable = mDataManager.getwithdrawalsRecord(Integer.valueOf(businessStoreId), page, rows, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取提现记录异常:" + e.toString());

                        mRequestView.onRequestErroInfo("获取提现记录失败~请检查网络");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取提现记录成功:" + jsonObject.toString());

                        ModelAction action = new ModelAction();

                        action.action = Action.UserInfoManage_getWithdrawalsRecord;

                        List<WithdrawalsRecord> list = new ArrayList<WithdrawalsRecord>();

                        list = JSONArray.parseArray(jsonObject.getJSONArray("rows").toString(), WithdrawalsRecord.class);

                        if (list.size() != 0) {
                            list.get(0).setTotal(jsonObject.getIntValue("total"));
                        }
                        action.t = list;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

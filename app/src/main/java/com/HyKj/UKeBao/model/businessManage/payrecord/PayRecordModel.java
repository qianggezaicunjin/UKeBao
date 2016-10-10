package com.HyKj.UKeBao.model.businessManage.payrecord;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.businessManage.bean.OrderRecord;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/29.
 */
public class PayRecordModel extends BaseModel{

    //获取筛选后的订单数据
    public void getPayRecord(int status,int page, int rows) {
        Observable<JSONObject> observable=mDataManager.getPayRecord(page,rows,status, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取筛选订单数据错误，异常信息为:"+e.toString());

                        mRequestView.onRequestErroInfo("获取订单数据失败，请检查网络！");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if(jsonObject.get("msg").equals("success")) {
                            JSONArray arr = jsonObject.getJSONArray("rows");

                            LogUtil.d("获取筛选订单数据成功,返回数据结果为:" + arr.toString());

                            List<OrderRecord> list = JSON.parseArray(arr.toString(), OrderRecord.class);

                            ModelAction action = new ModelAction();

                            action.t = list;

                            action.action = Action.BusinessManage_getPayRecord;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取订单数据失败，请检查网络！");
                        }
                    }
                });
    }

    //获取全部订单数据，不筛选
    public void getPayRecord(int page, int rows) {
        Observable<JSONObject> observable=mDataManager.getAllPayRecord(page,rows,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取全部订单数据错误，异常信息为:"+e.toString());

                        mRequestView.onRequestErroInfo("获取订单数据失败，请检查网络！");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        if(jsonObject.get("msg").equals("success")) {
                            JSONArray arr = jsonObject.getJSONArray("rows");

                            LogUtil.d("获取全部订单数据成功,返回数据结果为:" + arr.toString());

                            List<OrderRecord> list = JSON.parseArray(arr.toString(), OrderRecord.class);

                            ModelAction action = new ModelAction();

                            action.t = list;

                            action.action = Action.BusinessManage_getPayRecord;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("获取订单数据失败，请检查网络！");
                        }
                    }
                });
    }
}

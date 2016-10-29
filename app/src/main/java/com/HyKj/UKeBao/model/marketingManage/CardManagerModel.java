package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardListInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/28.
 */
public class CardManagerModel extends BaseModel {
    //获取卡劵列表全部信息
    public void getAllCardInfo(int page, int rows) {
        Observable<JSONObject> observable = mDataManager.getAllCardInfo(rows, page, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取卡劵列表信息异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取卡劵列表信息失败，请重试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取卡劵列表成功，返回数据为:"+jsonObject.toString());

                        if(jsonObject.getBoolean("success")){
                            ModelAction action=new ModelAction();

                            action.action= Action.MarketingManage_getCardListInfo;

                            List<CardListInfo> info= JSON.parseArray(jsonObject.getJSONArray("rows").toString(), CardListInfo.class);

                            int total=jsonObject.getIntValue("total");

                            info.get(0).setTotal(total);

                            info.set(0,info.get(0));

                            action.t=info;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("msg"));
                        }
                    }
                });
    }
}

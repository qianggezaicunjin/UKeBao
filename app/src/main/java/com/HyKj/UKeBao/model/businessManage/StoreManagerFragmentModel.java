package com.HyKj.UKeBao.model.businessManage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.businessManage.bean.NotifyInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/24.
 */
public class StoreManagerFragmentModel extends BaseModel{
    private int page=1;//获取公告页数

    private int rows=20;//获取行数

    private int receiveIdentity=5;//接收方身份：5（商家）
    //发送网络请求，获取系统公告信息
    public void getNoticeInfo() {
        Observable<JSONObject> observable=mDataManager.getNoticeInfo(receiveIdentity,page,rows);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRequestView.onRequestErroInfo("获取系统公告信息失败~");

                        LogUtil.d("获取系统公告信息异常"+e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("StoreManagerFragment_notice_setting"+jsonObject.toString());

                        JSONArray array=jsonObject.getJSONArray("rows");

                        List<NotifyInfo> notifyList = JSON.parseArray(array.toString(),NotifyInfo.class);

                        ModelAction action = new ModelAction();

                        action.action = Action.Main_getNoticeInfo;

                        action.t = notifyList;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

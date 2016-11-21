package com.HyKj.UKeBao.model.userInfoManage;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/14.
 */
public class VipFunctionModel extends LeftMenuFragmentModel{

    //申请vip
    public void applyVip() {
        //申请成为vip
            Observable<JSONObject> observable=mDataManager.applyVip(MyApplication.token);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<JSONObject>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            LogUtil.d("申请VIP异常:"+e.toString());

                            mRequestView.onRequestErroInfo("申请vip失败~请检查网络设置！");
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            LogUtil.d("申请VIP成功，回调数据为:"+jsonObject.toString());

                            if (jsonObject.getBoolean("success")) {
                                ModelAction action=new ModelAction();

                                action.action= Action.MarketingManage_ApplyVip;

                                action.t=jsonObject.getIntValue("rows");

                                mRequestView.onRequestSuccess(action);
                            }else {
                                mRequestView.onRequestErroInfo("申请VIP失败~请重试！");
                            }
                        }
                    });
        }
}

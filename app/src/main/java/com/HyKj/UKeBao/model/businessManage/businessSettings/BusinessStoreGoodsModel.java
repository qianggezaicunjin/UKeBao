package com.HyKj.UKeBao.model.businessManage.businessSettings;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.alibaba.fastjson.JSONObject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/11/13.
 */
public class BusinessStoreGoodsModel extends BusinessStoreImageModel{
    //删除照片
    public void delete(int id) {
        Observable<JSONObject> observable=mDataManager.delete_goods(id, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("删除商品异常:"+e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("删除成功，数据回调："+jsonObject.toString());

                        if(jsonObject.getBoolean("success")){
                            ModelAction action=new ModelAction();

                            action.action= Action.BusinessManage_Delete_Goods;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo("delete_exception");
                        }
                    }
                });
    }
}

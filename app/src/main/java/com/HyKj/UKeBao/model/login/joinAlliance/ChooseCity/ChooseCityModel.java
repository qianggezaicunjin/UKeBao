package com.HyKj.UKeBao.model.login.joinAlliance.ChooseCity;

import com.alibaba.fastjson.JSONArray;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.login.baen.Province;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ChooseCityModel extends BaseModel {

    public void chooseCity() {
        Observable<JSONArray> obseravle=mDataManager.chooseCity();
        obseravle.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONArray>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("选择城市网络获取数据失败："+e.toString());
                        mRequestView.onRequestErroInfo("网络连接失败");
                    }

                    @Override
                    public void onNext(JSONArray jsonArray) {
                        ModelAction action=new ModelAction();
                        List<Province> province=JSONArray.parseArray(jsonArray.toString(),Province.class);
                        LogUtil.d("获取成功"+province.get(2).getName().toString());
                        province.remove(0);
                        action.t=province;
                        action.action= Action.Login_SettledAlliance_chooseCity;
                        mRequestView.onRequestSuccess(action);
                    }
                });
    }
}

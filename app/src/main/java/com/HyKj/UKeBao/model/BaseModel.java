package com.HyKj.UKeBao.model;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.data.NetWorkService;
import com.HyKj.UKeBao.data.RetrofitHelp;
import com.HyKj.UKeBao.view.customView.RequestView;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BaseModel {
    public RequestView mRequestView;

    public NetWorkService mDataManager;//不带token的请求方式

    public BaseModel(){
        mDataManager= RetrofitHelp.createService(NetWorkService.class,MyApplication.token);
    }

    public void setView(RequestView requestView){
        mRequestView=requestView;
    }
}

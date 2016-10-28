package com.HyKj.UKeBao.model.marketingManage;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.BaseModel;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetail;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetailInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacket_collect_record;
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
 * Created by Administrator on 2016/10/27.
 */
public class RedPacketDetailModel extends BaseModel{
    //获取单个红包详情
    public void getSingRedPacketDetail(Integer integer) {
        Observable<JSONObject> observable=mDataManager.getSingRedPacketDetail(integer, MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("获取单个红包详情出现异常:"+e.toString());

                        mRequestView.onRequestErroInfo("获取卡劵详情失败，请稍候再试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("获取单个红包详情成功");

                        ModelAction action=new ModelAction();

                        JSONObject obj=jsonObject.getJSONObject("rows");

                        RedPacketDetail redPacketDetail= JSON.parseObject(obj.toString(),RedPacketDetail.class);

                        LogUtil.d("解析单个红包详情成功，回调数据为:"+redPacketDetail.toString());

                        action.t=redPacketDetail;

                        action.action= Action.MarketingManage_GetSingRedPacketDetail;

                        mRequestView.onRequestSuccess(action);
                    }
                });
    }

    //
    public void collect_records(int id, int page, int rows) {
        Observable<JSONObject> observable=mDataManager.redPacket_record(id,page,rows,MyApplication.token);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("领取红包记录失败,异常类型为:"+e.toString());

                        mRequestView.onRequestErroInfo("获取红包记录失败，请稍候再试~");
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        LogUtil.d("领取红包记录获取数据成功，返回值为:"+jsonObject.toString());

                        if(jsonObject.getIntValue("status")==0) {
                            List<RedPacketDetailInfo> record = JSON.parseArray(jsonObject.getJSONArray("rows").toString(), RedPacketDetailInfo.class);

                            RedPacket_collect_record collect_record=new RedPacket_collect_record();

                            collect_record.setRows(record);

                            collect_record.setTotal(jsonObject.getIntValue("total"));

                            ModelAction action = new ModelAction();

                            action.action = Action.MarketingMange_RedPacket_Collect_Record;

                            action.t = collect_record;

                            mRequestView.onRequestSuccess(action);
                        }else {
                            mRequestView.onRequestErroInfo(jsonObject.getString("获取数据失败~"));
                        }
                    }
                });
    }
}

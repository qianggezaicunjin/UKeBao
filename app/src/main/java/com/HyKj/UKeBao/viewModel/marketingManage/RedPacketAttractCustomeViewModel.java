package com.HyKj.UKeBao.viewModel.marketingManage;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.marketingManage.RedPacketAttractCustomeModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.MarketingManage.RedPacketAttractCustomeActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.io.File;

/**
 * Created by Administrator on 2016/10/21.
 */
public class RedPacketAttractCustomeViewModel extends BaseViewModel {

    private RedPacketAttractCustomeActivity mActivity;

    private RedPacketAttractCustomeModel mModel;

    private String advertisement_context;

    private String intagral;

    private String count;

    private BusinessInfo businessInfo;

    public RedPacketAttractCustomeViewModel(RedPacketAttractCustomeModel model, RedPacketAttractCustomeActivity activity) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //是否发送红包请求
    public void isSend(String redPacketImage, String advertisement_context, String intagral, String count) {
        if (TextUtils.isEmpty(redPacketImage)) {
            BufferCircleDialog.dialogcancel();

            toast("请添加一张图片~");

            return;
        } else if (TextUtils.isEmpty(advertisement_context)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写广告语~");

            return;
        } else if (TextUtils.isEmpty(intagral)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写总积分~");

            return;
        } else if (TextUtils.isEmpty(count)) {
            BufferCircleDialog.dialogcancel();

            toast("请填写红包个数~");

            return;
        }
        //1≤红包个数≤总积分
        else if (!(1 <= Integer.valueOf(count) && Integer.valueOf(count) <= Integer.valueOf(intagral))) {
            BufferCircleDialog.dialogcancel();

            toast("1≤红包个数≤总积分");

            return;
        }

        this.advertisement_context = advertisement_context;

        this.intagral = intagral;

        this.count = count;

        //模块类型 1:产品图2:商家图3:临时图片4:广告图片
        mModel.getImagePath(new File(redPacketImage), 4);
    }

    //发送揽客红包
    public void sendDataToWeb(String count, double integralQuota, int distance,
                              String redPacketImage, String context, double curryentLatitude,
                              double currryentLongtitude, short payType) {
        mModel.sendDataToWeb(count,integralQuota,distance,redPacketImage,context,curryentLatitude,currryentLongtitude,payType);
    }

    public void toast(String context) {
        mActivity.toast(context, mActivity);
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.MarketingMange_RedPacketAttractCustome_AddImage) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String path = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            BufferCircleDialog.dialogcancel();

            mActivity.initPopupWindow(path);
        }else if (data.action==Action.MarketingManage_GetBusinessInfo){

        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo, mActivity);

        BufferCircleDialog.dialogcancel();
    }

}

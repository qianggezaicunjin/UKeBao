package com.HyKj.UKeBao.viewModel.login.joinAlliance;

import android.text.TextUtils;


import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.joinAlliance.SettledAllianceModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.SettledAllianceActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/8/30.
 */
public class SettledAllianceViewModel extends BaseViewModel {

    public SettledAllianceModel mModel;

    public BusinessInfo mBusinessinfo;

    public SettledAllianceActivity mActivity;

    public SettledAllianceViewModel(SettledAllianceModel model, SettledAllianceActivity activity) {
        mModel = model;
        mModel.setView(this);
        mActivity = activity;
    }

    //请求店铺数据
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }

    /**
     * String name,  用户名
     * String contacts_name,  联系人姓名
     * String phone_num,  联系电话
     * String regis_number,  工商注册号
     * BusinessInfo businessInfo,  用户信息
     * Boolean hasSetCoordinate  是否设置坐标
     * List<PhotoInfo> mlist  营业执照、身份证正面、身份证背面
     */
    public void submitData(String name,
                           String contacts_name,
                           String phone_num, String regis_number,
                           BusinessInfo businessInfo,
                           Boolean hasSetCoordinate,
                           List<PhotoInfo> mlist) {

        if (TextUtils.isEmpty(name)) {
            BufferCircleDialog.dialogcancel();

            mActivity.toast("请输入用户名");

            return;
        }
        if (TextUtils.isEmpty(contacts_name)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请输入联系人姓名");
            return;
        }
        if (TextUtils.isEmpty(phone_num)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请输入联系电话");
            return;
        }
        if (TextUtils.isEmpty(regis_number)||regis_number.length()!=15) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请输入15位工商注册号");
            return;
        }
        if (TextUtils.isEmpty(businessInfo.ptype)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请选择行业类型");
            return;
        }
        if (TextUtils.isEmpty(businessInfo.province)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请选择城市");
            return;
        }
        if (TextUtils.isEmpty(businessInfo.city)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请选择城市");
            return;
        }
        if (TextUtils.isEmpty(businessInfo.address)) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请填写详细地址");
            return;
        }
        if (!hasSetCoordinate) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("设置店铺坐标");
            return;
        }
        if (TextUtils.isEmpty(businessInfo.businessStoreImages.get(0))) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("设置店铺招牌");
            return;
        }
        if (businessInfo.category == -1) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请选择行业类型");
            return;
        }
        if (TextUtils.isEmpty(mlist.get(0).getPhotoPath())){
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请添加营业执照信息");
            return;
        }
        if (TextUtils.isEmpty(mlist.get(1).getPhotoPath())) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请添加手持身份证信息");
            return;
        }
        if (TextUtils.isEmpty(mlist.get(2).getPhotoPath())) {
            BufferCircleDialog.dialogcancel();
            mActivity.toast("请添加身份证背面信息");
            return;
        }
        if(!phone_num.matches("1[345789][0-9]{9,9}")){
            BufferCircleDialog.dialogcancel();

            mActivity.toast("请输入正确的联系电话");

            return;
        }
        List<String> list=new ArrayList<>();
        for (int i = 0; i < mlist.size(); i++) {
            LogUtil.d(mlist.size()+"1111111111111111");
            list.add(i,mlist.get(i).getPhotoPath());
        }
        businessInfo.identityPicture=list;

        businessInfo.businessName=name;

        businessInfo.name=contacts_name;

        businessInfo.tel=phone_num;

        businessInfo.businessRegistrationNo=regis_number;

        mActivity.businessInfo=businessInfo;

        mActivity.jump();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.Login_SettledAlliance_getBusinessInfo) {
            BufferCircleDialog.dialogcancel();

            mBusinessinfo = (BusinessInfo) data.t;

            mActivity.businessInfo = mBusinessinfo;

            mActivity.setData();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }

        mActivity.toast(erroinfo);
    }


}

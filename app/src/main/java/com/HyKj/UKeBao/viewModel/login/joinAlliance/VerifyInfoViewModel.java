package com.HyKj.UKeBao.viewModel.login.joinAlliance;

import android.text.TextUtils;
import android.widget.CheckBox;


import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.login.joinAlliance.VerifyInfo.VerifyInfoModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.VerifyInfo.VerifyInfoActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.io.File;

/**
 * Created by Administrator on 2016/9/18.
 */
public class VerifyInfoViewModel extends BaseViewModel {
    private VerifyInfoModel mModel;

    private VerifyInfoActivity mActivity;

    private int position = 0;

    private BusinessInfo businessInfo;

    private String feedBack;

    private String businessStoreId;
    public VerifyInfoViewModel(VerifyInfoModel model, VerifyInfoActivity activity) {
        mModel = model;
        mActivity = activity;
        mModel.setView(this);
    }

    //提交商家申请数据
    public void commit(BusinessInfo businessInfo, CheckBox cb, String feedBack, String businessStoreId) {

        this.businessStoreId=businessStoreId;

        this.feedBack = feedBack;

        if (!cb.isChecked()) {
            mActivity.toast("请阅读协议并勾选");
            return;
        }
        //上传店铺信息图
        else if (businessInfo.identityPicture.size() == 3 &&
                businessInfo.identityPicture.get(0) != null &&
                businessInfo.identityPicture.get(1) != null &&
                businessInfo.identityPicture.get(2) != null) {

            this.businessInfo = businessInfo;
            for (int i = 0; i < 3; i++) {
                mModel.uploadImage(new File(businessInfo.identityPicture.get(i)));
            }
        }

    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取服务器返回照片储存路径
        if (data.action == Action.Login_SettledAlliance_Commit_uploadImage) {

            StoreSignage storeInfo = (StoreSignage) data.t;

            String imagePrefix = storeInfo.getRows().getImagePrefix();

            String imageSrc = storeInfo.getRows().getImageSrc();

            String url = imagePrefix + imageSrc;

            businessInfo.identityPicture.set(position, url);

            position++;

            //当三张图片上传成功后上传数据
            if (position == 3 && TextUtils.isEmpty(feedBack)) {
                LogUtil.d("申请成功页面");
                mModel.first_commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
                        businessInfo.businessRegistrationNo, businessInfo.category,
                        businessInfo.province, businessInfo.city, businessInfo.area,
                        businessInfo.address, businessInfo.longitude, businessInfo.latitude,
                        businessInfo.businessStoreImages.get(0), businessInfo.identityPicture);
            } else if(position == 3 && !TextUtils.isEmpty(feedBack)){
                //审核失败时重新填写之后上传
                LogUtil.d("重新审核上传图片");
                mModel.commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
                        businessInfo.businessRegistrationNo, businessInfo.category,
                        businessInfo.province, businessInfo.city, businessInfo.area,
                        businessInfo.address, businessInfo.longitude, businessInfo.latitude,
                        businessInfo.businessStoreImages.get(0), businessInfo.identityPicture, businessStoreId);
            }
        }
        //当数据数据上传成功时
        else if (data.action == Action.Login_SettledAlliance_Commit_userInfo) {
            int status = (int) data.t;
            if (status == 0) {
                mActivity.toast("添加成功！");
                //通知界面跳转
                mActivity.jump();
            } else {
                mActivity.toast("添加失败！登陆已过期，请重新登录~");
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo);
    }
}

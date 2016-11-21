package com.HyKj.UKeBao.viewModel.login.joinAlliance;

import android.text.TextUtils;
import android.widget.CheckBox;


import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.model.login.joinAlliance.VerifyInfo.VerifyInfoModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.VerifyInfo.VerifyInfoActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/18.
 */
public class VerifyInfoViewModel extends BaseViewModel {
    private VerifyInfoModel mModel;

    private VerifyInfoActivity mActivity;

    private int position = 0;//添加照片成功返回数

    private BusinessInfo businessInfo;

    private String feedBack;

    private String businessStoreId;

    private int upload_ImageNum = 3;//需要上传照片总数

    private int new_localImage = 0;//修改后本地照片的数量

    private ArrayList<Integer> local_position = new ArrayList<>();//需要上传的本地图片下标集合

    public VerifyInfoViewModel(VerifyInfoModel model, VerifyInfoActivity activity) {
        mModel = model;
        mActivity = activity;
        mModel.setView(this);
    }

    //提交商家申请数据
    public void commit(BusinessInfo businessInfo, CheckBox cb, String feedBack, String businessStoreId) {

        this.businessStoreId = businessStoreId;

        this.feedBack = feedBack;

        if (!cb.isChecked()) {
            mActivity.toast("请阅读协议并勾选");

            BufferCircleDialog.dialogcancel();

            return;
        }
        //无反馈信息
        else if (TextUtils.isEmpty(feedBack)) {

            this.businessInfo = businessInfo;

            upload_ImageNum = businessInfo.identityPicture.size();

            mModel.uploadImage(new File(businessInfo.identityPicture.get(position)));

        }
        //当审核不通过
        else if (!TextUtils.isEmpty(feedBack)) {

            this.businessInfo = businessInfo;

            //判断本地图片数量
            for (int i = 0; i < businessInfo.identityPicture.size(); i++) {
                String tag = businessInfo.identityPicture.get(i).substring(0, 4);

                if (!tag.equals("http")) {
                    new_localImage++;

                    local_position.add(i);
                }
            }
            //无本地图片
            if (new_localImage == 0) {
                mModel.first_commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
                        businessInfo.businessRegistrationNo, businessInfo.category,
                        businessInfo.province, businessInfo.city, businessInfo.area,
                        businessInfo.address, businessInfo.longitude, businessInfo.latitude,
                        businessInfo.businessStoreImages.get(0), businessInfo.identityPicture);
            }
            //有本地图片
            else if (new_localImage > 0) {
                mModel.uploadImage(new File(businessInfo.identityPicture.get(local_position.get(position))));//拿出第一张本地图片上传

            }
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取服务器返回照片储存路径
        if (data.action == Action.Login_SettledAlliance_Commit_uploadImage) {

            LogUtil.d("提交申请——添加照片成功");

            StoreSignage storeInfo = (StoreSignage) data.t;

            String imagePrefix = storeInfo.getRows().getImagePrefix();

            String imageSrc = storeInfo.getRows().getImageSrc();

            String url = imagePrefix + imageSrc;

            LogUtil.d("提交申请——添加照片成功,position:" + position + "feedBack:" + feedBack);

            //当三张图片上传成功后上传数据
            if (TextUtils.isEmpty(feedBack)) {

                businessInfo.identityPicture.set(position, url);

                position++;

                LogUtil.d("申请成功页面");

                //判断是否添加完毕
                if(position == upload_ImageNum ) {
                    mModel.first_commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
                            businessInfo.businessRegistrationNo, businessInfo.category,
                            businessInfo.province, businessInfo.city, businessInfo.area,
                            businessInfo.address, businessInfo.longitude, businessInfo.latitude,
                            businessInfo.businessStoreImages.get(0), businessInfo.identityPicture);
                }else {
                    mModel.uploadImage(new File(businessInfo.identityPicture.get(position)));
                }
            } else if (!TextUtils.isEmpty(feedBack)) {
                LogUtil.d("有本地照片");

                businessInfo.identityPicture.set(local_position.get(position), url);

                position++;

                //判断本地图片是否添加完毕
                if (position == new_localImage) {
                    LogUtil.d("提交审核不通过信息！" + businessInfo.identityPicture);
                    mModel.commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
                            businessInfo.businessRegistrationNo, businessInfo.category,
                            businessInfo.province, businessInfo.city, businessInfo.area,
                            businessInfo.address, businessInfo.longitude, businessInfo.latitude,
                            businessInfo.businessStoreImages.get(0), businessInfo.identityPicture, businessStoreId);
                }else {
                    mModel.uploadImage(new File(businessInfo.identityPicture.get(local_position.get(position))));
                }
            }
//            else if(position == storeImageNum && !TextUtils.isEmpty(feedBack)){
//                //审核失败时重新填写之后上传
//                LogUtil.d("重新审核上传图片");
//                mModel.commit(businessInfo.businessName, businessInfo.name, businessInfo.tel,
//                        businessInfo.businessRegistrationNo, businessInfo.category,
//                        businessInfo.province, businessInfo.city, businessInfo.area,
//                        businessInfo.address, businessInfo.longitude, businessInfo.latitude,
//                        businessInfo.businessStoreImages.get(0), businessInfo.identityPicture, businessStoreId);
//            }
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

package com.HyKj.UKeBao.viewModel.businessManage.businessSettings;

import android.text.TextUtils;

import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreGoodsModel;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.GalleryFinalUtil;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.businessSettings.BusinessStoreGoodsActivity;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.GoodsImageRecycleViewAdapter;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.MyRecycleViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;

/**
 * Created by Administrator on 2016/11/13.
 */
public class BusinessStoreGoodsViewModel extends BusinessStoreImageViewModel {
    private BusinessStoreGoodsModel mModel;

    private BusinessStoreGoodsActivity mActivity;

    private boolean flag;//判断商品是否为空标记

    private int delete_num = 0;//删除照片数目

    private int delete_success = 0;//成功删除照片数

    private List<Integer> delete_list = new ArrayList<>();//删除照片id集合

    public BusinessStoreGoodsViewModel(BusinessStoreGoodsActivity activity, BusinessStoreGoodsModel model) {
        mActivity = activity;

        mModel = model;

        mModel.setView(this);
    }

    //判断是否该打开相册
    public void addData(GoodsImageRecycleViewAdapter adapter,
                        int postion, List<String> data,
                        FunctionConfig functionConfig,
                        GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
        if (data.get(postion).equals("end")) {
            GalleryFinalUtil.openMuti(mActivity, mActivity.getSupportFragmentManager(), functionConfig, mOnHanlderResultCallback);
        }
    }

    //判断图片数量
    public void updataImage(List<String> data) {
        pictureList = data;

        if (((pictureList.size() - 1) < 3)) {
            mActivity.toast("至少上传三张照片哦~亲", mActivity);
        } else {
            getUpdataPosition();//上传图片到服务器
        }
    }

    //执行上传方法，发送请求
    public void updata(int position) {
        addImage_position = position;

        if (pictureList.get(pictureList.size() - 1).equals("end")) {
            File file = new File(pictureList.get(addImage_position));

            mModel.updataIamgeVacancy(file);
        } else {
            File file = new File(pictureList.get(addImage_position));

            mModel.updataIamge(file);

        }
    }

    //检验用户是否输入正确商品名称
    public boolean inspect_GoodsName(List<String> goodsName_list) {
        for (int i = 0; i < goodsName_list.size(); i++) {

            if (goodsName_list.get(i).equals("请输入商品名称") || TextUtils.isEmpty(goodsName_list.get(i))) {
                flag = false;

                break;
            } else {
                flag = true;
            }
        }
        return flag;
    }

    //从集合中判断出本地图片并做标记
    public void getUpdataPosition() {
        for (int i = 0; i < pictureList.size(); i++) {
            String tag = pictureList.get(i).substring(0, 3);

            LogUtil.d("tag" + tag);

            if (!tag.equals("htt") && !tag.equals("end")) {
                LogUtil.d("i" + i);

                updata(i);

                return;
            }
        }
        //判断最后一张图是否为添加背景图
        if (pictureList.get(pictureList.size() - 1).equals("end")) {
            pictureList.remove(pictureList.size() - 1);
        }
        mActivity.updataSuccess(pictureList);
    }

    //删除照片
    public void delete_phone(List<Integer> delete_list, List<String> data) {
        pictureList = data;

        //判断是否需要删除
        if (delete_list.size() != 0) {
            this.delete_list = delete_list;

            delete_num = delete_list.size();

            mModel.delete(delete_list.get(delete_success));//删除第一张
        }else {
            updataImage(pictureList);
        }
    }


    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.BusinessManage_businessSettings_updataImage) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String url = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            //把服务器返回的路径替换掉集合原来的本地路径
            pictureList.set(addImage_position++, url);

            LogUtil.d("addGoods_position" + addImage_position);

            LogUtil.d(pictureList.toString());

            if (addImage_position == pictureList.size()) {

                mActivity.updataSuccess(pictureList);
            } else {
                File file = new File(pictureList.get(addImage_position));

                mModel.updataIamge(file);
            }

        } else if (data.action == Action.BusinessManage_businessSettings_updataImageVacancy) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String url = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            //把服务器返回的路径替换掉集合原来的本地路径
            pictureList.set(addImage_position++, url);

            LogUtil.d("Vacancy_position" + addImage_position);

            LogUtil.d(pictureList.toString());

            if (addImage_position == pictureList.size() - 1) {
                pictureList.remove(pictureList.size() - 1);//移除最后一张背景图

                mActivity.updataSuccess(pictureList);
            } else {
                File file = new File(pictureList.get(addImage_position));

                mModel.updataIamgeVacancy(file);
            }
        } else if (data.action == Action.BusinessManage_Delete_Goods) {
            delete_num--;

            delete_success++;

            if (delete_num == 0) {
                updataImage(pictureList);
            } else {
                mModel.delete(delete_list.get(delete_success));//删除第一张
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()) {
            BufferCircleDialog.dialogcancel();
        }

        mActivity.toast(erroinfo, mActivity);
    }

}

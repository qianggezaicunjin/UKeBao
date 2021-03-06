package com.HyKj.UKeBao.viewModel.businessManage.businessSettings;

import android.databinding.Bindable;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreImageModel;
import com.HyKj.UKeBao.model.login.baen.StoreSignage;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.GalleryFinalUtil;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.businessManage.businessSettings.BusinessStoreImageActivity;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.MyRecycleViewAdapter;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/10/11.
 */
public class BusinessStoreImageViewModel extends BaseViewModel {

    private BusinessStoreImageActivity activitiy;

    private BusinessStoreImageModel mModel;

    @Bindable
    public int pictureNum;//相片数量

    public int addImage_position;//添加第一张本地照片的下标

    @Bindable
    public List<String> pictureList = new ArrayList<>();

    public BusinessStoreImageViewModel(BusinessStoreImageActivity activity, BusinessStoreImageModel model) {
        activitiy = activity;

        mModel = model;

        mModel.setView(this);
    }

    public BusinessStoreImageViewModel() {

    }

    //判断是否显示添加图片背景
    public List<String> isAddImage(List<String> pictures) {
        if (pictures.size() != 5) {
            pictures.add(pictures.size(), "end");
        }
        return pictures;
    }

    //判断是否该打开相册
    public void addData(MyRecycleViewAdapter adapter,
                        int postion, List<String> data,
                        FunctionConfig functionConfig,
                        GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
        if (data.get(postion).equals("end")) {
            GalleryFinalUtil.openMuti(activitiy, activitiy.getSupportFragmentManager(), functionConfig, mOnHanlderResultCallback);
        }
    }

    //添加本地图片操作
    public List<String> addLocalImage(List<String> data, List<PhotoInfo> resultList) {
        int size = data.size();

        for (int i = 0; i < resultList.size(); i++) {
            data.set(size - 1, resultList.get(i).getPhotoPath());

            data.add(size++, "end");

            if (size == 6) {
                data.remove(size - 1);

                break;
            }
        }
        return data;
    }

    //刷新现有照片数量
    public void refresh(List<String> list) {
        pictureNum = list.size();

        if (list.get(pictureNum - 1).equals("end")) {
            pictureNum = list.size() - 1;
        }
        notifyPropertyChanged(BR.pictureNum);
    }

    public String getPictureNum() {
        return pictureNum + "/5";
    }

    //判断图片数量
    public void updataImage(List<String> data) {
        pictureList = data;

        if (((pictureList.size() - 1) < 3)) {
            activitiy.toast("至少上传三张照片哦~亲", activitiy);
        } else {
            BufferCircleDialog.show(activitiy, "上传中，请稍候..", false, null);

            getUpdataPosition();//上传图片到服务器
        }
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
        activitiy.updataSuccess(pictureList);
    }

    //执行上传方法，发送请求
    public void updata(int position) {
        addImage_position = position;
        //判断是否存在添加背景
        if (pictureList.get(pictureList.size() - 1).equals("end")) {

            File file = new File(pictureList.get(addImage_position));

            mModel.updataIamgeVacancy(file);

        }else{
            File file = new File(pictureList.get(addImage_position));

            mModel.updataIamge(file);
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

                activitiy.updataSuccess(pictureList);
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
                pictureList.remove(pictureList.size()-1);//移除最后一张背景图

                activitiy.updataSuccess(pictureList);
            } else {
                File file = new File(pictureList.get(addImage_position));

                mModel.updataIamgeVacancy(file);
            }
        }
    }

        @Override
        public void onRequestErroInfo (String erroinfo){
            if (BufferCircleDialog.isShowDialog()) {
                BufferCircleDialog.dialogcancel();
            }

            activitiy.toast(erroinfo, activitiy);
        }
    }

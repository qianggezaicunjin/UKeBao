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
import java.util.List;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/10/11.
 */
public class BusinessStoreImageViewModel extends BaseViewModel {

    private BusinessStoreImageActivity mActivitiy;

    private BusinessStoreImageModel mModel;

    @Bindable
    private int pictureNum;//相片数量

    private int position;//添加第一张本地照片的下标

    @Bindable
    private List<String> pictureList;

    public BusinessStoreImageViewModel(BusinessStoreImageActivity activity, BusinessStoreImageModel model) {
        mActivitiy = activity;

        mModel = model;

        mModel.setView(this);
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
            GalleryFinalUtil.openMuti(mActivitiy, mActivitiy.getSupportFragmentManager(), functionConfig, mOnHanlderResultCallback);
        }
    }

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
            mActivitiy.toast("至少上传三张照片哦~亲", mActivitiy);
        } else {
            BufferCircleDialog.show(mActivitiy,"上传中，请稍候..",false,null);

            getUpdataPosition();//上传图片到服务器
        }
    }

    //从集合中判断出本地图片并做标记
    private void getUpdataPosition() {
        for (int i = 0; i < pictureList.size(); i++) {
            String tag = pictureList.get(i).substring(0, 3);

            LogUtil.d("tag" + tag);

            if (!tag.equals("htt") && !tag.equals("end")) {
                LogUtil.d("i" + i);

                updata(pictureList, i);

                break;
            }
        }
    }

    //执行上传方法，发送请求
    private void updata(List<String> data, int position) {
        this.position = position;
        if (data.get(data.size() - 1).equals("end")) {
            for (int i = 0; i < data.size() - 1 - position; i++) {
                File file = new File(data.get(position));

                mModel.updataIamgeVacancy(file);
            }
        } else {
            for (int i = 0; i < data.size() - position; i++) {
                File file = new File(data.get(position));

                mModel.updataIamge(file);
            }
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.BusinessManage_businessSettings_updataImage) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String url = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            //把服务器返回的路径替换掉集合原来的本地路径
            pictureList.set(position++, url);

            LogUtil.d("position" + position);

            LogUtil.d(pictureList.toString());

            if (position == pictureList.size()) {
                mActivitiy.updataSuccess(pictureList);
            }

        } else if (data.action == Action.BusinessManage_businessSettings_updataImageVacancy) {
            StoreSignage storeSignage = (StoreSignage) data.t;

            String url = storeSignage.getRows().getImagePrefix() + storeSignage.getRows().getImageSrc();

            //把服务器返回的路径替换掉集合原来的本地路径
            pictureList.set(position++, url);

            LogUtil.d("Vacancy_position" + position);

            LogUtil.d(pictureList.toString());

            if (position == pictureList.size() - 1) {
                mActivitiy.updataSuccess(pictureList);
            }
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        BufferCircleDialog.dialogcancel();

        mActivitiy.toast(erroinfo, mActivitiy);
    }
}

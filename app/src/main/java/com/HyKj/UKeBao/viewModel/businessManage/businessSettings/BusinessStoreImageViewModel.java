package com.HyKj.UKeBao.viewModel.businessManage.businessSettings;

import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreImageModel;
import com.HyKj.UKeBao.util.GalleryFinalUtil;
import com.HyKj.UKeBao.view.activity.businessManage.businessSettings.BusinessStoreImageActivity;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.MyRecycleViewAdapter;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

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

    public List<String> addLocalImage(List<String> data,List<PhotoInfo> resultList) {
        int size=data.size();

        for(int i=0;i<resultList.size();i++){
            data.set(size-1,resultList.get(i).getPhotoPath());

            data.add(size++,"end");

            if(size==6){
                data.remove(size-1);

                break;
            }
        }
        return data;
    }
}

package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessSettingsBinding;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessSettingsModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessSettingsViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BusinessSettingsActivity extends BaseActiviy {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BusinessSettingsActivity.class);

        return intent;
    }

    private ActivityBusinessSettingsBinding mBinding;

    private BusinessSettingsViewModel viewModel;

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    public List<String> pictureList;

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_settings);

        viewModel = new BusinessSettingsViewModel(this, new BusinessSettingsModel());

        viewModel.getBusinessInfo();

        mBinding.setViewModel(viewModel);

        initGalleryFinal();
    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺设置");
    }

    @Override
    public void setListeners() {
        mBinding.rlPhotoAlbumSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          GalleryFinalUtil.openMuti(BusinessSettingsActivity.this,getSupportFragmentManager(),functionConfig,mOnHanlderResultCallback);
            Intent intent=BusinessStoreImageActivity.getStartIntent(BusinessSettingsActivity.this);

                LogUtil.d("点击了进入店铺相册页面，获取数据:"+pictureList);

            intent.putExtra("pictures", (Serializable) pictureList);

            startActivity(intent);
            }
        });
    }

    //初始化GalleryFinal
    public void initGalleryFinal() {
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(5)
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        imageloade = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloade, MyApplication.themeConfig)
                .setFunctionConfig(functionConfig)
                .setNoAnimcation(false)
                .setPauseOnScrollListener(MyApplication.pauseOnScrollListener)
                .build();

        GalleryFinal.init(coreConfig);
    }
   private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback= new GalleryFinal.OnHanlderResultCallback(){
       @Override
       public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

       }

       @Override
       public void onHanlderFailure(int requestCode, String errorMsg) {

       }
   };
}

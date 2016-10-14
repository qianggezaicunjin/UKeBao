package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessSettingsBinding;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessSettingsModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
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

    public List<String> pictureList;

    public static final int RESULT_Settings_Success = 9;

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_settings);

        BufferCircleDialog.show(this, "正在获取店铺数据...", false, null);

        viewModel = new BusinessSettingsViewModel(this, new BusinessSettingsModel());

        viewModel.getBusinessInfo();

        mBinding.setViewModel(viewModel);

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
                Intent intent = BusinessStoreImageActivity.getStartIntent(BusinessSettingsActivity.this);

                LogUtil.d("点击了进入店铺相册页面，获取数据:" + pictureList);

                intent.putExtra("pictures", (Serializable) pictureList);

                startActivityForResult(intent, RESULT_Settings_Success);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_Settings_Success:

                    LogUtil.d("BusinessSettings回调成功");

                    ArrayList<String> pictures = data.getStringArrayListExtra("pictures");

                    if (pictures != null) {
                        pictureList = pictures;

                        mBinding.tvPhotoAlbumSetting.setText("修改/已设置");
                    }

                    break;
            }
        }
    }
}

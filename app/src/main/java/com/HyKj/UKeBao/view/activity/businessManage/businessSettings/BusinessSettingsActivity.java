package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessSettingsBinding;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessSettingsModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.LoginUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessSettingsViewModel;
import com.squareup.picasso.Picasso;

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

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_settings);

        viewModel = new BusinessSettingsViewModel(this, new BusinessSettingsModel());

        mBinding.setViewModel(viewModel);

        viewModel.getBusinessInfo();

    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺设置");
    }

    @Override
    public void setListeners() {

    }
}

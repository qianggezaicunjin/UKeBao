package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.View;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityVipFunctionBinding;
import com.HyKj.UKeBao.model.userInfoManage.VipFunctionModel;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.marketingManage.VipFunctionViewModel;

/**
 * VIP功能介绍
 * Created by Administrator on 2016/11/14.
 */
public class VipFunctionActivity extends BaseActiviy{
    private ActivityVipFunctionBinding mBinding;

    private VipFunctionViewModel viewModel;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,VipFunctionActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        SystemBarUtil.changeColor(R.color.vip_title_backgroud);

        mBinding=DataBindingUtil.setContentView(this,R.layout.activity_vip_function);

        viewModel=new VipFunctionViewModel(new VipFunctionModel());

        //获取客服电话
        viewModel.getOfficePhone();

        mBinding.setViewModel(viewModel);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setListeners() {
        //退出
        mBinding.imbTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //跳转到支付界面
        mBinding.btChargeVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PayVipActivity.getStartIntent(VipFunctionActivity.this));
            }
        });
    }
}

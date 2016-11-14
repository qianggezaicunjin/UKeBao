package com.HyKj.UKeBao.view.activity.businessManage.financialManagement;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityRealIncomeDetailBinding;
import com.HyKj.UKeBao.model.businessManage.financial.RealIncomeDetailModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.marketingManage.ExchangRecordActivity;
import com.HyKj.UKeBao.viewModel.businessManage.financialManagement.RealIncomeDetailViewModel;

/**
 * 实收详情
 * Created by Administrator on 2016/11/10.
 */
public class RealIncomeDetailActivity extends BaseActiviy{
    private ActivityRealIncomeDetailBinding mBinding;

    private String startTime;

    private String endTime;

    private RealIncomeDetailViewModel viewModel;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,RealIncomeDetailActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_real_income_detail);

        startTime=getIntent().getStringExtra("startTime");

        endTime=getIntent().getStringExtra("endTime");

        viewModel=new RealIncomeDetailViewModel(new RealIncomeDetailModel(),this);

        mBinding.setViewModel(viewModel);

        BufferCircleDialog.show(this,"查询中,请稍候~",true,null);

        viewModel.getRealDetail(startTime,endTime);
    }

    @Override
    public void setUpView() {
        setTitleTheme("实收详情");
    }

    @Override
    public void setListeners() {
        mBinding.rlExchangeRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ExchangRecordActivity.getStartIntent(RealIncomeDetailActivity.this));
            }
        });
    }
}

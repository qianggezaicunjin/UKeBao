package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityMybankcardBinding;
import com.HyKj.UKeBao.model.userInfoManage.MyBankCardModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.userInfoManage.MyBankCardViewModel;

/**
 * 提现账户
 * Created by Administrator on 2016/11/9.
 */
public class MyBankCardActivity extends BaseActiviy{
    private ActivityMybankcardBinding mBinding;

    private MyBankCardViewModel viewModel;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,MyBankCardActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_mybankcard);

        viewModel=new MyBankCardViewModel(this,new MyBankCardModel());

        mBinding.setViewModel(viewModel);

        BufferCircleDialog.show(this,"正在加载数据...请稍候~",false,null);

        viewModel.getBusinessInfo();
    }

    @Override
    public void setUpView() {
        setTitleTheme("我的银行卡");
    }

    @Override
    public void setListeners() {
        mBinding.btBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddBankActivity.getStartIntent(MyBankCardActivity.this));
            }
        });
    }

    //未绑定银行卡执行的操作
    public void notBinding() {
        mBinding.rlBindingView.setVisibility(View.VISIBLE);

        mBinding.rlBankInfo.setVisibility(View.GONE);

        BufferCircleDialog.dialogcancel();
    }

    //已绑定银行卡执行操作
    public void binding() {
        mBinding.rlBindingView.setVisibility(View.GONE);

        mBinding.rlBankInfo.setVisibility(View.VISIBLE);

        BufferCircleDialog.dialogcancel();
    }
    @Override
    protected void onResume() {
        super.onResume();

        BufferCircleDialog.show(this,"正在加载数据...请稍候~",false,null);

        viewModel.getBusinessInfo();
    }


}

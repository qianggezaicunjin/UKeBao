package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityApplyCashAccountBinding;
import com.HyKj.UKeBao.model.userInfoManage.AddBankCardModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LoginUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.userInfoManage.AddBankCardViewModel;

/**
 * 添加银行卡
 * Created by Administrator on 2016/11/9.
 */
public class AddBankActivity extends BaseActiviy{
    private ActivityApplyCashAccountBinding mBinding;

    private SharedPreferences sp;

    private AddBankCardViewModel viewModel;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,AddBankActivity.class);

        return intent;
    }
    @Override
    public void onCreateBinding() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_apply_cash_account);

        sp=getSharedPreferences("user_login",MODE_PRIVATE);

        viewModel=new AddBankCardViewModel(new AddBankCardModel(),this);

        mBinding.setViewModel(viewModel);
    }

    @Override
    public void setUpView() {
        setTitleTheme("添加银行卡");
    }

    @Override
    public void setListeners() {
        //发送验证码
        mBinding.btnSendSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUtil loginUtil = new LoginUtil(AddBankActivity.this);

                String phone=sp.getString("phone","");

                loginUtil.getSecurityCode(mBinding.btnSendSecurityCode,phone);
            }
        });

        //添加银行卡
        mBinding.btnConfirmAccountApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankName=mBinding.etAccountBankNameInput.getText().toString().trim();

                String bankNo=mBinding.etAccountCardNumberInput.getText().toString().trim();

                String name=mBinding.etAccountNameInput.getText().toString().trim();

                String code=mBinding.etSecurityCodeInput.getText().toString().trim();

                BufferCircleDialog.show(AddBankActivity.this,"正在处理...请稍候~",true,null);

                viewModel.addBankCard(bankName,bankNo,name,code);
            }

        });
    }


}

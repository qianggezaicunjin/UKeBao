package com.HyKj.UKeBao.view.activity.login.regist;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityRegistBinding;
import com.HyKj.UKeBao.model.login.baen.RegistInfo;
import com.HyKj.UKeBao.model.login.regist.RegistModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LoginUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.JoinAllianceActivity;
import com.HyKj.UKeBao.viewModel.login.regist.RegistViewModel;

/**
 *  注册页面
 * Created by Administrator on 2016/8/23.
 */
public class RegistActivity extends BaseActiviy {

    private ActivityRegistBinding mBinding;

    private RegistViewModel viewModel;

    public long phone;

    private ImageButton bt_back;

    private TextView tv_title;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RegistActivity.class);
        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_regist);

        viewModel = new RegistViewModel(RegistActivity.this, new RegistModel());

        bt_back = (ImageButton) findViewById(R.id.imb_title_bar_back);

        tv_title = (TextView) findViewById(R.id.tv_title_bar_name);
    }

    @Override
    public void setUpView() {
        setTitleTheme("注册页面");
    }

    @Override
    public void setListeners() {

        //退出按钮监听
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //点击获取验证码
        mBinding.btnSendRegisterSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferCircleDialog.show(RegistActivity.this, "数据提交中,请稍候..", false, null);

                viewModel.isExistence(Long.parseLong(mBinding.etPhoneNumberInput.getText().toString().trim()));
            }
        });

        //注册按钮被按下
        mBinding.btnRegisterAlliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取验证码
                String smsSecurityCode = mBinding.etRegisterSecurityCodeInput.getText().toString();
                //获取密码
                String passWord = mBinding.etLoginPasswordInput.getText().toString();
                //获取确认密码
                String passwordConfirm = mBinding.etConfirmLoginPasswordInput.getText().toString();
                phone=Long.parseLong(mBinding.etPhoneNumberInput.getText().toString().trim());
                if (smsSecurityCode.equals("")) {
                    toast("请输入验证码!");
                    return;
                }
                if (String.valueOf(phone).equals("")) {
                    toast("请输入电话号码！");
                    return;
                }
                if (passWord.equals("")) {
                    toast("请输入密码！");
                    return;
                }
                if (passWord.length() < 6) {
                    toast("密码最少6位数");
                    return;
                }
                if (passwordConfirm.equals("")) {
                    toast("请输入密码！");
                    return;
                }
                if (passwordConfirm.length() < 6) {
                    toast("密码最少6位数");
                    return;
                }
                if (!(passWord.equals(passwordConfirm))) {
                    toast("两次密码输入不一致,请重新输入！");
                    return;
                }
                BufferCircleDialog.show(RegistActivity.this, "玩命注册中..请稍候~", false, null);

                viewModel.regist(smsSecurityCode,phone, passWord);
            }
        });
    }

    //注册请求成功
    public void registSuccess(RegistInfo registInfo) {
        BufferCircleDialog.dialogcancel();

        Toast.makeText(this, registInfo.getMsg(), Toast.LENGTH_SHORT).show();

        if (registInfo.getSuccess()) {
            startActivity(JoinAllianceActivity.getStartIntent(RegistActivity.this));

            RegistActivity.this.finish();
        }

    }

    public void toast(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    //获取验证码
    public void getSecurityCode() {
        LoginUtil loginUtil = new LoginUtil(RegistActivity.this);

        loginUtil.getSecurityCode(mBinding.btnSendRegisterSecurityCode, mBinding.etPhoneNumberInput);
    }
}

package com.HyKj.UKeBao.view.activity.login.forgetPassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.forgetPassword.ForgetPasswordModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LoginUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.login.forgetPassword.ForgetPasswordViewModel;

/**
 * 找回密码
 * Created by Administrator on 2016/8/25.
 */
public class ForgetPasswordActivity extends BaseActiviy {

    private SharedPreferences sp;

    private ForgetPasswordViewModel viewModel;

    private ImageButton backImageButton;
    /**
     * 用户名编辑框
     */
    private EditText et_user;//用户名编辑框
    /**
     * 新密码
     */
    private EditText newPassword;
    /**
     * 确认新密码
     */
    private EditText confirmNewPassword;
    /**
     * 验证码
     */
    private EditText securityCodeInput;
    /**
     * 发送验证码
     */
    private Button sendSecurityCode;
    /**
     * 完成找回密码
     */
    private Button completeFindPassword;
    /**
     * 标题名字
     */
    private TextView titleBarName;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, ForgetPasswordActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {


        setContentView(R.layout.activity_forgetpassword);

        viewModel = new ForgetPasswordViewModel(new ForgetPasswordModel(), this);//初始化viewModel

        sp = getSharedPreferences("user_login", MODE_PRIVATE);//初始化sharedPreferences

        et_user = (EditText) findViewById(R.id.et_account_input);

        // 找到控件
        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        securityCodeInput = (EditText) findViewById(R.id.et_security_code_input);

        newPassword = (EditText) findViewById(R.id.et_new_password_input);

        confirmNewPassword = (EditText) findViewById(R.id.et_confirm_password_input);

        sendSecurityCode = (Button) findViewById(R.id.btn_send_security_code);

        completeFindPassword = (Button) findViewById(R.id.btn_complete_find_password);

        backImageButton= (ImageButton) findViewById(R.id.imb_title_bar_back);

        titleBarName= (TextView) findViewById(R.id.tv_title_bar_name);

        confirmNewPassword= (EditText) findViewById(R.id.et_confirm_password_input);
    }

    @Override
    public void setUpView() {
        et_user.setText(viewModel.displayUserName(sp));//显示用户名

        setTitleTheme("找回密码");
    }


    @Override
    public void setListeners() {
        //设置发送验证码按钮监听
        sendSecurityCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferCircleDialog.show(ForgetPasswordActivity.this,"正在加载,请稍候~",false,null);

                viewModel.isExistence(Long.parseLong(et_user.getText().toString().trim()));

            }
        });

        //设置完成按钮监听
        completeFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferCircleDialog.show(ForgetPasswordActivity.this,"正在加载,请稍候~",false,null);

                //拿到EdiText数据
                String code=securityCodeInput.getText().toString().trim();

                String password=newPassword.getText().toString().trim();

                String  phone=et_user.getText().toString().trim();

                String confirm=confirmNewPassword.getText().toString().trim();

                viewModel.forgetPassword(code,password,phone,confirm);
            }
        });

        //设置返回按钮监听
        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //获取验证码
    public void getSecurityCode() {
        LoginUtil loginUtil=new LoginUtil(ForgetPasswordActivity.this);

        loginUtil.getSecurityCode(sendSecurityCode,et_user);
    }
}

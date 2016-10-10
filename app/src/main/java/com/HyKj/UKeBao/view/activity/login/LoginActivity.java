package com.HyKj.UKeBao.view.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.SplashModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.BaseFragmentActivity;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.activity.login.examine.ExamineActivity;
import com.HyKj.UKeBao.view.activity.login.forgetPassword.ForgetPasswordActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.JoinAllianceActivity;
import com.HyKj.UKeBao.view.activity.login.regist.RegistActivity;
import com.HyKj.UKeBao.viewModel.login.SplashViewModel;

import com.HyKj.UKeBao.databinding.ActivityLoginBinding;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/8/22.
 */
public class LoginActivity extends BaseFragmentActivity {
    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        return intent;
    }

    private ActivityLoginBinding mBinding;

    public SplashViewModel viewModel;

    private String account;//用户名

    private String passWord;//密码

    private SharedPreferences sharedPreferences;

    private Boolean isFirst=true;//是否第一次登陆


    @Override
    public void onCreateBinding() {
        SystemBarUtil.initSystemBar(this);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new SplashViewModel(new SplashModel(),LoginActivity.this);

    }

    public void setUpView() {

        isFirst();//判断是否显示用户名和头像

        land();//设置登陆按钮监听

        regist();//设置申请联盟监听

        forgetPassword();//设置找回密码监听
    }

    @Override
    public void setListeners() {

    }

    //设置找回密码监听
    private void forgetPassword() {
        mBinding.btnCannotLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ForgetPasswordActivity.getStartIntent(LoginActivity.this));
            }
        });
    }

    //申请联盟
    private void regist() {
        mBinding.btnApplyAlliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启申请联盟
                startActivity(RegistActivity.getStartIntent(LoginActivity.this));

            }
        });
    }

    //登陆按钮被按下
    private void land() {
        mBinding.btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferCircleDialog.show(LoginActivity.this,"登陆中。。",false,null);

                account = mBinding.etUserNameInput.getText().toString();

                passWord = mBinding.etUserPasswordInput.getText().toString();

                //商家端：5
                String identityId = "5";

                // android:3 ios:4
                int deviceType = 3;

                Log.d("登陆界面登陆按钮被按下", "发送网络请求，设备号为");

                //将数据请求网络
                viewModel.userLogin(account, passWord, identityId, deviceType, MyApplication.channelId);
            }
        });
    }

    //根据审核状态
    public void getData(String msg,int isExamine){

        SharedPreferences sp=getSharedPreferences("user_login", MODE_PRIVATE);

        SharedPreferences.Editor editor=sp.edit();

        viewModel.savaUserInfo(editor,passWord);//保存用户信息

        //商家状态（0：待审核，1：审核通过，2：未填写资料，3：审核不通过）
        switch (isExamine) {
            case 0:
                //跳转到待审核页面
                BufferCircleDialog.dialogcancel();

                startActivity(ExamineActivity.getStartIntent(LoginActivity.this));

                Log.d("跳转到待审核页面","待审核页面跳转");

                LoginActivity.this.finish();

                break;
            case 1:
                //跳转至主界面
                BufferCircleDialog.dialogcancel();

                isFirst=false;//标记为已登录状态

                startActivity(MainActivity.getStartIntent(LoginActivity.this));

                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();

                LoginActivity.this.finish();

                break;
            case 2:
                //跳转到入驻联盟页面
                BufferCircleDialog.dialogcancel();

                startActivity(JoinAllianceActivity.getStartIntent(LoginActivity.this));

                Log.d("入驻联盟","跳转到入驻联盟页面");

                break;
            case 3:
                //跳转到审核失败页面
                BufferCircleDialog.dialogcancel();

                Intent intent= ExamineActivity.getStartIntent(LoginActivity.this);

                //当审核失败时，将服务器返回数据传到申请联盟页面
                intent.putExtra("feedBack",viewModel.loginInfo.rows.feedBack);

                startActivity(intent);

                Log.d("审核失败","跳转到审核失败页面"+viewModel.loginInfo.rows.toString());

                LoginActivity.this.finish();

                break;
        }
    }

    //是否第一次登陆
    public void isFirst() {

        if (!isFirst){
                sharedPreferences=getSharedPreferences("user_login",MODE_PRIVATE);

                String imageUrl=sharedPreferences.getString("businessStoreImage",null);//头像图片地址

                String account=sharedPreferences.getString("lg_account",null);//用户名

                //将用户名显示在EditText上
                mBinding.etUserNameInput.setText(account);

                //加载图片
                Picasso.with(this)
                        .load(imageUrl)
                        .error(R.drawable.app_img)//当发生错误时占位图
                        .into(mBinding.civLoginUserIcon);
        }
    }

    //显示错误信息
    public void getErroInfo(String msg){
        Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
    }
}

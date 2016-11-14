package com.HyKj.UKeBao.view.activity.login;

import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivitySplashBinding;
import com.HyKj.UKeBao.model.login.SplashModel;
import com.HyKj.UKeBao.model.login.baen.SplashBean;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.NetWorkUtils;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.viewModel.login.SplashViewModel;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;


public class SplashActivity extends AppCompatActivity{
    //判断是否首次登陆
    public  Boolean isFirst = true;

    private SharedPreferences sharedPreferences;

    private String userName;

    private String userPassword;

    private String token;

    private int isExamine;

    public SplashViewModel viewModel;

    private ActivitySplashBinding mBinding;

    public SplashBean splashBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtil.d("11111111111_______11111111");

        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_splash);

        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY, "vmNlBqZY0MmPnIPMC4w1Aoof");

        viewModel=new SplashViewModel(new SplashModel(),this);

        mBinding.setViewModel(viewModel);

        jump();

        viewModel.getBackground();

        LogUtil.d("2222222222222_______22222");
    }

    //跳转界面判断
    private void jump() {
        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        splashBean=new SplashBean();

        splashBean.setFirst(sharedPreferences.getBoolean("isFirst", true));

        splashBean.setToken(sharedPreferences.getString("token", ""));

        splashBean.setUserName(sharedPreferences.getString("lg_account", ""));

        splashBean.setUserPassword(sharedPreferences.getString("lg_passwd", ""));

        splashBean.setIsExamine(sharedPreferences.getInt("isExamine", 0));

        splashBean.setContext(this);
    }

}

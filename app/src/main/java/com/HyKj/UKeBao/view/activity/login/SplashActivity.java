package com.HyKj.UKeBao.view.activity.login;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.NetWorkUtils;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;


public class SplashActivity extends AppCompatActivity {
    //判断是否首次登陆
    public static Boolean isFirst = true;

    private SharedPreferences sharedPreferences;

    private String userName;

    private String userPassword;

    private String token;

    private int isExamine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        setUpView();//初始化数据

        PushManager.startWork(getApplicationContext(),
                PushConstants.LOGIN_TYPE_API_KEY, "vmNlBqZY0MmPnIPMC4w1Aoof");
    }

    private void setUpView() {
        jump();//跳转界面判断
    }

    //跳转界面判断
    private void jump() {

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        isFirst = sharedPreferences.getBoolean("isFirst", true);

        token = sharedPreferences.getString("token", "");

        userName = sharedPreferences.getString("lg_account", "");

        userPassword = sharedPreferences.getString("lg_passwd", "");

        isExamine = sharedPreferences.getInt("isExamine", 0);
        if (isFirst) {
            //第一次登陆跳转到新手引导页
            startActivity(GuideActivity.getStartIntent(SplashActivity.this));
            SplashActivity.this.finish();
            overridePendingTransition(0,0);
        } else {
            //非第一次登陆，判断是否存在用户信息
            if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPassword) && !TextUtils.isEmpty(token)&&isExamine == 1) {
                //用户信息存在，判断是否有网络
                if (NetWorkUtils.networkStatusOK(this)) {
                    MyApplication.token=token;
                    startActivity(MainActivity.getStartIntent(SplashActivity.this));
                    SplashActivity.this.finish();
                } else {
                    startActivity(LoginActivity.getStartIntent(SplashActivity.this));

                    Toast.makeText(this,"登陆信息过期~请重新登陆!",Toast.LENGTH_SHORT).show();

                    SplashActivity.this.finish();
                }
            } else {
                startActivity(LoginActivity.getStartIntent(SplashActivity.this));
                SplashActivity.this.finish();
            }
        }
    }
}

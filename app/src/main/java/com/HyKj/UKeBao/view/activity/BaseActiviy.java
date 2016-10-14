package com.HyKj.UKeBao.view.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.baidu.android.pushservice.PushMessageReceiver;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/8/22.
 */
public abstract class BaseActiviy extends AppCompatActivity {
    private TextView tv_title;

    private ImageButton ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemBarUtil.initSystemBar(this);

        // 软键盘弹出问题
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        onCreateBinding();//初始化绑定视图

        setUpView();//初始化数据

        setListeners();//点击事件

    }


    public abstract void onCreateBinding();

    //初始化使用该方法
    public abstract void setUpView();

    public abstract void setListeners();

    public void toast(String msg) {}

    public void toast(String msg, Context context){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public void setTitleTheme(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title_bar_name);

        ib_back = (ImageButton) findViewById(R.id.imb_title_bar_back);

        //更改标题
        tv_title.setText(title);

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}


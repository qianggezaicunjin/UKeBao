package com.HyKj.UKeBao.view.activity.login.joinAlliance;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * Created by Administrator on 2016/8/23.
 */
public class JoinAllianceActivity extends BaseActiviy{


    private TextView tv_title;//标题

    private ImageButton bt_back;

    private Button bt_join;
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, JoinAllianceActivity.class);
        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_join_alliance);
        tv_title= (TextView) findViewById(R.id.tv_title_bar_name);
        bt_back = (ImageButton) findViewById(R.id.imb_title_bar_back);
        bt_join= (Button) findViewById(R.id.btn_shopsEntrance);
    }

    @Override
    public void setUpView() {
        //设置标题
        tv_title.setText("店铺入驻");
    }

    @Override
    public void setListeners() {
        //返回按钮监听
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //跳转
        bt_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SettledAllianceActivity.getStartIntent(JoinAllianceActivity.this));


                JoinAllianceActivity.this.finish();
            }
        });
    }
}

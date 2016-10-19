package com.HyKj.UKeBao.view.activity.MarketingManage;

import android.content.Context;
import android.content.Intent;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * Created by Administrator on 2016/10/19.
 */
public class RedPacketDetailActivity extends BaseActiviy{
    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,RedPacketDetailActivity.class);

        return intent;
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_redpacket_detail);
    }

    @Override
    public void setUpView() {
        setTitleTheme("红包详情");
    }

    @Override
    public void setListeners() {

    }
}

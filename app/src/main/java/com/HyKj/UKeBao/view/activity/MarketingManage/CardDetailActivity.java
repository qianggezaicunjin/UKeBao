package com.HyKj.UKeBao.view.activity.MarketingManage;

import android.content.Context;
import android.content.Intent;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * 卡劵详情
 * Created by Administrator on 2016/10/19.
 */
public class CardDetailActivity extends BaseActiviy{
    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,CardDetailActivity.class);

        return intent;
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_card_detail);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setListeners() {

    }
}

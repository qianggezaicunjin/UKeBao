package com.HyKj.UKeBao.view.activity.MarketingManage;

import android.content.Context;
import android.content.Intent;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 *  卡劵揽客
 * Created by Administrator on 2016/10/19.
 */
public class CardCustomerActivity extends BaseActiviy {

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,CardCustomerActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_card_customer);
    }

    @Override
    public void setUpView() {
        setTitleTheme("卡劵揽客");
    }

    @Override
    public void setListeners() {

    }
}

package com.HyKj.UKeBao.view.activity.login.examine;

import android.content.Context;
import android.content.Intent;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * 审核失败页面
 * Created by Administrator on 2016/8/25.
 */
public class ExamineFailActivity extends BaseActiviy{

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, ExamineFailActivity.class);

        return intent;
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.examine_fail);
    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setListeners() {

    }
}

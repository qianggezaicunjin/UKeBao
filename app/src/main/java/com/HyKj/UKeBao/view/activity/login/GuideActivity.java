package com.HyKj.UKeBao.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.ActivityManagerTools;
import com.HyKj.UKeBao.view.adapter.ViewPagerAdapter;


/**
 * 新手引导页
 * Created by Administrator on 2016/8/22.
 */
public class GuideActivity extends AppCompatActivity{

    private ViewPager mview_pager;

    private ViewPagerAdapter adapter;

    private int[] pic = new int[] { R.drawable.guide1, R.drawable.guide2,
            R.drawable.guide3,R.drawable.guide4};

    public static Intent getStartIntent(Context context){

        Intent intent=new Intent(context,GuideActivity.class);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guide);

        findViews();

        initData();

        setListeners();
    }

    public void findViews() {

        mview_pager = (ViewPager) findViewById(R.id.mview_pager);

    }

    public void initData() {

        ActivityManagerTools.getInstance().addActivity(this);

        adapter = new ViewPagerAdapter(this, pic);

        mview_pager.setAdapter(adapter);

    }

    public void setListeners() {

    }
}

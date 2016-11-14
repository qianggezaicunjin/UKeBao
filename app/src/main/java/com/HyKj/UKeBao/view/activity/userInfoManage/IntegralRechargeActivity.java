package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.userInfoManage.IntegralRechargeAdapter;
import com.HyKj.UKeBao.view.fragment.userInfoManage.BalanceRechargeFragment;
import com.HyKj.UKeBao.view.fragment.userInfoManage.CashChargeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/12.
 */
public class IntegralRechargeActivity extends BaseActiviy implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private Context mContext;
    /**
     * 标题名称
     */
    private TextView titleBarName;
    /**
     * 余额充值
     */
    private TextView balanceAddTitle;
    /**
     * 现金充值
     */
    private TextView cashAddTitle;

    private ImageButton backImageButton;

    private ViewPager mViewPager;

    private List<Fragment> fragmentList;

    private int currentItemIndex = 0;// 当前pager页

    private View scrollTabDivide;// 选择下滑线

    private int screenWidth;// 屏幕宽度

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,IntegralRechargeActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mContext=this;

        setContentView(R.layout.activity_integral_add);

        // 找到控件
        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        mViewPager = (ViewPager) findViewById(R.id.vp_integral_add);

        scrollTabDivide = findViewById(R.id.view_divide_select);

        balanceAddTitle = (TextView) findViewById(R.id.tv_balance_add_title);

        cashAddTitle = (TextView) findViewById(R.id.tv_cash_add_title);

        initData();
    }

    private void initData() {
        // 设置选择下滑线宽度，为屏幕宽度的一半
        Display display = getWindowManager().getDefaultDisplay();

        screenWidth = display.getWidth();

        scrollTabDivide.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT));

        // 初始化fragment集合
        fragmentList = new ArrayList<Fragment>();

        Fragment balanceAddFragment = new BalanceRechargeFragment();

        Fragment cashAddFragment = new CashChargeFragment();

        fragmentList.add(balanceAddFragment);

        fragmentList.add(cashAddFragment);
        // 设置数据适配器
        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new IntegralRechargeAdapter(fm, fragmentList));

        mViewPager.setCurrentItem(currentItemIndex);
    }

    @Override
    public void setUpView() {
        setTitleTheme("积分充值");
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(this);

        cashAddTitle.setOnClickListener(this);

        balanceAddTitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 返回
            case R.id.imb_title_bar_back:
                this.finish();
                break;

            // 切换到现金充值页面
            case R.id.tv_cash_add_title:
                mViewPager.setCurrentItem(1);
                break;
            // 切换到余额充值页面
            case R.id.tv_balance_add_title:
                mViewPager.setCurrentItem(0);
                break;

            default:
                break;
        }
    }
    @Override
    public void onPageSelected(int position) {
        TranslateAnimation translateAnim = null;
        switch (position) {
            case 0:
                // 下滑线动画，现金充值-->余额充值
                if (currentItemIndex == 1) {
                    translateAnim = new TranslateAnimation(screenWidth / 2, 0, 0, 0);
                }
                balanceAddTitle.setTextColor(mContext.getResources().getColor(R.color.status_color));

                cashAddTitle.setTextColor(mContext.getResources().getColor(R.color.light_grey));
                break;

            case 1:
                // 下滑线动画，余额充值-->现金充值
                if (currentItemIndex == 0) {
                    translateAnim = new TranslateAnimation(0, screenWidth / 2, 0, 0);
                }
                // 选中项标题颜色为蓝色，未选中项为灰色
                cashAddTitle.setTextColor(mContext.getResources().getColor(R.color.status_color));

                balanceAddTitle.setTextColor(mContext.getResources().getColor(R.color.light_grey));
                break;

            default:
                break;
        }
        currentItemIndex = position;

        translateAnim.setFillAfter(true);// True:图片停在动画结束位置

        translateAnim.setDuration(300);

        scrollTabDivide.startAnimation(translateAnim);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

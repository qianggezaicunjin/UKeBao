package com.HyKj.UKeBao.view.activity.login.examine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.examine.ExamineModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.SettledAllianceActivity;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;
import com.HyKj.UKeBao.viewModel.login.examine.ExamineViewModel;

/**
 * 审核中页面
 * Created by Administrator on 2016/8/25.
 */
public class ExamineActivity extends BaseActiviy implements View.OnClickListener{

    private final String TAG = "WaitReviewActivity";

    private Button btn_jumpLogin;

    private ImageView iv_reviewOne;

    private ImageView iv_reviewTwo;

    private ImageView iv_reviewThree;

    private RelativeLayout rl_appliance_servicePhone;

    public String service_tel;

    private SharedPreferences sharePre;

    private String feedBack;

    private RelativeLayout rl_change_businessInfo;

    private View view_reviewSuccess_first;

    private TextView tv_review_result;

    private TextView tv_review_tips;

    private ExamineViewModel viewModel;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, ExamineActivity.class);

        return intent;
    }
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_examine);

        // 找到控件
        btn_jumpLogin = (Button) findViewById(R.id.btn_jumpLogin);

        iv_reviewOne = (ImageView) findViewById(R.id.iv_reviewOne);

        iv_reviewTwo = (ImageView) findViewById(R.id.iv_reviewTwo);

        tv_review_result = (TextView) findViewById(R.id.tv_review_result);

        tv_review_tips = (TextView) findViewById(R.id.tv_review_tips);

        view_reviewSuccess_first = (View) findViewById(R.id.view_reviewSuccess_first);

        iv_reviewThree = (ImageView) findViewById(R.id.iv_reviewThree);

        rl_change_businessInfo = (RelativeLayout) findViewById(R.id.rl_change_businessInfo);

        rl_appliance_servicePhone = (RelativeLayout) findViewById(R.id.rl_appliance_servicePhone);

        viewModel=new ExamineViewModel(this,new ExamineModel());

        viewModel.getCostomerService();
    }

    @Override
    public void setUpView() {
        sharePre=getSharedPreferences("user_login", MODE_PRIVATE);

        feedBack=getIntent().getStringExtra("feedBack");



        if(feedBack!=null){
            LogUtil.d("审核失败,失败理由："+feedBack);

            rl_change_businessInfo.setVisibility(View.VISIBLE);

            iv_reviewTwo.setImageResource(R.drawable.two_red);

            tv_review_result.setText("审核未成功");

            tv_review_result.setTextColor(getResources().getColor(R.color.text_drak_red));

            tv_review_tips.setText("对不起，您所提交的申请材料不完整或信息有误，请重新提交");

            tv_review_tips.setTextColor(getResources().getColor(R.color.text_drak_red));

            view_reviewSuccess_first.setBackgroundColor(getResources().getColor(R.color.text_drak_red));
        }
    }

    @Override
    public void setListeners() {
        btn_jumpLogin.setOnClickListener(this);

        rl_appliance_servicePhone.setOnClickListener(this);

        rl_change_businessInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //注销登陆
            case R.id.btn_jumpLogin:
                btn_jumpLogin.setEnabled(false);

                viewModel.loginOut();

                break;
            case R.id.rl_change_businessInfo:
                Intent intent = SettledAllianceActivity.getStartIntent(this);

                intent.putExtra("feedBack", feedBack);

                startActivity(intent);

                setResult(RESULT_OK);

                finish();
                break;
            case R.id.rl_appliance_servicePhone:
                if(service_tel!=null){
                    openDialog();
                }
                break;
            default:
                break;
        }
    }
    /**
     * 拨打热线弹窗
     */
    private void openDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle(service_tel);

        builder.setCancelable(true);

        builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent("android.intent.action.CALL", Uri.parse("tel:"+service_tel));

                startActivity(intent);

                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.show();
    }
    //注销token并跳转界面
    public void loginOut() {
        sharePre.edit().putString("lg_passwd", "").commit();

        sharePre.edit().putString("token", "").commit();

        startActivity(LoginActivity.getStartIntent(this));

        setResult(RESULT_OK);

        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            System.exit(0);

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    //当注销失败时，推出登录按钮恢复
    public void loginoutFail() {
        toast("注销失败~请重试",this);

        btn_jumpLogin.setEnabled(true);
    }
}

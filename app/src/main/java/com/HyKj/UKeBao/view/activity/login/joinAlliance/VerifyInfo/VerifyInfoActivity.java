package com.HyKj.UKeBao.view.activity.login.joinAlliance.VerifyInfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.joinAlliance.VerifyInfo.VerifyInfoModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.examine.ExamineActivity;
import com.HyKj.UKeBao.viewModel.login.joinAlliance.VerifyInfoViewModel;

/** 核对信息
 * Created by Administrator on 2016/9/14.
 */

public class VerifyInfoActivity extends BaseActiviy implements View.OnClickListener {
    private TextView tv_agreement;//商家协议

    private TextView tv_businessName;//商家名称

    private TextView tv_categoryName;//行业类型

    private TextView tv_categoryParentName;

    private TextView tv_discountNumber;//折扣

    private TextView tv_discountIntegral;//积分赠送

    private Button btn_commitApply;

    private CheckBox checkBox_agree;

    private BusinessInfo businessInfo;

    private VerifyInfoViewModel viewModel;

    private String feedBack;//反馈

    private SharedPreferences sp;

    private String businessStoreId;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, VerifyInfoActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_verifyinfo);

        tv_agreement= (TextView) findViewById(R.id.tv_agreement);

        tv_businessName= (TextView) findViewById(R.id.tv_businessName);

        tv_categoryName= (TextView) findViewById(R.id.tv_categoryName);

        tv_categoryParentName= (TextView) findViewById(R.id.tv_categoryParentName);

        tv_discountNumber= (TextView) findViewById(R.id.tv_discountNumber);

        tv_discountIntegral= (TextView) findViewById(R.id.tv_discountIntegral);

        btn_commitApply= (Button) findViewById(R.id.btn_commitApply);

        checkBox_agree= (CheckBox) findViewById(R.id.checkBox_agree);

        // 获取信息
        Bundle extras = getIntent().getExtras();

        businessInfo = (BusinessInfo) extras.getSerializable("businessInfo");

        feedBack=extras.getString("feedBack", "");

        sp = getSharedPreferences("user_login", MODE_PRIVATE);

        businessStoreId = sp.getString("businessStoreId", "");

        viewModel=new VerifyInfoViewModel(new VerifyInfoModel(),this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("提交申请");

        tv_businessName.setText(businessInfo.businessName);//设置商家名城

        tv_categoryName.setText(businessInfo.stype);//设置行业类型

        tv_categoryParentName.setText(businessInfo.ptype);

        tv_discountNumber.setText(businessInfo.businessDiscount+"折");//折扣

        tv_discountIntegral.setText("(消费100赠送"+businessInfo.integral+"积分)");//赠送积分
    }

    @Override
    public void setListeners() {
        tv_agreement.setOnClickListener(this);

        btn_commitApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //商家协议
            case R.id.tv_agreement:
                LogUtil.d("点击了商家协议TextView");
                startActivity(AgreementActivity.getStartIntent(this));
                break;
            //提交申请
            case R.id.btn_commitApply:
                BufferCircleDialog.show(VerifyInfoActivity.this,"正在提交中...",false,null);

                viewModel.commit(businessInfo,checkBox_agree,feedBack,businessStoreId);
                break;
            default:
                break;
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    //跳转到待审核界面
    public void jump() {
        BufferCircleDialog.dialogcancel();

        finish();

        setResult(RESULT_OK);

        startActivity(ExamineActivity.getStartIntent(this));
    }
}

package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.userInfoManage.WithdrawalsModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.HttpHeadUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.businessManage.BaseWebViewActivity;
import com.HyKj.UKeBao.viewModel.userInfoManage.WithdrawalsViewModel;

/**
 * 申请提现
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsActivity extends BaseActiviy implements View.OnClickListener{
    /**
     * 标题名称
     */
    private TextView titleBarName;
    /**
     * 返回按钮
     */
    private ImageButton backImageButton;
    /**
     * 申请提现
     */
    private Button applyCashButton;
    /**
     * 商户名称
     */
    private TextView businesName;
    /**
     * 账户余额
     */
    private TextView accountBalance;
    /**
     * 可提金额
     */
    private TextView availableAccountBalance;
    /**
     * 提现记录
     * */
    private Button btn_applyCash_record;

    private EditText et_apply_cash_limits;//提现金额

    private String businessStoreName;

    private String all_money;//全部金额

    private double cash;//可提金额

    private WithdrawalsViewModel viewModel;

    private SharedPreferences sp;
    private String businessStoreId;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, WithdrawalsActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {

        setContentView(R.layout.activity_apply_cash);

        titleBarName = (TextView) findViewById(R.id.tv_applyCash);

        backImageButton = (ImageButton) findViewById(R.id.imb_applyCash);

        btn_applyCash_record = (Button) findViewById(R.id.btn_applyCash_record);

        businesName = (TextView) findViewById(R.id.tv_business_name_detail);

        accountBalance = (TextView) findViewById(R.id.et_account_balance_detail);

        availableAccountBalance = (TextView) findViewById(R.id.et_available_account_balance_detail);

        applyCashButton = (Button) findViewById(R.id.btn_confirm_apply_cash);

        et_apply_cash_limits = (EditText) findViewById(R.id.et_apply_cash_limits);

        initData();
    }

    private void initData() {
        sp=getSharedPreferences("user_login", MODE_PRIVATE);

        businessStoreName = getIntent().getStringExtra("businessStoreName");

        all_money = getIntent().getStringExtra("all_money");

        cash = getIntent().getDoubleExtra("cash",0.0);

        viewModel=new WithdrawalsViewModel(this, new WithdrawalsModel());
    }

    @Override
    public void setUpView() {
        titleBarName.setText("申请提现");

        businesName.setText(businessStoreName);

        accountBalance.setText(all_money);

        availableAccountBalance.setText(String.valueOf(cash));
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);

        btn_applyCash_record.setOnClickListener(this);

        applyCashButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //退出
            case R.id.imb_applyCash:
                finish();

                break;
            //提现记录
            case R.id.btn_applyCash_record:
                startActivity(WithdrawalsRecordActivity.getStartIntent(this));

                break;
            //申请提现
            case R.id.btn_confirm_apply_cash:
                businessStoreId = sp.getString("businessStoreId", "");

                String demand_amount=et_apply_cash_limits.getText().toString();//需要金额

                if(!(TextUtils.isEmpty(demand_amount)||Integer.valueOf(demand_amount)<5)) {
                    BufferCircleDialog.show(this, "正在提交中，请稍候~", false, null);

                    viewModel.withdrawals(businessStoreId, demand_amount);
                }else {
                    toast("请输入正确的提现金额~",this);
                }

                break;
        }
    }
}

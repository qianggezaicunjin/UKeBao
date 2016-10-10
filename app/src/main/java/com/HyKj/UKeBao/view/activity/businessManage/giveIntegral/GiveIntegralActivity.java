package com.HyKj.UKeBao.view.activity.businessManage.giveIntegral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.giveIntegral.GiveIntegralModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.CashierInputFilter;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.businessManage.giveIntegral.GiveIntegralViewModel;

import java.math.BigDecimal;

/**
 * 赠送积分页面
 * Created by Administrator on 2016/9/24.
 */
public class GiveIntegralActivity extends BaseActiviy implements View.OnClickListener {

    //商品名称
    private String businessName;
    //用户电话
    private String phone;
    //用户名
    private String userName;

    private String integralScale;
    //现金
    private double cash;

    private double integral;

    private SharedPreferences sharedPreferences;
    /**
     * 返回按钮
     */
    private ImageButton backImageButton;
    /**
     * 标题
     */
    private TextView titleBarName;
    /**
     * 赠送记录
     */
    private Button presentIntegralRecode;
    /**
     * 会员账号
     */
    private EditText memberNumber;
    /**
     * 消费金额
     */
    private EditText comsumeMoney;
    /**
     * 会员昵称
     */
    private TextView memberNickname;
    /**
     * 赠送积分（数量）
     */
    private TextView presentIntegralNumber;
    /**
     * 赠送积分（确定按钮）
     */
    private TextView presentIntegralButton;

    private final String ACTION_NAME = "successBack";

    private GiveIntegralViewModel viewModel;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, GiveIntegralActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_give_integral);

        backImageButton = (ImageButton) findViewById(R.id.imb_presentIntegral_back);

        titleBarName = (TextView) findViewById(R.id.tv_presentIntegral_titleName);

        presentIntegralRecode = (Button) findViewById(R.id.btn_present_integral_record);

        memberNumber = (EditText) findViewById(R.id.et_member_number_input);

        memberNickname = (TextView) findViewById(R.id.tv_member_nickname);

        comsumeMoney = (EditText) findViewById(R.id.et_comsume_money_input);

        presentIntegralNumber = (TextView) findViewById(R.id.tv_present_integral_number);

        presentIntegralButton = (Button) findViewById(R.id.btn_present_integral);

        viewModel = new GiveIntegralViewModel(new GiveIntegralModel(), this);
    }

    @Override
    public void setUpView() {

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        integralScale = sharedPreferences.getString("integralScale", "");

        businessName = sharedPreferences.getString("businessName", "");

        //限制编辑框只能输入金钱格式
        InputFilter[] filters = {new CashierInputFilter()};

        comsumeMoney.setFilters(filters);
    }

    @Override
    public void setListeners() {
        presentIntegralRecode.setOnClickListener(this);

        backImageButton.setOnClickListener(this);

        presentIntegralButton.setOnClickListener(this);

        comsumeMoney.addTextChangedListener(new myWatchListener());

        memberNumber.addTextChangedListener(watcher);
    }

    //电话号码
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            phone = memberNumber.getText().toString();
            if (phone.length() >= 7) {
                viewModel.getUserName(phone);
            }
        }
    };

    //金额
    class myWatchListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String value = comsumeMoney.getText().toString();
            if (!TextUtils.isEmpty(value)) {
                cash = Double.parseDouble(value);
                LogUtil.d("积分转换："+cash+"比例："+integralScale);
                Double res = Double.parseDouble(integralScale);
                LogUtil.d("积分转换2："+res);

                BigDecimal a = new BigDecimal((cash * res) / 100);
                integral = a.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                presentIntegralNumber.setText(integral + "");
            } else {
                presentIntegralNumber.setText("0");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回
            case R.id.imb_presentIntegral_back:
                this.finish();
                break;
            // 赠送记录
            case R.id.btn_present_integral_record:
                openPresentIntegralRecode();
                break;
            case R.id.btn_present_integral:
                //赠送积分
                String money = comsumeMoney.getText().toString();

                phone = memberNumber.getText().toString();

                viewModel.sendIntegral(money, phone, cash, integral, businessName);

                break;
        }
    }

    //赠送记录
    private void openPresentIntegralRecode() {
        Intent intent=IntegralRecordActivity.getStartIntent(this);
        //筛选赠送记录
        intent.putExtra("isSend", "true");
        intent.putExtra("type", "false");
        startActivity(intent);
    }

    //设置用户名
    public void setUserName(String userName) {
        memberNickname.setText(userName);
    }

    public void sendIntegral() {
        //充值成功后，发送广播
        Intent mIntent = new Intent(ACTION_NAME);
        //发送广播
        sendBroadcast(mIntent);

        memberNumber.setText("");

        comsumeMoney.setText("");

        memberNickname.setText("");

        BufferCircleDialog.dialogcancel();
    }

}



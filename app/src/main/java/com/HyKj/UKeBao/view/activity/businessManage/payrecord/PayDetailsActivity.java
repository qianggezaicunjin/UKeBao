package com.HyKj.UKeBao.view.activity.businessManage.payrecord;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityPaydetailBinding;
import com.HyKj.UKeBao.model.businessManage.bean.OrderRecord;
import com.HyKj.UKeBao.model.businessManage.payrecord.PayDetailsModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.businessManage.payrecord.PayRecordDetailViewModel;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/10/6.
 */
public class PayDetailsActivity extends BaseActiviy {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PayDetailsActivity.class);

        return intent;
    }

    private ActivityPaydetailBinding mBinding;

    public OrderRecord orderRecord;

    private SharedPreferences sharedPreferences;

    //平台现金充值积分比例
    private double recharge;

    /**
     * 确认退款Dialog
     */
    private  AlertDialog refundDialog;
    /**
     * 取消（退款）
     */
    private Button cancelRefundButton;
    /**
     * 确认退款
     */
    private Button confirmRefundButton;
    /**
     * 密码
     * */
    private EditText inputSercet;

    private PayRecordDetailViewModel viewModel;

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_paydetail);

        mBinding.setView(this);

        orderRecord = (OrderRecord) getIntent().getSerializableExtra("orderRecord");

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        String recharges = sharedPreferences.getString("recharge", "");

        recharge = Double.valueOf(recharges);

        viewModel = new PayRecordDetailViewModel(this, new PayDetailsModel());
    }

    @Override
    public void setUpView() {
        mBinding.tvPayDetailName.setText("买单详情");

        DecimalFormat df = new DecimalFormat("#0.00");
        //添加金额或者删除金额
        getAddAmount(df);
        //折扣
        getDiscount();
        //不享受优惠金额
        getNodiscount();
        //买单金额
        mBinding.tvPayMoney.setText(df.format(Double.valueOf(orderRecord.getIntegral()) / recharge) + "元");
        //实收金额
        mBinding.tvReallySettleMoney.setText(df.format(Double.valueOf(orderRecord.getRealPrice())) + "元");
    }

    @Override
    public void setListeners() {
        //设置退出键监听
        mBinding.imbPayDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //是否显示退款按钮
        if (!viewModel.isDisplayRefund(orderRecord)) {
            mBinding.btnRefundMoney.setVisibility(View.GONE);
        }

        //设置退款按钮监听
        mBinding.btnRefundMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出退款对话框
                openRefundWindows();
            }
        });
    }

    private void openRefundWindows() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogContentView = View.inflate(this, R.layout.dialog_confirm_refund, null);

        confirmRefundButton = (Button) dialogContentView.findViewById(R.id.btn_confirm_refund);

        cancelRefundButton = (Button) dialogContentView.findViewById(R.id.btn_cancel_refund);

        inputSercet = (EditText) dialogContentView.findViewById(R.id.et_password_input);

        //确认退款按钮监听
        confirmRefundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSercet.setFocusable(true);

                viewModel.refund(inputSercet.getText().toString(),orderRecord.getId());
            }
        });

        //取消对话框按钮监听
        cancelRefundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refundDialog.dismiss();
            }
        });

        refundDialog = builder.create();

        refundDialog.setView(((Activity)this).getLayoutInflater().inflate(R.layout.dialog_confirm_refund, null));

        refundDialog.setCanceledOnTouchOutside(false);

        refundDialog.show();
        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = refundDialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.4); // 高度设置为屏幕的0.4

        lp.width = (int) (display.getWidth() * 0.8); // 宽度设置为屏幕的0.8

        dialogWindow.setAttributes(lp);
    }

    private void getNodiscount() {
        if (orderRecord.getFreeServiceQuota() != 0) {
            mBinding.tvPayNodiscount.setText(orderRecord.getFreeServiceQuota() + "元(不参与优惠)");
        } else {
            mBinding.llPayNodiscount.setVisibility(View.GONE);
        }
    }

    private void getAddAmount(DecimalFormat df) {
        String status = orderRecord.getStatus();
        if (status != null) {
            if (status.equals("1")) {
                mBinding.tvAddAmount.setText("+" + df.format(Double.valueOf(orderRecord.getRealPrice())));
            } else if (status.equals("5")) {
                mBinding.tvAddAmount.setText("-" + df.format(Double.valueOf(orderRecord.getRealPrice())));
            }
        }
    }


    //获取折扣
    public void getDiscount() {
        int menberCou = orderRecord.getMenberCouponId();

        LogUtil.d("卡劵优惠id"+menberCou);

        if (menberCou != 0) {
            //有卡券优惠
            double couponQuo = orderRecord.getCouponQuota();

            double decuction = orderRecord.getDeduction();

            mBinding.tvIntegralDiscountTitle.setText("卡券优惠");

            mBinding.tvIntegralDiscountAmount.setText("-" + couponQuo + "元" + "（满" + decuction + "元减" + couponQuo + "元）");

            mBinding.tvServiceMoneyDetail.setText("服务费:(实付金额-免服务费金额)*1%");

            mBinding.llRealMoney.setVisibility(View.VISIBLE);
        } else {
            //会员折扣
            mBinding.tvIntegralDiscountTitle.setText("会员折扣");

            mBinding.tvIntegralDiscountAmount.setText(orderRecord.getDiscount() + "折");

        }
    }
    //退款成功后页面执行的操作
    public void refundSuccess() {
        refundDialog.dismiss();

        mBinding.btnRefundMoney.setVisibility(View.GONE);
    }
}

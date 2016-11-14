package com.HyKj.UKeBao.view.activity.businessManage.financialManagement;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.financial.FinancialManagementModel;
import com.HyKj.UKeBao.model.businessManage.bean.FinancialInfo;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.MyDatePickerDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.businessManage.giveIntegral.IntegralRecordActivity;
import com.HyKj.UKeBao.viewModel.businessManage.financialManagement.FinancialManagementViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 财务管理
 * Created by Administrator on 2016/9/27.
 */
public class FinancialManagementActivity extends BaseActiviy implements
        View.OnClickListener, DatePickerDialog.OnDateSetListener {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, FinancialManagementActivity.class);

        return intent;
    }

    private FinancialManagementViewModel viewModel;

    private final String TAG = "FinanceManagerActivity";
    /**
     * 标题
     */
    private TextView titleBarName;
    /**
     * 积分账户
     */
    private LinearLayout ll_integral_financeManager;
    /**
     * 现金账户
     */
    private LinearLayout ll_cash_financeManager;
    /**
     * 开始时间
     */
    private TextView tv_start_time;
    /**
     * 结束时间
     */
    private TextView tv_end_time;
    /**
     * 成交金额
     */
    private TextView turnoverNumber;
    /**
     * 折后实收
     */
    private TextView discountIncomeNumber;
    /**
     * 成交总额 计算规则
     */
    private TextView turnoverAmountRule;
    /**
     * 实收净额 计算规则
     */
    private TextView actualIncomeRule;
    /**
     * 订单金额
     */
    private TextView orderMoney;
    /**
     * 订单笔数
     */
    private TextView orderAmount;
    /**
     * 订单金额 计算规则
     */
    private TextView orderMoneyRule;
    /**
     * 退款笔数
     */
    private TextView refundMoneyAmount;
    /**
     * 退款金额
     */
    private TextView refundMoneyNumber;

    private RelativeLayout rl_real_income;

    private TextView tv_integral_finance_number;

    private TextView tv_cash_account_number;

    private int timePickFlag = -1;

    private int startTimePick = 0;

    private int endTimePick = 1;

    private String startDate, stopDate;// 检索的开始时间，结束时间

    private int currentDay, currentYear, currentMonth;

    private int startYear, startMonth, startDay;

    private int endYear, endMonth, endDay;

    private int pickCount = 0;

    private DatePickerDialog dataPickerDialog;

    private boolean start, end;

    private TextView orderTotalRule;

    private TextView orderCountRule;

    private TextView tv_turnoverSuccess_count;// 成交笔数

    private String startTimesrc;

    private String endTimesrc;

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_financial_management);

        viewModel = new FinancialManagementViewModel(new FinancialManagementModel(), this);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        ll_integral_financeManager = (LinearLayout) findViewById(R.id.ll_integral_financeManager);

        ll_cash_financeManager = (LinearLayout) findViewById(R.id.ll_cash_financeManager);

        rl_real_income= (RelativeLayout) findViewById(R.id.rl_real_income_detail);

        tv_integral_finance_number = (TextView) findViewById(R.id.tv_integral_finance_number);

        tv_cash_account_number = (TextView) findViewById(R.id.tv_cash_account_number);

        tv_start_time = (TextView) findViewById(R.id.tv_start_time);

        tv_end_time = (TextView) findViewById(R.id.tv_end_time);

        turnoverNumber = (TextView) findViewById(R.id.tv_turnover_financeNumuber);

        discountIncomeNumber = (TextView) findViewById(R.id.tv_discount_financeNumuber);

        turnoverAmountRule = (TextView) findViewById(R.id.tv_calculate_rule_finance);

        actualIncomeRule = (TextView) findViewById(R.id.tv_calculate_rule_financeNumber);

        orderMoney = (TextView) findViewById(R.id.tv_order_financeNumber);

        orderAmount = (TextView) findViewById(R.id.tv_order_financeAmount);

        refundMoneyAmount = (TextView) findViewById(R.id.tv_refund_money_financeAmount);

        refundMoneyNumber = (TextView) findViewById(R.id.tv_refund_money_financeNumber);

        orderTotalRule = (TextView) findViewById(R.id.tv_orderTotal_rule_finance);

        orderCountRule = (TextView) findViewById(R.id.tv_orderCount_rule_finance);

        tv_turnoverSuccess_count = (TextView) findViewById(R.id.tv_turnoverSuccess_count);
    }

    @Override
    public void setUpView() {
        setTitleTheme("财务管理");

        turnoverAmountRule.setText("成交金额:订单总金额，不包含退款、交易关闭及待支付订单");

        actualIncomeRule.setText("折后实收:折扣实结+免服务费金额");

        orderTotalRule.setText("订单金额:所有支付成功的订单总额（包含已退款订单）");

        orderCountRule.setText("订单笔数:支付成功订单统计（包含已退款订单）");

        Calendar calendar=Calendar.getInstance();

        //获取当前年份
        int year=calendar.get(Calendar.YEAR);

        int month=calendar.get(Calendar.MONTH)+1;

        int day=calendar.get(Calendar.DAY_OF_MONTH);

        tv_start_time.setText(year+"-"+month+"-"+"01");

        tv_end_time.setText(year+"-"+month+"-"+day);

        LogUtil.d("startDate:" + startDate + "stopDate" + stopDate);

        BufferCircleDialog.show(this, "努力加载中..", true, null);

        viewModel.getFinancialData(startDate, stopDate);//获取全部数据
    }

    @Override
    public void setListeners() {
        ll_integral_financeManager.setOnClickListener(this);

        ll_cash_financeManager.setOnClickListener(this);

        tv_start_time.setOnClickListener(this);

        tv_end_time.setOnClickListener(this);

        rl_real_income.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 积分账户
            case R.id.ll_integral_financeManager:
                startActivity(IntegralRecordActivity.getStartIntent(this));
                break;
            // 现金账户
            case R.id.ll_cash_financeManager:
                openCashRecode();
                break;
            // 开启时间
            case R.id.tv_start_time:
                openDatePicker(startTimePick);
                break;
            // 结束时间
            case R.id.tv_end_time:
                openDatePicker(endTimePick);
                break;
            //实收详情
            case R.id.rl_real_income_detail:
                Intent intent=RealIncomeDetailActivity.getStartIntent(this);

                intent.putExtra("startTime",tv_start_time.getText().toString().trim());

                intent.putExtra("endTime",tv_end_time.getText().toString().trim());

                startActivity(intent);

                break;
        }
    }

    //跳转到现金账户详情
    private void openCashRecode() {
        Intent cash_intent=IntegralRecordActivity.getStartIntent(this);

        cash_intent.putExtra("isCashRecord",true);

        startActivity(cash_intent);
    }

    /**
     * 日期选择器
     */
    private void openDatePicker(int flag) {
        timePickFlag = flag;

        Calendar d = Calendar.getInstance(Locale.CHINA);

        // 创建一个日历引用d，通过静态方法getInstance() 从指定时区 Locale.CHINA 获得一个日期实例
        Date myDate = new Date();

        // 创建一个Date实例
        d.setTime(myDate);

        // 设置日历的时间，把一个新建Date实例myDate传入
        currentYear = d.get(Calendar.YEAR);

        currentMonth = d.get(Calendar.MONTH);

        currentDay = d.get(Calendar.DAY_OF_MONTH);

        // 获得日历中的 year month day
        dataPickerDialog = new MyDatePickerDialog(this, this, currentYear, currentMonth, currentDay);

        dataPickerDialog.show();

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        pickCount++;
        if (timePickFlag == 0) {
            // 开始时间
            startYear = year;

            startMonth = monthOfYear;

            startDay = dayOfMonth;

            //判断是否在月份前加0
            if (monthOfYear + 1 > 9) {
                if (dayOfMonth > 9) {
                    tv_start_time.setText(year + "-" + (monthOfYear + 1) + "-"
                            + dayOfMonth);
                    startTimesrc = year + "" + (monthOfYear + 1) + ""
                            + dayOfMonth + "";
                } else {
                    tv_start_time.setText(year + "-" + (monthOfYear + 1) + "-"
                            + "0" + dayOfMonth);
                    startTimesrc = year + "" + (monthOfYear + 1) + "0"
                            + dayOfMonth + "";
                }
            } else {
                if (dayOfMonth > 9) {
                    tv_start_time.setText(year + "-" + "0" + (monthOfYear + 1)
                            + "-" + dayOfMonth);
                    startTimesrc = year + "0" + (monthOfYear + 1) + dayOfMonth
                            + "";
                } else {
                    tv_start_time.setText(year + "-" + "0" + (monthOfYear + 1)
                            + "-" + "0" + dayOfMonth);
                    startTimesrc = year + "0" + (monthOfYear + 1) + "0"
                            + dayOfMonth + "";
                }
            }
            tv_start_time.setTextColor(Color.BLACK);//确定后将字体设置为黑色
        }
        if (timePickFlag == 1) {
            // 结束时间
            endYear = year;
            endMonth = monthOfYear;
            endDay = dayOfMonth;
            if (monthOfYear + 1 > 9) {
                if (dayOfMonth > 9) {
                    tv_end_time.setText(year + "-" + (monthOfYear + 1) + "-"
                            + dayOfMonth);
                    endTimesrc = year + "" + (monthOfYear + 1) + ""
                            + dayOfMonth + "";
                } else {
                    tv_end_time.setText(year + "-" + (monthOfYear + 1) + "-"
                            + "0" + dayOfMonth);
                    endTimesrc = year + "" + (monthOfYear + 1) + "" + "0"
                            + dayOfMonth + "";
                }
            } else {
                if (dayOfMonth > 9) {
                    tv_end_time.setText(year + "-" + "0" + (monthOfYear + 1)
                            + "-" + dayOfMonth);
                    endTimesrc = year + "0" + "" + (monthOfYear + 1)
                            + dayOfMonth + "";
                } else {
                    tv_end_time.setText(year + "-" + "0" + (monthOfYear + 1)
                            + "-" + "0" + dayOfMonth);
                    endTimesrc = year + "0" + (monthOfYear + 1) + "0"
                            + dayOfMonth + "";
                }
            }
            tv_end_time.setTextColor(Color.BLACK);//确定后将字体设置为黑色
        }
        startDate = tv_start_time.getText().toString().trim();
        stopDate = tv_end_time.getText().toString().trim();
        if (pickCount > 1) {
            if (startDate.equals("开始时间") || startDate.equals("")) {
                Toast.makeText(this, "请选择开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
            if (stopDate.equals("结束时间") || stopDate.equals("")) {
                Toast.makeText(this, "请选择结束时间", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if (startTimesrc != null && endTimesrc != null) {
            if (Integer.valueOf(startTimesrc) <= Integer.valueOf(endTimesrc)) {
                BufferCircleDialog.show(this, "努力加载中..", true, null);

                viewModel.getFinancialData(startDate, stopDate);
            } else {
                toast("开始时间不能大于结束时间", this);
            }
        }

    }

    //将获取的财务数据填充到界面上
    public void setData(FinancialInfo info) {
        tv_integral_finance_number.setText(info.integral + "积分");

        tv_cash_account_number.setText(info.cash + "元");

        turnoverNumber.setText(info.turnover + "元");

        discountIncomeNumber.setText(info.discountPaid + "元");

        refundMoneyAmount.setText(info.refundCount + "笔");

        refundMoneyNumber.setText(info.refundmount + "元");

        orderAmount.setText(info.OrderCount + "笔");

        tv_turnoverSuccess_count.setText(info.bargainCount + "笔");

        orderMoney.setText(info.OrderAmount + "元");

        BufferCircleDialog.dialogcancel();
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }
}

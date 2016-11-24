package com.HyKj.UKeBao.view.fragment.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.userInfoManage.BalanceModel;
import com.HyKj.UKeBao.model.userInfoManage.LeftMenuFragmentModel;
import com.HyKj.UKeBao.view.customView.BufferCircleDialog;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.viewModel.userInfoManage.BalanceViewModel;
import com.HyKj.UKeBao.viewModel.userInfoManage.LeftMenuFragmentViewModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 余额充值
 * Created by Administrator on 2016/11/12.
 */
public class BalanceRechargeFragment extends BaseFragment implements View.OnClickListener,
        TextWatcher {
    private View contentView;

    private Context mContext;
    /**
     * 充值金额
     */
    private EditText addMoneyNumber;
    /**
     * 需支付
     */
    private TextView needPayNumber;
    /**
     * 现金账户
     */
    private TextView cashAccountNumber;

    /**
     * 可用余额
     */
    private TextView availableBalanceNumber;
    /**
     * 确认充值
     */
    private Button confirmAddButton;

    private SharedPreferences sharePre;

    private int id;// 商家id

    private BusinessInfo businessInfo;

    private double recharge;

    private double balanceCash;

    private double needPay;

    private DecimalFormat df = new DecimalFormat("0.00");

    private final String ACTION_NAME = "successBack";

    private double addIntegral;// 充值积分值

    private BalanceViewModel viewModel;
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();

        contentView = inflater.inflate(R.layout.fragment_balance_add, container, false);

        return contentView;
    }

    @Override
    public void findViews() {
        addMoneyNumber = (EditText) contentView.findViewById(R.id.et_add_money_input);

        needPayNumber = (TextView) contentView.findViewById(R.id.tv_need_pay_number);

        availableBalanceNumber = (TextView) contentView.findViewById(R.id.tv_available_balance_number);

        cashAccountNumber = (TextView) contentView.findViewById(R.id.tv_cash_account_number);

        confirmAddButton = (Button) contentView.findViewById(R.id.btn_confirm_add);

        viewModel=new BalanceViewModel(this,new BalanceModel());
    }

    @Override
    public void initData() {
        sharePre = getActivity().getSharedPreferences("user_login", mContext.MODE_PRIVATE);

        String recharges = sharePre.getString("recharge", "");

        if (!TextUtils.isEmpty(recharges)) {
            recharge = Double.parseDouble(recharges);
        }

        BufferCircleDialog.show(mContext, "正在加载数据", true, null);

        viewModel.getBusinessInfo();
    }

    @Override
    public void setListeners() {
        confirmAddButton.setOnClickListener(this);

        addMoneyNumber.addTextChangedListener(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 确认充值
            case R.id.btn_confirm_add:

                confirmAdd();

                break;

            default:
                break;
        }
    }

    //确认充值
    private void confirmAdd() {
        String integral = addMoneyNumber.getText().toString().trim();

        String cash = needPayNumber.getText().toString().trim();

        if (addIntegral < 0.1) {
            toast("充值积分不能少于0.1",getActivity());

            return;
        }
        if (TextUtils.isEmpty(integral)) {
            Toast.makeText(mContext, "请输入充值积分", Toast.LENGTH_SHORT).show();

            return;
        }
        confirmAddButton.setEnabled(false);

        BufferCircleDialog.show(mContext, "正在充值...", true, null);

        viewModel.confirmRecharge(integral,cash);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String addAccount = addMoneyNumber.getText().toString().trim();
        if (!TextUtils.isEmpty(addAccount) && !addAccount.equals(".")) {
            addIntegral = Double.parseDouble(addAccount);
            if (addIntegral < 0.1) {
                needPayNumber.setText("");
            } else {
                needPay = addIntegral / recharge;
                String pay = df.format(needPay);
                needPayNumber.setText(pay);
            }
        } else {
            needPayNumber.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // 限制一位小数
        String temp = editable.toString();
        int posDot = temp.indexOf(".");

        if (posDot <= 0)
            return;

        if (temp.length() - posDot - 1 > 1) {
            editable.delete(posDot + 2, posDot + 3);
        }
    }

    //设置金额
    public void setCashData(BusinessInfo info){
        businessInfo=info;

        balanceCash=info.cash;

        double cash=BigDecimal.valueOf(balanceCash).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();//可用余额保留两位小数

        double cashAccount=BigDecimal.valueOf(info.cash + info.freezeCash).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();//现金账户保留两位小数

        availableBalanceNumber.setText(cash + "元");//可用余额

        cashAccountNumber.setText(cashAccount + "元");//现金账户

        BufferCircleDialog.dialogcancel();
    }

    //确认充值回调
    public void confirmRecharge(String msg) {
        toast(msg,getActivity());

        balanceCash = balanceCash - needPay;

        String balance = df.format(balanceCash);

        availableBalanceNumber.setText(balance + "元");

        addMoneyNumber.setText("");

        needPayNumber.setText("");

        cashAccountNumber.setText(balanceCash+ businessInfo.freezeCash + "元");

        // 充值成功后，发送广播
        Intent mIntent = new Intent(ACTION_NAME);
        // 发送广播
        getActivity().sendBroadcast(mIntent);

        BufferCircleDialog.dialogcancel();

        confirmAddButton.setEnabled(true);

        BufferCircleDialog.dialogcancel();
    }
}

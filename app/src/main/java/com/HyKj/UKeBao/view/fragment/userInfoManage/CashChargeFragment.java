package com.HyKj.UKeBao.view.fragment.userInfoManage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.model.userInfoManage.CashChargeModel;
import com.HyKj.UKeBao.util.AliPayResult;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.customView.BufferCircleDialog;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.viewModel.userInfoManage.CashChargeViewModel;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/11/12.
 */
public class CashChargeFragment extends BaseFragment implements
        View.OnClickListener, TextWatcher {
    private final String TAG = "CashChargeFragment";

    private Context mContext;

    private EditText addMoneyNumber;// 充值金额

    private TextView needPayNumber;// 需支付

    private LinearLayout wechatpayRelativeLayout;// 微信支付

    private LinearLayout alipayRelativeLayout;

    private RadioButton radioBtn_wechatPay;

    private RadioButton radioBtn_alipay;

    private Button confirmPayButton;// 确认支付

    private View contentView;

    private SharedPreferences sharePre;

    private double recharge;

    private double needPay;

    private DecimalFormat df = new DecimalFormat("0.00");// 保留两位小数

    private int payType=2;//初始支付类型为支付宝
    /**************************/
    /**
     * 支付宝支付相关参数
     **/
    // 商户PID
    public static final String PARTNER = "";
    // 商户收款账号
    public static final String SELLER = "";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 1;

    private String sign;
    /**************************/
    /**
     * 微信支付相关参数
     **/
    private IWXAPI api;

    private final String ACTION_NAME = "successBack";

    private double addIntegral;

    private CashChargeViewModel viewModel;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();

        contentView = inflater.inflate(R.layout.fragment_cash_add, container, false);

        return contentView;
    }

    @Override
    public void findViews() {
        addMoneyNumber = (EditText) contentView.findViewById(R.id.et_add_money_input);

        needPayNumber = (TextView) contentView.findViewById(R.id.tv_need_pay_number);

        wechatpayRelativeLayout = (LinearLayout) contentView.findViewById(R.id.ll_wechat_pay);

        alipayRelativeLayout = (LinearLayout) contentView.findViewById(R.id.ll_alipay_pay);

        radioBtn_wechatPay = (RadioButton) contentView.findViewById(R.id.radioBtn_wechatPay);

        radioBtn_alipay = (RadioButton) contentView.findViewById(R.id.radioBtn_alipay);

        confirmPayButton = (Button) contentView.findViewById(R.id.btn_confirm_pay);

        viewModel=new CashChargeViewModel(this, new CashChargeModel());
    }

    @Override
    public void initData() {
        radioBtn_alipay.setChecked(true);

        sharePre = getActivity().getSharedPreferences("user_login", mContext.MODE_PRIVATE);

        String recharges = sharePre.getString("recharge", "");

        if (!TextUtils.isEmpty(recharges)) {
            recharge = Double.parseDouble(recharges);
        }
    }

    @Override
    public void setListeners() {
        wechatpayRelativeLayout.setOnClickListener(this);

        alipayRelativeLayout.setOnClickListener(this);

        confirmPayButton.setOnClickListener(this);

        addMoneyNumber.addTextChangedListener(this);

        radioBtn_alipay.setOnClickListener(this);

        radioBtn_wechatPay.setOnClickListener(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // 微信支付
            case R.id.radioBtn_wechatPay:
                radioBtn_wechatPay.setChecked(true);

                radioBtn_alipay.setChecked(false);

                payType=1;

                break;
            // 支付宝支付
            case R.id.radioBtn_alipay:
                radioBtn_alipay.setChecked(true);

                radioBtn_wechatPay.setChecked(false);

                payType=2;

                break;

            // 微信支付
            case R.id.ll_wechat_pay:
                radioBtn_wechatPay.setChecked(true);

                radioBtn_alipay.setChecked(false);

                payType=1;

                break;
            // 支付宝支付
            case R.id.ll_alipay_pay:
                radioBtn_alipay.setChecked(true);

                radioBtn_wechatPay.setChecked(false);

                payType=2;

                break;

            // 确认支付
            case R.id.btn_confirm_pay:

                CreatePayment();

                break;

            default:
                break;
        }
    }

    //确认支付
    private void CreatePayment() {
        //需要充值的积分数
        String recharge_integral = addMoneyNumber.getText().toString().trim();

        if(addIntegral<0.1){
            toast("充值积分不能少于0.1",mContext);

            return ;
        }
        if (TextUtils.isEmpty(recharge_integral)) {
            Toast.makeText(mContext, "请输入充值金额", Toast.LENGTH_SHORT).show();

            return;
        }

        BufferCircleDialog.show(mContext, "获取数据", true, null);

        viewModel.createPayment(recharge_integral,payType);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String addAccount = addMoneyNumber.getText().toString().trim();

        if (!TextUtils.isEmpty(addAccount) && !addAccount.equals(".")) {
            addIntegral = Double.parseDouble(addAccount);

            if(addIntegral<0.1){
                needPayNumber.setText("");
            }else{
                needPay=addIntegral/recharge;

                String pay = df.format(needPay);

                needPayNumber.setText(pay);
            }
        } else {
            needPayNumber.setText("");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /*********** 支付宝相关配置 ***************/
    /**************************/
    /**************************/
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    AliPayResult payResult = new AliPayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    Log.i(TAG, "resultStatus:" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        addMoneyNumber.setText("");

                        needPayNumber.setText("");

                        Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功后发送广播
                        //充值成功后，发送广播
                        Intent mIntent = new Intent(ACTION_NAME);
                        //发送广播
                        getActivity().sendBroadcast(mIntent);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(mContext, "支付结果确认中", Toast.LENGTH_SHORT)
                                    .show();

                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            // 用户主动取消支付
                            // Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT)
                            // .show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    //支付宝支付信息
    public void pay(PayResult payResult) {
        String orderInfo = getOrderInfo(payResult);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(payResult.getSign(), "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + "sign_type=" + payResult.getSign_type();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getActivity());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();

                msg.what = SDK_PAY_FLAG;

                msg.obj = result;

                mHandler.sendMessage(msg);
            }
        };
        Log.i(TAG, "payInfo:" + payInfo.toString());
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);

        payThread.start();
    }

    //订单信息
    private String getOrderInfo(PayResult payResult) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + payResult.getPartner();

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + payResult.getSeller_id();

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + payResult.getOut_trade_no();

        // 商品名称
        orderInfo += "&subject=" + payResult.getSubject();

        // 商品详情
        // orderInfo += "&body=" + "\""+"商品名称"+"\"" ;
        orderInfo += "&body=" + payResult.getBody();

        // 商品金额
        // orderInfo += "&total_fee=" +"\""+ price+"\"";
        orderInfo += "&total_fee=" + payResult.getTotal_fee();

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + payResult.getNotify_url();

        // 服务接口名称， 固定值
        orderInfo += "&service=" + payResult.getService();

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + payResult.getPayment_type();

        // 参数编码， 固定值
        orderInfo += "&_input_charset=" + payResult.get_input_charset();
        // orderInfo += "&_input_charset="+"\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        // orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        Log.i(TAG, "orderInfo:" + orderInfo);

        return orderInfo;
    }

    //微信支付信息
    public void wxPay(WXPayResult wxPayResult) {
        PayReq req = new PayReq();

        api = WXAPIFactory.createWXAPI(getActivity(), wxPayResult.getAppid());

        req.appId=wxPayResult.getAppid();

        req.partnerId=wxPayResult.getMch_id();

        req.nonceStr=wxPayResult.getNoncestr();

        req.packageValue=wxPayResult.getPackages();

        req.prepayId=wxPayResult.getPrepayid();

        req.sign=wxPayResult.getSign();

        req.timeStamp=wxPayResult.getTimestamp();

        api.registerApp(wxPayResult.getAppid());

        //检查是否安装微信或微信版本是否支持微信支付
        if(api.isWXAppInstalled()){
            if(api.isWXAppSupportAPI()){
                MyApplication.payTpye = 1;

                LogUtil.d("现金支付调起微信");

                api.sendReq(req);
            }else{
                toast("当前微信版本不支持微信支付，请更新版本！",mContext);
            }
        }else{
            toast("请安装微信客户端！",mContext);
        }
    }

    @Override
    public void onResume() {
        addMoneyNumber.setText("");

        super.onResume();
    }
}

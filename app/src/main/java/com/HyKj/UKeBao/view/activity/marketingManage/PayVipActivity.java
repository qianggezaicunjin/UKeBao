package com.HyKj.UKeBao.view.activity.marketingManage;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityPayVipBinding;
import com.HyKj.UKeBao.model.marketingManage.PayVipModel;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.VipPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;
import com.HyKj.UKeBao.util.AliPayResult;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.viewModel.marketingManage.PayVipViewModel;
import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 充值vip
 * Created by Administrator on 2016/11/14.
 */
public class PayVipActivity extends BaseActiviy{
    private ActivityPayVipBinding mBinding;

    private final String TAG = "PayVipActivity";

    private AlertDialog pay_success_dialog;//支付成功提示框
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

    private int payType=3;//支付类型：现金支付

    private PayVipViewModel viewModel;
    private Button bt_experience;
    private ImageButton bt_back;
    private SharedPreferences sp;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,PayVipActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_pay_vip);

        viewModel=new PayVipViewModel(this,new PayVipModel());

        BufferCircleDialog.show(this,"获取数据中，请稍候..",false,null);

        viewModel.getPayInfo();

        mBinding.setViewModel(viewModel);
    }

    @Override
    public void setUpView() {
        setTitleTheme("支付订单");

        mBinding.rgPayVipPayType.check(0);//初始化支付方式
    }

    @Override
    public void setListeners() {
        //支付方式
        mBinding.rgPayVipPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId){
                    //微信支付
                    case R.id.bt_wxPay:
                        payType=1;

                        break;
                    //支付宝支付
                    case R.id.bt_aliPay:
                        payType=2;

                        break;
                    //现金支付
                    case R.id.bt_cashPay:
                        payType=3;

                        break;
                }
            }
        });

        //确认支付
        mBinding.btVipPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BufferCircleDialog.show(PayVipActivity.this,"正在操作，请稍候..",false,null);

                sp = getSharedPreferences("user_login",MODE_PRIVATE);

                int vipPayId= sp.getInt("vipPayId",0);

                LogUtil.d("vip充值支付类型payType"+payType+"vipPayId------"+vipPayId);

                if(vipPayId!=0) {
                    viewModel.rechargeVip(payType, vipPayId);
                }else {
                    BufferCircleDialog.dialogcancel();

                    toast("抱歉，您不具备vip申请资格!",PayVipActivity.this);
                }
            }
        });

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
                        Toast.makeText(PayVipActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        //支付成功后发送广播
                        //充值成功后，发送广播
                        Intent mIntent = new Intent(ACTION_NAME);
                        //发送广播
                        PayVipActivity.this.sendBroadcast(mIntent);

                        paySuccessDialog();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            BufferCircleDialog.dialogcancel();

                            Toast.makeText(PayVipActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else if (TextUtils.equals(resultStatus, "6001")) {
                            BufferCircleDialog.dialogcancel();

                            // 用户主动取消支付
                            // Toast.makeText(mContext, "取消支付", Toast.LENGTH_SHORT)
                            // .show();

                        } else {
                            BufferCircleDialog.dialogcancel();

                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(PayVipActivity.this, "支付失败", Toast.LENGTH_SHORT)
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
                PayTask alipay = new PayTask(PayVipActivity.this);
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

        api = WXAPIFactory.createWXAPI(this, wxPayResult.getAppid());

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
                MyApplication.payTpye = 5;

                LogUtil.d("现金支付调起微信");

                api.sendReq(req);

            }else{
                toast("当前微信版本不支持微信支付，请更新版本！",this);
            }
        }else{
            toast("请安装微信客户端！",this);
        }
    }

    //支付成功界面
    public void paySuccessDialog(){
        sp.edit().putInt("vipPayId",0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogContentView = View.inflate(this, R.layout.view_vip_success_dialog, null);

        //马上体验
        bt_experience = (Button) dialogContentView.findViewById(R.id.bt_experience);

        bt_back = (ImageButton) dialogContentView.findViewById(R.id.imb_title_bar_back);

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay_success_dialog.dismiss();
            }
        });

        bt_experience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= MainActivity.getStartIntent(PayVipActivity.this);

                intent.putExtra("vip_pay_success",true);

                pay_success_dialog.dismiss();

                startActivity(intent);

                finish();
            }
        });

        pay_success_dialog = builder.create();

        pay_success_dialog.show();

        pay_success_dialog.setCancelable(false);

        pay_success_dialog.setCanceledOnTouchOutside(false);

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = pay_success_dialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 1); // 高度设置为屏幕的1

        lp.width = (int) (display.getWidth() * 1); // 宽度设置为屏幕的1

        dialogWindow.setAttributes(lp);
    }

    //支付按钮设置为不可用
    public void btFlase() {
       mBinding.btCashPay.setEnabled(false);
    }
}

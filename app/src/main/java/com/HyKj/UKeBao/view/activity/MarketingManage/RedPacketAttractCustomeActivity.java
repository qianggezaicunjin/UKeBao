package com.HyKj.UKeBao.view.activity.marketingManage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityRedpacketAttractCustomeBinding;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.RedPacketAttractCustomeModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CashOrIntegralPayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.PayInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.PayResult;
import com.HyKj.UKeBao.model.marketingManage.bean.WXPayInfo;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.GalleryFinalUtil;
import com.HyKj.UKeBao.util.ImageCacheUtils;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.util.SaveBitMapToSD;
import com.HyKj.UKeBao.util.TimeCount;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreSignage.StoreSignageActivity;
import com.HyKj.UKeBao.view.customView.CircleImageView;
import com.HyKj.UKeBao.view.customView.SelectPhotoDialog;
import com.HyKj.UKeBao.viewModel.marketingManage.RedPacketAttractCustomeViewModel;
import com.alipay.sdk.app.PayTask;

import com.squareup.picasso.Picasso;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 红包揽客
 * Created by Administrator on 2016/10/19.
 */
public class RedPacketAttractCustomeActivity extends BaseActiviy implements View.OnClickListener {

    private String integralQuota;

    private ActivityRedpacketAttractCustomeBinding mBinding;

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    private String redPacketImage;//红包图片路径

    private String count;

    private short payType;

    private boolean canClickFlag = true;

    private BusinessInfo businessInfo;

    private TextView tv_efficientMoney;

    private TextView tv_efficientIntegral;

    private TextView tv_price;

    private TextView certain_payTypeFromRedPacket;

    private RadioButton buttonCatsh;

    private RadioButton buttonScore;

    private RadioButton buttonWeixin;

    private RadioButton buttonZhifubao;

    private CircleImageView senRedPacket_toast_reddialog;

    private ImageView finish_red;

    private RadioGroup rg_payType;//1.积分支付 2.现金支付  3.微信支付 4.支付宝支付

    private boolean advertisement_flag = false;//是否有广告语

    private boolean integral_flag = false;//是否有总积分

    private RedPacketAttractCustomeViewModel viewModel;

    private Dialog dialog;

    public String currylocation;//当前位置

    public int distance;//距离

    public String context;

    public double curryentLatitude;//当前纬度

    public double currryentLongtitude;//当前精度

    public String gradearrange;//公里数

    public String memberCount;//会员数

    private PayInfo payInfo;

    private SharedPreferences sharedPreferences;

    private SelectPhotoDialog photoview;

    private Bitmap photo;

    private Uri uritempFile;

    private static final int SDK_PAY_FLAG = 1;

    private static final int FLAG_CHOOSE_IMG = 0x11;

    private static final int FLAG_MODIFY_FINISH = 7;

    private static final int FLAG_CHOOSE_CAMERA = 0x17;

    private static int PHOTO_REQUEST_CUT = 0x88;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RedPacketAttractCustomeActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_redpacket_attract_custome);

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        currylocation = getIntent().getStringExtra("currylocation");

        distance = getIntent().getIntExtra("distance", 3);

        curryentLatitude = getIntent().getDoubleExtra("curryentLatitude", 0.0);

        currryentLongtitude = getIntent().getDoubleExtra("currryentLongtitude", 0.0);

        LogUtil.d("进入发红包页面，纬度为:" + curryentLatitude + "精度为:" + currryentLongtitude);

        gradearrange = getIntent().getStringExtra("gradearrange");

        memberCount = getIntent().getStringExtra("membercount");

        businessInfo = (BusinessInfo) getIntent().getSerializableExtra("businessInfo");

        mBinding.setView(this);

        viewModel = new RedPacketAttractCustomeViewModel(new RedPacketAttractCustomeModel(), this);

        photoview=new SelectPhotoDialog(this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("红包揽客");
    }

    @Override
    public void setListeners() {
        mBinding.adImagRedPacketAttractCustomeActivity.setOnClickListener(this);

        mBinding.senRedPacketRedPacketAttractCustomeActivity.setOnClickListener(this);

        photoview.btn_qx.setOnClickListener(this);

        photoview.btn_pz.setOnClickListener(this);

        photoview.btn_xc.setOnClickListener(this);

        //监听广告语输入框变化
        mBinding.inPutAdRedPacketAttractCustomeActivity.addTextChangedListener(new TextWatcher() {
            @Override

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBinding.showAdContentRedPacketAttractCustomeActivity.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                LogUtil.d("advertisement_context_after:" + editable.toString());

                if (editable.length() == 0) {
                    mBinding.showAdContentRedPacketAttractCustomeActivity.setText("广告内容");

                    advertisement_flag = false;
                } else {
                    advertisement_flag = true;
                }
            }
        });

        //监听总积分输入框变化
        mBinding.inPutScoreRedPacketAttractCustomeActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBinding.showBigScoreRedPacketAttractCustomeActivity.setText(charSequence.toString() + "积分");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    mBinding.showBigScoreRedPacketAttractCustomeActivity.setText("0积分");

                    integral_flag = false;
                } else {
                    mBinding.showBigScoreRedPacketAttractCustomeActivity.setText(editable.toString() + "积分");

                    integral_flag = true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonCatsh_payTypeFromRedPacket:
                payType = 3;

                break;
            case R.id.buttonScore_payTypeFromRedPacket:
                payType = 0;

                break;
            case R.id.buttonWeixin_payTypeFromRedPacket:
                payType = 2;

                break;
            case R.id.buttonZhifubao__payTypeFromRedPacket:
                payType = 1;

                break;
            //广告图片添加
            case R.id.adImag_RedPacketAttractCustome_Activity:
                photoview.show();
//                initGalleryFinal();
//
//                GalleryFinalUtil.go(this, getSupportFragmentManager(), functionConfig, mOnHanlderResuleCallback);

                break;

            //揽客
            case R.id.senRedPacket_RedPacketAttractCustome_Activity:
                //广告内容
                String advertisement_context = mBinding.inPutAdRedPacketAttractCustomeActivity.getText().toString();
                //总积分
                integralQuota = mBinding.inPutScoreRedPacketAttractCustomeActivity.getText().toString();
                //个数
                count = mBinding.inPutCountRedPacketAttractCustomeActivity.getText().toString();

                BufferCircleDialog.show(this, "加速生产红包中..", false, null);

                viewModel.isSend(redPacketImage, advertisement_context, integralQuota, count);

                break;

            //支付前跳出红包动画
            case R.id.certain_payTypeFromRedPacket:
                certain_payTypeFromRedPacket.setEnabled(false);

                if (payType == 0 && Double.valueOf(integralQuota) > businessInfo.integral) {

                    toast("账户积分不足，请充值或选取其他支付方式", this);

                    break;

                }

                String recharge = sharedPreferences.getString("recharge", "-1");

                double aa = Double.valueOf(recharge);

                double score = aa * businessInfo.cash;

                if (payType == 3 && Double.valueOf(integralQuota) > score) {

                    toast("账户现金不足，请充值或选取其他支付方式", this);

                    break;
                }

                toastRedPacket();

                break;

            //支付并生成红包或卡劵
            case R.id.senRedPacket_toast_reddialog:
                context = mBinding.inPutAdRedPacketAttractCustomeActivity.getText().toString();

                ObjectAnimator aniamtionRoyal = ObjectAnimator.ofFloat(
                        senRedPacket_toast_reddialog, "rotationY", 0.0f, 360.0f)
                        .setDuration(600);
                aniamtionRoyal.start();

                aniamtionRoyal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if (canClickFlag) {
                            BufferCircleDialog.show(RedPacketAttractCustomeActivity.this, "加载数据中", false, null);

                            TimeCount ti = new TimeCount(7000, 1000);

                            ti.start();

                            canClickFlag = false;

                            viewModel.sendDataToWeb(count, Double.valueOf(integralQuota), distance,
                                    redPacketImage, context, curryentLatitude,
                                    currryentLongtitude, payType);
                        } else {
                            toast("正在生成红包请稍后", RedPacketAttractCustomeActivity.this);
                        }

                    }

                });

                break;

            //生成红包退出按钮
            case R.id.finish_red:
                dialog.dismiss();

                break;
            // 拍照
            case R.id.btn_pz:
                photoview.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg")));
                startActivityForResult(intent, FLAG_CHOOSE_CAMERA);

                break;
            case R.id.btn_xc:
                photoview.dismiss();
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, FLAG_CHOOSE_IMG);

                break;
        }
    }

    // 弹出红包页面
    public void toastRedPacket() {
        dialog = new Dialog(this);

        Window dialogWindow = dialog.getWindow();

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.toast_redpacket);

        senRedPacket_toast_reddialog = (CircleImageView) dialog.findViewById(R.id.senRedPacket_toast_reddialog);

        finish_red = (ImageView) dialog.findViewById(R.id.finish_red);

        finish_red.setOnClickListener(this);

        senRedPacket_toast_reddialog.setOnClickListener(this);

        dialog.setCanceledOnTouchOutside(true);

        WindowManager m = getWindowManager();

        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6

        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65

        dialogWindow.setAttributes(p);

        dialogWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.photo_red_fade));

        certain_payTypeFromRedPacket.setEnabled(true);

        dialog.show();
    }

    //初始化数据
    public void initPopupWindow(String imagePath) {
        redPacketImage = imagePath;

        //修改Activity背景为半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.alpha = 0.7f;

        getWindow().setAttributes(params);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_paytype_fromredpacket, null);

        tv_efficientMoney = (TextView) view.findViewById(R.id.balanceCatsh_payTypeFromRedPacket);

        tv_efficientIntegral = (TextView) view.findViewById(R.id.balanceScore_payTypeFromRedPacket);

        tv_price = (TextView) view.findViewById(R.id.integralQuota_payTypeFromRedPacket);

        rg_payType = (RadioGroup) view.findViewById(R.id.rg_payType);

        buttonCatsh = (RadioButton) view.findViewById(R.id.buttonCatsh_payTypeFromRedPacket);

        buttonScore = (RadioButton) view.findViewById(R.id.buttonScore_payTypeFromRedPacket);

        buttonWeixin = (RadioButton) view.findViewById(R.id.buttonWeixin_payTypeFromRedPacket);

        buttonZhifubao = (RadioButton) view.findViewById(R.id.buttonZhifubao__payTypeFromRedPacket);

        certain_payTypeFromRedPacket = (TextView) view.findViewById(R.id.certain_payTypeFromRedPacket);

        certain_payTypeFromRedPacket.setOnClickListener(this);

        buttonCatsh.setOnClickListener(this);

        buttonScore.setOnClickListener(this);

        buttonWeixin.setOnClickListener(this);

        buttonZhifubao.setOnClickListener(this);

        rg_payType.check(R.id.buttonScore_payTypeFromRedPacket);//默认勾选

        tv_efficientMoney.setText("可用余额" + businessInfo.cash + "元");

        tv_efficientIntegral.setText("可用积分" + businessInfo.integral + "分");

        tv_price.setText("需要支付:" + mBinding.inPutScoreRedPacketAttractCustomeActivity.getText().toString() + "积分");

        view.setFocusable(true);

        view.setFocusableInTouchMode(true);

        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setFocusable(true);//是否能获得焦点

        popupWindow.setOutsideTouchable(true);//点击空白处退出该窗口，注意：必须设置背景该方法才有效。因为事件分发机制的特性

        ColorDrawable dw = new ColorDrawable(00000);

        popupWindow.setBackgroundDrawable(dw);

        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });

        //设置关闭pop时复原Activity背景
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                //修改Activity背景
                WindowManager.LayoutParams params = getWindow().getAttributes();

                params.alpha = 1f;

                getWindow().setAttributes(params);
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

//    //初始化GalleryFinal
//    public void initGalleryFinal() {
//        //配置功能
//        functionConfig = new FunctionConfig.Builder()
//                .setEnableCamera(false)
//                .setEnableEdit(true)
//                .setEnableCrop(false)
//                .setEnableRotate(true)
//                .setEnablePreview(true)
//                .build();
//
//        //配置imageloader
//        imageloade = new PicassoImageLoader();
//        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloade, MyApplication.themeConfig)
//                .setFunctionConfig(functionConfig)
//                .setNoAnimcation(false)
//                .setPauseOnScrollListener(MyApplication.pauseOnScrollListener)
//                .build();
//
//        GalleryFinal.init(coreConfig);
//    }
//
//    private GalleryFinal.OnHanlderResultCallback mOnHanlderResuleCallback = new GalleryFinal.OnHanlderResultCallback() {
//        @Override
//        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//            redPacketImage = resultList.get(0).getPhotoPath();
//
//            mBinding.adImagRedPacketAttractCustomeActivity.setScaleType(ImageView.ScaleType.FIT_CENTER);
//
//            Picasso.with(RedPacketAttractCustomeActivity.this)
//                    .load(new File(redPacketImage))
//                    .placeholder(R.drawable.default_picture)
//                    .error(R.drawable.default_picture)
//                    .config(Bitmap.Config.ARGB_8888)
//                    .centerCrop()
//                    .resize(320,178)
//                    .into(mBinding.adImagRedPacketAttractCustomeActivity);
//        }
//
//        @Override
//        public void onHanlderFailure(int requestCode, String errorMsg) {
//
//        }
//    };

    //是否展示
    public void isShow() {
        if (dialog.isShowing()) {
            dialog.cancel();
        }
        return;
    }

    //支付宝支付
    public void alipay(PayInfo payInfo) {
        this.payInfo = payInfo;

        String orderInfo = getOrderInfo(payInfo.getPayResult());

        String new_sign = null;
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */

        try {
            /**
             * 仅需对sign 做URL编码
             */
            new_sign = URLEncoder.encode(payInfo.getPayResult().getSign(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */

        final String aliPayInfo = orderInfo + "&sign=\"" + new_sign + "\"&" + "sign_type=" + payInfo.getPayResult().getSign_type();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(RedPacketAttractCustomeActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(aliPayInfo, true);

                Message msg = new Message();

                msg.what = SDK_PAY_FLAG;

                msg.obj = result;

                mHandler.sendMessage(msg);
            }
        };
        LogUtil.d("payInfo:" + aliPayInfo.toString());
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);

        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(PayResult result) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + result.getPartner();

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + result.getSeller_id();

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + result.getOut_trade_no();

        // 商品名称
        orderInfo += "&subject=" + result.getSubject();

        // 商品详情
        orderInfo += "&body=" + result.getBody();

        // 商品金额
        orderInfo += "&total_fee=" + result.getTotal_fee();

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + result.getNotify_url();

        // 服务接口名称， 固定值
        orderInfo += "&service=" + result.getService();

        // 支付类型， 固定值
        orderInfo += "&payment_type=" + result.getPayment_type();

        // 参数编码， 固定值
        orderInfo += "&_input_charset=" + result.get_input_charset();

        LogUtil.d("orderInfo:" + orderInfo);

        return orderInfo;

    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    com.HyKj.UKeBao.util.AliPayResult aliPayResult = new com.HyKj.UKeBao.util.AliPayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = aliPayResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = aliPayResult.getResultStatus();

                    LogUtil.d("resultStatus:" + resultStatus);

                    if (TextUtils.equals(resultStatus, "9000")) {
                        toast("支付成功", RedPacketAttractCustomeActivity.this);
                        //跳转回主界面
                        jump(payInfo, 1);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            toast("支付结果确认中", RedPacketAttractCustomeActivity.this);
                        } else if (TextUtils.equals(resultStatus, "6001")) {

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            toast("支付失败", RedPacketAttractCustomeActivity.this);
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void jump(Object object, int type) {
        switch (type) {
            case 1:
                PayInfo aliInfo = (PayInfo) object;

                Intent intentso = MainActivity.getStartIntent(RedPacketAttractCustomeActivity.this);

                intentso.putExtra("typeAll", 10);

                intentso.putExtra("context", aliInfo.getContext());

                intentso.putExtra("curryentLatitude", curryentLatitude);

                intentso.putExtra("currryentLongtitude", currryentLongtitude);

                intentso.putExtra("distance", distance);

                intentso.putExtra("count", Integer.parseInt(aliInfo.getCount()));

                intentso.putExtra("id", aliInfo.getBusinessStoreShowmanshipId());

                intentso.putExtra("imageUrl", aliInfo.getImage());

                intentso.putExtra("integralQuota", aliInfo.getIntegralQuota());

                startActivity(intentso);

                break;
            case 2:
                WXPayInfo wxinfo = (WXPayInfo) object;

                Intent wx_intent = MainActivity.getStartIntent(RedPacketAttractCustomeActivity.this);

                wx_intent.putExtra("typeAll", 10);

                wx_intent.putExtra("context", wxinfo.getContext());

                wx_intent.putExtra("curryentLatitude", curryentLatitude);

                wx_intent.putExtra("currryentLongtitude", currryentLongtitude);

                wx_intent.putExtra("distance", distance);

                wx_intent.putExtra("count", Integer.parseInt(wxinfo.getCount()));

                wx_intent.putExtra("id", wxinfo.getBusinessStoreShowmanshipId());

                wx_intent.putExtra("imageUrl", wxinfo.getImage());

                wx_intent.putExtra("integralQuota", wxinfo.getIntegralQuota());

                startActivity(wx_intent);

                break;
            case 3:
                CashOrIntegralPayInfo info = (CashOrIntegralPayInfo) object;

                Intent intent = MainActivity.getStartIntent(RedPacketAttractCustomeActivity.this);

                intent.putExtra("typeAll", 10);

                intent.putExtra("context", info.getContext());

                intent.putExtra("curryentLatitude", curryentLatitude);

                intent.putExtra("currryentLongtitude", currryentLongtitude);

                intent.putExtra("distance", distance);

                intent.putExtra("count", Integer.parseInt(info.getCount()));

                intent.putExtra("id", info.getBusinessStoreShowmanshipId());

                intent.putExtra("imageUrl", info.getImage());

                intent.putExtra("integralQuota", info.getIntegralQuota());

                startActivity(intent);

                break;
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        boolean flag = intent.getBooleanExtra("wechat_pay", false);
        if (flag) {
            LogUtil.d("微信支付成功，执行跳转!");

            jump(viewModel.wxPayInfo, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
            imageCut(data.getData());

        } else if (requestCode == FLAG_CHOOSE_CAMERA && resultCode == RESULT_OK) {
            File temp = new File(Environment.getExternalStorageDirectory()
                    + "/test.jpg");
            imageCut(Uri.fromFile(temp));

        } else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {

            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                }
                String path = SaveBitMapToSD.saveBitmap(photo, this)
                        .getPath();
                mBinding.adImagRedPacketAttractCustomeActivity.setImageBitmap(ImageCacheUtils
                        .decodeBitmap(path));
                redPacketImage=path;
            }
        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == RESULT_OK) {
            // 将Uri图片转换为Bitmap
            try {
                //获取uri的路径
//				uploadFilePath = getRealFilePath(mContext, uritempFile);
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                // 将裁剪的bitmap显示在imageview控件上
                String path = SaveBitMapToSD.saveBitmap(bitmap, this).getPath();

                Log.d("上传图片得路径为", path);

                Picasso.with(RedPacketAttractCustomeActivity.this)
                    .load(new File(path))
                    .placeholder(R.drawable.default_picture)
                    .error(R.drawable.default_picture)
                    .config(Bitmap.Config.ARGB_8888)
                    .fit()
                    .into(mBinding.adImagRedPacketAttractCustomeActivity);

                redPacketImage=path;

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private void imageCut(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 开启裁剪功能
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例,2:1
        intent.putExtra("aspectX", 1.56);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("aspectY", 1);

        intent.putExtra("aspectX", 320);

        intent.putExtra("aspectY", 178);

        // intent.putExtra("outputX", 440);
        // intent.putExtra("outputY", 267);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        // intent.putExtra("return-data", true);

        // uritempFile为Uri类变量，实例化uritempFile
        uritempFile = Uri.parse("file://" + "/"
                + Environment.getExternalStorageDirectory().getPath() + "/"
                + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_REQUEST_CUT);

    }
}

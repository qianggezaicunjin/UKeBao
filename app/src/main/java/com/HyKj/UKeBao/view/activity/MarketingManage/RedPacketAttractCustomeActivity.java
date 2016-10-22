package com.HyKj.UKeBao.view.activity.MarketingManage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityRedpacketAttractCustomeBinding;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.RedPacketAttractCustomeModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.GalleryFinalUtil;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.util.TimeCount;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.customView.CircleImageView;
import com.HyKj.UKeBao.viewModel.marketingManage.RedPacketAttractCustomeViewModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
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

    private boolean canClickFlag=true;

    private BusinessInfo businessInfo;

    private TextView tv_efficientMoney;

    private TextView tv_efficientIntegral;

    private TextView tv_price;

    private TextView certain_payTypeFromRedPacket;

    private CircleImageView senRedPacket_toast_reddialog;

    private ImageView finish_red;

    private RadioGroup rg_payType;//1.积分支付 2.现金支付  3.微信支付 4.支付宝支付

    private boolean advertisement_flag=false;//是否有广告语

    private boolean integral_flag=false;//是否有总积分

    private RedPacketAttractCustomeViewModel viewModel;

    private Dialog dialog;

    public String currylocation;//当前位置

    public int distance;//距离

    public String context;

    public double curryentLatitude;//当前纬度

    public double currryentLongtitude;//当前精度

    public String gradearrange;//公里数

    public String memberCount;//会员数

    private SharedPreferences sharedPreferences;

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

        curryentLatitude = getIntent().getDoubleExtra("curryentLayitude", 0.0);

        currryentLongtitude = getIntent().getDoubleExtra("currryentLongtitude", 0.0);

        gradearrange = getIntent().getStringExtra("gradearrange");

        memberCount = getIntent().getStringExtra("membercount");

        businessInfo= (BusinessInfo) getIntent().getSerializableExtra("businessInfo");

        mBinding.setView(this);

        viewModel=new RedPacketAttractCustomeViewModel(new RedPacketAttractCustomeModel(),this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("红包揽客");
    }

    @Override
    public void setListeners() {
        mBinding.adImagRedPacketAttractCustomeActivity.setOnClickListener(this);

        mBinding.senRedPacketRedPacketAttractCustomeActivity.setOnClickListener(this);

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
                LogUtil.d("advertisement_context_after:"+editable.toString());

                if (editable.length() == 0) {
                    mBinding.showAdContentRedPacketAttractCustomeActivity.setText("广告内容");

                    advertisement_flag=false;
                }else {
                    advertisement_flag=true;
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
                mBinding.showBigScoreRedPacketAttractCustomeActivity.setText(charSequence.toString()+"积分");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.length()==0){
                    mBinding.showBigScoreRedPacketAttractCustomeActivity.setText("0积分");

                    integral_flag=false;
                }else {
                    mBinding.showBigScoreRedPacketAttractCustomeActivity.setText(editable.toString()+"积分");

                    integral_flag=true;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //广告图片添加
            case R.id.adImag_RedPacketAttractCustome_Activity:
                initGalleryFinal();

                GalleryFinalUtil.go(this, getSupportFragmentManager(), functionConfig, mOnHanlderResuleCallback);

                break;

            //揽客
            case R.id.senRedPacket_RedPacketAttractCustome_Activity:
                //广告内容
                String advertisement_context=mBinding.inPutAdRedPacketAttractCustomeActivity.getText().toString();
                //总积分
                integralQuota = mBinding.inPutScoreRedPacketAttractCustomeActivity.getText().toString();
                //个数
                count = mBinding.inPutCountRedPacketAttractCustomeActivity.getText().toString();

                BufferCircleDialog.show(this,"加速生产红包中..",false,null);

                viewModel.isSend(redPacketImage,advertisement_context, integralQuota, count);

                break;

            //支付前跳出红包动画
            case R.id.certain_payTypeFromRedPacket:
                if(payType==0&&Double.valueOf(integralQuota)>businessInfo.getIntegral()){

                    toast("账户积分不足，请充值或选取其他支付方式",this);

                    break;

                }

                String recharge=sharedPreferences.getString("recharge", "-1");

                double aa=Double.valueOf(recharge);

                double score=aa*businessInfo.getCash();

                if(payType==3&&Double.valueOf(integralQuota)>score){

                    toast("账户现金不足，请充值或选取其他支付方式",this);

                    break;
                }

                toastRedPacket();

                break;

            //支付并生成红包或卡劵
            case R.id.senRedPacket_toast_reddialog:
                context=mBinding.inPutAdRedPacketAttractCustomeActivity.getText().toString();

                ObjectAnimator aniamtionRoyal = ObjectAnimator.ofFloat(
                        senRedPacket_toast_reddialog, "rotationY", 0.0f, 360.0f)
                        .setDuration(600);
                aniamtionRoyal.start();

                aniamtionRoyal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        if(canClickFlag){
                            BufferCircleDialog.show(RedPacketAttractCustomeActivity.this, "加载数据中", false, null);

                            TimeCount ti = new TimeCount(7000, 1000);

                            ti.start();

                            canClickFlag=false;

                            viewModel.sendDataToWeb(count, Double.valueOf(integralQuota),distance,
                                    redPacketImage,context,curryentLatitude,
                                    currryentLongtitude,payType);
                        }else{
                            toast("正在生成红包请稍后",RedPacketAttractCustomeActivity.this);
                        }

                    }

                });

                break;

            //生成红包退出按钮
            case R.id.finish_red:
                dialog.dismiss();

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

        finish_red=(ImageView)dialog.findViewById(R.id.finish_red);

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

        dialog.show();

    }

    //初始化数据
    public void initPopupWindow(String imagePath) {
        redPacketImage=imagePath;

        //修改Activity背景为半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.alpha = 0.7f;

        getWindow().setAttributes(params);

        LayoutInflater inflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=inflater.inflate(R.layout.activity_paytype_fromredpacket,null);

        tv_efficientMoney= (TextView) view.findViewById(R.id.balanceCatsh_payTypeFromRedPacket);

        tv_efficientIntegral= (TextView) view.findViewById(R.id.balanceScore_payTypeFromRedPacket);

        tv_price= (TextView) view.findViewById(R.id.integralQuota_payTypeFromRedPacket);

        rg_payType= (RadioGroup) view.findViewById(R.id.rg_payType);

        certain_payTypeFromRedPacket= (TextView) view.findViewById(R.id.certain_payTypeFromRedPacket);

        certain_payTypeFromRedPacket.setOnClickListener(this);

        rg_payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                LogUtil.d("选择了第"+i+"种支付方式");

                payType=(short) i;
            }
        });

        tv_efficientMoney.setText("可用余额"+businessInfo.getCash()+"元");

        tv_efficientIntegral.setText("可用积分"+businessInfo.getIntegral()+"分");

        tv_price.setText("需要支付:"+mBinding.inPutScoreRedPacketAttractCustomeActivity.getText().toString()+"积分");

        view.setFocusable(true);

        view.setFocusableInTouchMode(true);

        final PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

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

        popupWindow.showAtLocation(view,Gravity.CENTER, 0, 0);
    }

    //初始化GalleryFinal
    public void initGalleryFinal() {
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setForceCrop(true)
                .setForceCropEdit(true)
                .setCropWidth(216)//裁剪宽度
                .setCropHeight(120)//裁剪高度
                .build();

        //配置imageloader
        imageloade = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloade, MyApplication.themeConfig)
                .setFunctionConfig(functionConfig)
                .setNoAnimcation(false)
                .setPauseOnScrollListener(MyApplication.pauseOnScrollListener)
                .build();

        GalleryFinal.init(coreConfig);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResuleCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            redPacketImage = resultList.get(0).getPhotoPath();

            mBinding.adImagRedPacketAttractCustomeActivity.setScaleType(ImageView.ScaleType.FIT_XY);

            mBinding.addImageTextRedPacketAttractCustomeActivity.setVisibility(View.INVISIBLE);

            Picasso.with(RedPacketAttractCustomeActivity.this)
                    .load(new File(redPacketImage))
                    .placeholder(R.drawable.default_picture)
                    .config(Bitmap.Config.RGB_565)
                    .into(mBinding.adImagRedPacketAttractCustomeActivity);
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };
}

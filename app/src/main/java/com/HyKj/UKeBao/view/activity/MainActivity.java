package com.HyKj.UKeBao.view.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.MainModel;
import com.HyKj.UKeBao.model.marketingManage.bean.LanBean;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.businessManage.payrecord.PayRecordActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.PayVipActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.VipFunctionActivity;
import com.HyKj.UKeBao.view.fragment.businessManage.StoreManagerFragment;
import com.HyKj.UKeBao.view.fragment.marketingManage.LanFragment;
import com.HyKj.UKeBao.view.fragment.marketingManage.MarketManagerFragment;
import com.HyKj.UKeBao.view.fragment.userInfoManage.LeftMenuFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;
import com.HyKj.UKeBao.viewModel.MainViewModel;
import com.baidu.mapapi.SDKInitializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class MainActivity extends BaseFragmentActivity implements OnClickListener,
        OnCheckedChangeListener, MainFragmentListener {
    private DrawerLayout mDrawerLayout;

    private FrameLayout leftMenu;

    private FrameLayout frameLayout;

    private RadioGroup radioGroupGuide;

    private RadioButton radioButtonStoreManager;

    private RadioButton radioButtonLang;

    private RadioButton radioButtonMarketManager;

    private StoreManagerFragment storeManagerFragment;

    private MarketManagerFragment marketManagerFragment;

    private LanFragment langFragment;

    private List<Fragment> fragmentList = new ArrayList<Fragment>();

    private int currentTabIndex = 0;

    private LeftMenuFragment leftFragment;

    private ActionBarDrawerToggle mDrawerToggle;

    private AlertDialog exitDialog;

    private Button confirmExitButton;

    private Button cancelExitButton;

    private TextView contentText;

    private String ACTION_NAME = "BAIDU_TUISONG_TOUCHUAN";

    private Context mContext;

    private TextView tv_ask_confirm_exit;

    private int message_count = 0;
    // 百度推送信息
    private String data = "";
    // 推送类型 1为语音文本
    private String type = "";
    // 语音信息
    private String voiceContext = "";

    private int count = 0;

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private String version;

    protected String urlsFromWebString;

    private boolean showMesageDialogFlag = true;

    private boolean clickOpen = false;

    private Button btn_confirm;

    private Button btn_cancel_exit;

    private TextView tv_confirm_exit;

    private Button btn_cancel_update;

    private AlertDialog gengXinDialog;//更新提示框

    private AlertDialog vipDialog;//更新提示框

    private MainViewModel viewModel;

    private Button bt_dialog_close;

    private Button bt_dialog_openVip;

    private TextView tv_vip_function;

    private boolean setCenter_flag=false;//是否启动点击回到中心点

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        leftMenu = (FrameLayout) findViewById(R.id.fl_leftMenu);

        frameLayout = (FrameLayout) findViewById(R.id.fl_mainActivity);

        radioGroupGuide = (RadioGroup) findViewById(R.id.radioGroup_guide);

        radioButtonStoreManager = (RadioButton) findViewById(R.id.radioButton_store_manager);

        radioButtonLang = (RadioButton) findViewById(R.id.radioButton_lang);

        radioButtonMarketManager = (RadioButton) findViewById(R.id.radioButton_market_manager);

        mContext = this;

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        message_count = sharedPreferences.getInt("message_count", 0);

        viewModel = new MainViewModel(this, new MainModel());

        //注册广播
        registerBoradcastReceiver();
    }

    @Override
    public void setUpView() {
        SystemBarUtil.changeColor(R.color.transparent);

        PackageManager manager = this.getPackageManager();

        PackageInfo info;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);

            version = info.versionName;

            editor.putString("versonName", version);

            editor.commit();
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        viewModel.whetheToUpDate(Integer.valueOf(version.replace(".", "")));

        radioGroupGuide.check(R.id.radioButton_store_manager);

        initFragment();//初始化Fragment
    }

    @Override
    public void setListeners() {
        radioGroupGuide.setOnCheckedChangeListener(this);

        radioButtonLang.setOnClickListener(this);//当揽视图加载完毕之后，添加点击揽返回原坐标点击事件

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int arg0) {
                clickOpen = viewModel.isOpenleft(leftFragment, arg0);//判断是否打开
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDrawerOpened(View arg0) {
                // TODO Auto-generated method stub
                clickOpen = false;
                viewModel.isOpen = true;
            }

            @Override
            public void onDrawerClosed(View arg0) {
                viewModel.isOpen = false;
                // TODO Auto-generated method stub
//				mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }
        });

    }

    @Override
    public void toastOutLeftFragment() {
        clickOpen = true;
        if (!mDrawerLayout.isDrawerOpen(leftMenu)) {
            mDrawerLayout.openDrawer(leftMenu);
            if (leftFragment != null) {
                leftFragment.viewModel.getBusinessInfo();
            }
        } else {
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        // 店铺管理
        if (checkedId == radioButtonStoreManager.getId()) {
            LogUtil.d("点击了店铺管理fragment");

            SystemBarUtil.changeColor(R.color.transparent);

            setCenter_flag=false;

            switchFragment(0);
        }
        // 揽
//        else if (checkedId == radioButtonLang.getId()) {
//            LogUtil.d("执行了揽onCheckedChanged");
//        }
        // 营销管理
        else if (checkedId == radioButtonMarketManager.getId()) {

            setCenter_flag=false;

            switchFragment(2);

            SystemBarUtil.changeColor(R.color.status_color);

            LogUtil.d("点击了营销管理fragment");

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 更新
            case R.id.btn_confirm:
                if (null != urlsFromWebString) {
                    Intent intent = new Intent();

                    intent.setAction("android.intent.action.VIEW");

                    Uri content_url = Uri.parse(urlsFromWebString);

                    intent.setData(content_url);

                    startActivity(intent);
                } else {
                    Toast.makeText(this, "请检查网络,更新失败", Toast.LENGTH_SHORT);
                }
                gengXinDialog.dismiss();
                break;
            case R.id.btn_cancel_update:
                gengXinDialog.dismiss();
                break;
            //点击用户头像时，弹出侧滑菜单
            case R.id.imb_user_icon:
                LogUtil.d("onClick imb_user_icon");
                toastOutLeftFragment();
                break;
            //当订单提醒款到达，点击确认件触发的事件
            case R.id.btn_confirm_exit:
                exitDialog.dismiss();

                message_count = 0;

                count = 0;

                storeManagerFragment.updateNews(0);

                editor.putInt("message_count", message_count);

                startActivity(PayRecordActivity.getStartIntent(this));

                break;
            //订单提醒框取消
            case R.id.btn_cancel_exit:
                exitDialog.dismiss();

                break;
//            //揽
//            case R.id.radioButton_lang:
//                LogUtil.d("点击了揽fragment");
//
//                viewModel.isVip();
//
//                break;
            //VIP取消框按钮
            case R.id.bt_dialog_close:

                vipDialog.dismiss();

                break;
            //VIP商户功能说明
            case R.id.tv_vip_function:
                vipDialog.dismiss();

                startActivity(VipFunctionActivity.getStartIntent(this));

                break;
            //开通vip
            case R.id.bt_dialog_openVip:
                vipDialog.dismiss();

                BufferCircleDialog.show(this, "正在申请成为vip~", true, null);

                viewModel.applyVip();

//                startActivity(PayVipActivity.getStartIntent(this));

                break;
            //揽
            case R.id.radioButton_lang:
                LogUtil.d("执行揽onclick");

                if(setCenter_flag){
                    langFragment.moveThePositon();
                }else{
                    viewModel.isVip();
                }
                break;
            default:
                break;
        }
    }

    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();

        myIntentFilter.addAction(ACTION_NAME);

        myIntentFilter.addAction("FINISH_MAIN");

        myIntentFilter.addAction("CLEAR_NUM");
        // 注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(ACTION_NAME)) {
                voiceContext = intent.getStringExtra("voiceContext");

                getCount();//当有订单时，刷新订单数量标记

                if (showMesageDialogFlag) {
                    showMessageDialog(count + "");
                } else {
                    if (exitDialog.isShowing()) {
                        tv_ask_confirm_exit.setText(count + "");
                    } else {
                        exitDialog.show();
                        tv_ask_confirm_exit.setText(count + "");
                    }
                }
            }
            if (action.equals("FINISH_MAIN")) {
                finish();
            }
            if (action.equals("CLEAR_NUM")) {
                LogUtil.d("接收到广播，执行清空方法");
                storeManagerFragment.updateNews(0);
            }

        }
    };

    //当有订单时，刷新订单数量标记
    private void getCount() {
        count++;

        message_count++;

        editor.putInt("message_count", message_count);

        editor.commit();

        storeManagerFragment.updateNews(count);
    }

    @SuppressLint("ValidFragment")
    private void initFragment() {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);

        radioGroupGuide.measure(w, h);

        int height = radioGroupGuide.getMeasuredHeight();

        leftFragment = new LeftMenuFragment();

        //设置版本号
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_leftMenu, leftFragment).commit();

        storeManagerFragment = new StoreManagerFragment(this);

        marketManagerFragment = new MarketManagerFragment(this);

        langFragment = new LanFragment(getApplicationContext(), this);

        fragmentList.add(storeManagerFragment);

        fragmentList.add(langFragment);

        fragmentList.add(marketManagerFragment);
        // 默认显示店铺管理，集合中第一个
        switchFragment(currentTabIndex);
    }

    /**
     * 选择fragment
     *
     * @param targetTabIndex
     */
    public void switchFragment(int targetTabIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment currentFragment = fragmentList.get(currentTabIndex);

        Fragment targetFragment = fragmentList.get(targetTabIndex);
        if (currentFragment != targetFragment) {
            if (!targetFragment.isAdded()) {
                transaction.hide(currentFragment).add(R.id.fl_mainActivity, targetFragment);
            } else {
                transaction.hide(currentFragment).show(targetFragment);
            }

        } else {
            if (!targetFragment.isAdded()) {
                // 相同为默认的集合第一个currentTabIndex=0;
                transaction.add(R.id.fl_mainActivity, targetFragment).show(targetFragment);
            }
        }
        transaction.commit();

        currentTabIndex = targetTabIndex;
    }


    //订单提醒框
    private void showMessageDialog(String count) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View dialogContentView = View.inflate(mContext,
                R.layout.message_dialog, null);

        confirmExitButton = (Button) dialogContentView
                .findViewById(R.id.btn_confirm_exit);

        cancelExitButton = (Button) dialogContentView
                .findViewById(R.id.btn_cancel_exit);

        tv_ask_confirm_exit = (TextView) dialogContentView
                .findViewById(R.id.tv_ask_confirm_exit);

        tv_ask_confirm_exit.setText(count + "");

        confirmExitButton.setOnClickListener(this);

        cancelExitButton.setOnClickListener(this);

        exitDialog = builder.create();

        exitDialog.show();

        exitDialog.setCancelable(false);

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = exitDialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.3); // 高度设置为屏幕的0.6

        lp.width = (int) (display.getWidth() * 0.8); // 宽度设置为屏幕的0.95

        dialogWindow.setAttributes(lp);

        showMesageDialogFlag = false;
    }

    // 更新对话框
    public void initUpdateDialog(String content, String url) {
        //拿到更新地址
        this.urlsFromWebString = url;

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View dialogContentView = View.inflate(mContext, R.layout.update_dialog, null);

        btn_confirm = (Button) dialogContentView.findViewById(R.id.btn_confirm);

        btn_cancel_update = (Button) dialogContentView.findViewById(R.id.btn_cancel_update);

        tv_confirm_exit = (TextView) dialogContentView.findViewById(R.id.tv_confirm_exit);

        btn_confirm.setOnClickListener(this);

        btn_cancel_update.setOnClickListener(this);

        btn_confirm.setText("马上更新");

        btn_cancel_update.setText("稍后更新");

        tv_confirm_exit.setText(content);

        gengXinDialog = builder.create();

        gengXinDialog.show();

        gengXinDialog.setCancelable(false);

        gengXinDialog.setCanceledOnTouchOutside(false);

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = gengXinDialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.25); // 高度设置为屏幕的0.6

        lp.width = (int) (display.getWidth() * 0.8); // 宽度设置为屏幕的0.95

        dialogWindow.setAttributes(lp);
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getIntExtra("typeAll", -1) == 10) {
            String context = intent.getStringExtra("context");

            String id = intent.getStringExtra("id");

            double curryentLatitude = intent.getDoubleExtra("curryentLatitude", -1.0);

            double currryentLongtitude = intent.getDoubleExtra("currryentLongtitude", -1.0);

            LogUtil.d("跳转成功，纬度为:" + curryentLatitude + "精度为:" + currryentLongtitude);

            double distance = intent.getDoubleExtra("distance", -1.0);

            int count = intent.getIntExtra("count", 3);

            LanBean bean = new LanBean();

            bean.setDistance(distance);

            bean.setId(Integer.valueOf(id));

            bean.setImageUrl(intent.getStringExtra("imageUrl"));

            bean.setIntegralQuota(intent.getStringExtra("integralQuota"));

            bean.setSurplusCount("0");

            bean.setContext(context);

            bean.setCurrryentLongtitude(currryentLongtitude);

            bean.setCurryentLatitude(curryentLatitude);

            bean.setCount(count);

            langFragment.setToMessageFromRedPacket(bean);
        } else if (intent.getIntExtra("typeAll", -1) == 11) {
            LanBean bean = new LanBean();

            intent.getStringExtra("id");

            intent.getStringExtra("nameTiltle");

            double curryentLatitude = intent.getDoubleExtra("curryentLatitude", -1.0);

            double currryentLongtitude = intent.getDoubleExtra("currryentLongtitude", -1.0);

            bean.setCurrryentLongtitude(currryentLongtitude);

            bean.setCurryentLatitude(curryentLatitude);

            bean.setId(Integer.valueOf(intent.getStringExtra("id")));

            bean.setNameTiltle(intent.getStringExtra("nameTiltle") + "");

            langFragment.setToMessageFromCardDetail(bean);
        } else if (intent.getBooleanExtra("vip_pay_success", false)) {
            switchFragment(1);

            SystemBarUtil.changeColor(R.color.status_color);

            setCenter_flag=true;
        }
    }

    //判断是否为vip  (0:已开通 2:未开通)
    public void isVip(int isVip) {
        switch (isVip) {
            //会员
            case 0:
                switchFragment(1);

                setCenter_flag=true;

                SystemBarUtil.changeColor(R.color.status_color);

                break;

            //非会员
            case 2:
                initDialog();

                break;
        }
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        View dialogContentView = View.inflate(mContext, R.layout.view_vip_dialog, null);

        bt_dialog_close = (Button) dialogContentView.findViewById(R.id.bt_dialog_close);//关闭

        bt_dialog_openVip = (Button) dialogContentView.findViewById(R.id.bt_dialog_openVip);//开通vip

        tv_vip_function = (TextView) dialogContentView.findViewById(R.id.tv_vip_function);//开通vip功能提示

        bt_dialog_close.setOnClickListener(this);

        bt_dialog_openVip.setOnClickListener(this);

        tv_vip_function.setOnClickListener(this);

        vipDialog = builder.create();

        vipDialog.show();

        vipDialog.setCancelable(true);

        vipDialog.setCanceledOnTouchOutside(true);

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = vipDialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.63); // 高度设置为屏幕的0.63

        lp.width = (int) (display.getWidth() * 0.73); // 宽度设置为屏幕的0.73

        dialogWindow.setAttributes(lp);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    //申请vip资格成功回调
    public void setVipPayInfo(int id) {
        SharedPreferences sp = getSharedPreferences("user_login", MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        //vip支付id
        editor.putInt("vipPayId", id);

        editor.commit();

        startActivity(PayVipActivity.getStartIntent(this));
    }
}

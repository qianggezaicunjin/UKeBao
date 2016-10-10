package com.HyKj.UKeBao.view.fragment.businessManage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.StoreManagerFragmentModel;
import com.HyKj.UKeBao.model.businessManage.bean.NotifyInfo;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.businessManage.BaseWebViewActivity;
import com.HyKj.UKeBao.view.activity.businessManage.businessSettings.BusinessSettingsActivity;
import com.HyKj.UKeBao.view.activity.businessManage.financialManagement.FinancialManagementActivity;
import com.HyKj.UKeBao.view.activity.businessManage.payrecord.PayRecordActivity;
import com.HyKj.UKeBao.view.activity.businessManage.giveIntegral.GiveIntegralActivity;
import com.HyKj.UKeBao.view.adapter.HomeGridViewAdapter;
import com.HyKj.UKeBao.view.customView.MyGridView;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;
import com.HyKj.UKeBao.viewModel.businessManage.StoreManagerFragmentViewModel;

import java.util.List;

/**
 * 店铺管理页面
 * Created by Administrator on 2016/9/21.
 */
public class StoreManagerFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * 赠送积分
     */
    private ImageButton presentIntegral;

    private View contentView;

    private MainFragmentListener imagListener;

    private MyGridView gridViewStoreManager;

    private String[] textStr;

    private int[] imgRes;

    private int newCount;

    private ImageButton imb_user;

    private SharedPreferences sharedPreferences;

    private Editor editor;

    private String businessStoreId;

    private String currentNotify, nextNotify;

    private StoreManagerFragmentViewModel viewModel;

    private TextView tv_current_notify, tv_next_notify;

    private List<NotifyInfo> notifyList;

    private boolean firstShow = true;

    private View dialogContentView;
    /**
     * 公告对话框标题
     */
    private TextView notifyIntegralDialogTitle;
    /**
     * 公司名称
     */
    private TextView notifyDialogCompany;
    /**
     * 公告日期
     */
    private TextView notifyDialogDate;

    private TextView tv_notify_integral_context;

    private AlertDialog notifyDialog;

    public StoreManagerFragment() {
        super();
    }

    public StoreManagerFragment(MainFragmentListener listener) {
        imagListener = listener;
    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate((R.layout.fragment_store_manager), container, false);
        }
        return contentView;
    }

    @Override
    public void findViews() {
        sharedPreferences = this.getActivity().getSharedPreferences(
                "user_login", this.getActivity().MODE_PRIVATE);

        editor = sharedPreferences.edit();

        gridViewStoreManager = (MyGridView) contentView.findViewById(R.id.gridview_storeManager);

        imb_user = (ImageButton) contentView.findViewById(R.id.imb_user_icon);

        tv_current_notify = (TextView) contentView.findViewById(R.id.tv_current_notify);

        tv_next_notify = (TextView) contentView.findViewById(R.id.tv_next_notify);

        presentIntegral = (ImageButton) contentView
                .findViewById(R.id.imb_present_integral);

        viewModel = new StoreManagerFragmentViewModel(new StoreManagerFragmentModel(), this);


    }

    @Override
    public void initData() {
        businessStoreId = sharedPreferences.getString("businessStoreId", "");

        initRes();

        gridViewStoreManager.setAdapter(new HomeGridViewAdapter(mContext,
                textStr, imgRes, newCount));

        LogUtil.d("初始化获取系统公告，发送网络请求");

        //从服务器获取系统公告信息
        viewModel.getNoticeInfo();
    }

    @Override
    public void setListeners() {
        imb_user.setOnClickListener(this);

        gridViewStoreManager.setOnItemClickListener(this);

        tv_current_notify.setOnClickListener(this);

        tv_next_notify.setOnClickListener(this);

        presentIntegral.setOnClickListener(this);

    }

    @Override
    public void initViews() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            //店铺设置
            case 0:
                LogUtil.d("OnClick Bussiness Setting!");

                startActivity(BusinessSettingsActivity.getStartIntent(getActivity()));

                break;
            //商品管理
            case 1:
                LogUtil.d("OnClick Goods Manage!");

                break;
            //买单记录
            case 2:
                LogUtil.d("OnClick Order account!");

                startActivity(PayRecordActivity.getStartIntent(getActivity()));

                updateNews(0);

                sharedPreferences = this.getActivity().getSharedPreferences("user_login", this.getActivity().MODE_PRIVATE);

                editor = sharedPreferences.edit();

                editor.putInt("message_count", 0);

                editor.commit();

                break;
            //会员管理
            case 3:
                LogUtil.d("OnClick Member Manage!" + MyApplication.token);

                Intent intent = BaseWebViewActivity.getStartIntent(mContext);

                intent.putExtra("title", "会员管理");

                intent.putExtra("url", "http://test.51ujf.cn/" + "business/member.html?v=1000&token=" + MyApplication.token);

                startActivity(intent);

                break;
            //财务管理
            case 4:
                LogUtil.d("OnClick Finance Manage!");

                startActivity(FinancialManagementActivity.getStartIntent(getActivity()));

                break;
            //评价管理
            case 5:
                LogUtil.d("OnClick Comment Manage!");

                Intent intent_comment = BaseWebViewActivity.getStartIntent(mContext);

                intent_comment.putExtra("title", "评价管理");

                intent_comment.putExtra("url", "http://test.51ujf.cn/"
                        + "business/comment.html?v=1000&token="
                        + MyApplication.token);

                startActivity(intent_comment);

                break;
            //公告设置
            case 6:
                LogUtil.d("OnClick Notice setting!");

                Intent intent_notice = BaseWebViewActivity.getStartIntent(mContext);

                intent_notice.putExtra("title", "公告设置");

                intent_notice.putExtra("url", "http://test.51ujf.cn/"
                        + "business/notice.html?v=1000&token="
                        + MyApplication.token);

                startActivity(intent_notice);

                break;
            //收款二维码
            case 7:
                LogUtil.d("OnClick QR code!");

                break;
            //店铺浏览
            case 8:
                LogUtil.d("OnClick Bussiness browse!");

                Intent intent_browse = BaseWebViewActivity.getStartIntent(mContext);

                intent_browse.putExtra("title", "店铺浏览");

                intent_browse.putExtra("url", "http://test.51ujf.cn/"
                        + "mobile/details.html?id="
                        + businessStoreId
                        + "&v=1000&token="
                        + MyApplication.token);

                startActivity(intent_browse);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 赠送积分
            case R.id.imb_present_integral:
                startActivity(GiveIntegralActivity.getStartIntent(getActivity()));
                break;
            case R.id.imb_user_icon:
                imagListener.toastOutLeftFragment();
                break;
            // 公告通知栏
            case R.id.tv_current_notify:
                showNotifyIntegralDialog(tv_current_notify);
                break;
            // 公告通知栏
            case R.id.tv_next_notify:
                showNotifyIntegralDialog(tv_next_notify);
                break;
        }
    }

    //刷新未读订单提醒数
    public void updateNews(int newCount) {
        gridViewStoreManager.setAdapter(new HomeGridViewAdapter(mContext,
                textStr, imgRes, newCount));
    }

    /**
     * 初始化功能模块的标题和图标
     */
    private void initRes() {
        textStr = new String[]{"店铺设置", "商品管理", "买单记录", "会员管理", "财务管理",
                "评价管理", "公告设置", "收款二维码", "店铺预览"};
        imgRes = new int[]{R.drawable.store_setting,
                R.drawable.product_manager, R.drawable.pay_recode,
                R.drawable.user_icon, R.drawable.finance_manager,
                R.drawable.comment_manager, R.drawable.notify_manager,
                R.drawable.make_scode, R.drawable.return_store};
    }

    /**
     * 公告通知栏
     */
    private void showNotifyIntegralDialog(TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        dialogContentView = View.inflate(mContext,
                R.layout.dialog_notify_integral, null);

        notifyIntegralDialogTitle = (TextView) dialogContentView.findViewById(R.id.tv_notify_integral_title);

        notifyDialogCompany = (TextView) dialogContentView.findViewById(R.id.tv_notify_company_name);

        notifyDialogDate = (TextView) dialogContentView.findViewById(R.id.tv_notify_date);

        tv_notify_integral_context = (TextView) dialogContentView.findViewById(R.id.tv_notify_integral_context);

        notifyDialog = builder.create();

        notifyDialog.show();
        // 点击dialog区域外关闭对话框
        notifyDialog.setCanceledOnTouchOutside(true);

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = notifyDialog.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = mContext.getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.6); // 高度设置为屏幕的0.6

        lp.width = (int) (display.getWidth() * 0.8); // 宽度设置为屏幕的0.8

        dialogWindow.setAttributes(lp);
        // 设置标题，公司名称和公告日期
        String title = textView.getText().toString().trim();

        for (int i = 0; i < notifyList.size(); i++) {
            NotifyInfo notify = notifyList.get(i);
            if (notify.title.equals(title)) {
                notifyIntegralDialogTitle.setText(notify.title);

                notifyDialogDate.setText(notify.addTime);

                tv_notify_integral_context.setText(notify.context);
            }
        }
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }

    public void setNoticeInfo(List<NotifyInfo> mNotifyInfo) {
        notifyList = mNotifyInfo;

        TranslateAnimation inAnim = (TranslateAnimation) AnimationUtils
                .loadAnimation(mContext,
                        R.anim.in_to_buttom);

        tv_current_notify.setText("公告消息");

        tv_current_notify.startAnimation(inAnim);

        //消息循环
        viewModel.notifyRunning();
    }

    //轮询公告栏
    public void refreshNotice(String currentNotify, String nextNotify) {
        TranslateAnimation outAnim = (TranslateAnimation) AnimationUtils
                .loadAnimation(mContext, R.anim.out_to_top);

        TranslateAnimation inAnim = (TranslateAnimation) AnimationUtils
                .loadAnimation(mContext, R.anim.in_to_buttom);

        // 添加动画
        tv_next_notify.setVisibility(View.VISIBLE);

        if (firstShow) {
            firstShow = false;

            tv_next_notify.setText(nextNotify);

            tv_current_notify.startAnimation(outAnim);

            tv_next_notify.startAnimation(inAnim);
        } else {
            tv_current_notify.setText(currentNotify);

            tv_next_notify.setText(nextNotify);

            tv_current_notify.startAnimation(outAnim);

            tv_next_notify.startAnimation(inAnim);
        }
    }
}

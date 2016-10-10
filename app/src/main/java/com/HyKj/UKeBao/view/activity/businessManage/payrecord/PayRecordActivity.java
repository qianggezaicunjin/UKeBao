package com.HyKj.UKeBao.view.activity.businessManage.payrecord;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.bean.OrderRecord;
import com.HyKj.UKeBao.model.businessManage.payrecord.PayRecordModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.businessManage.financialManagement.FinancialManagementActivity;
import com.HyKj.UKeBao.view.adapter.businessManage.PayRecordAdapter;
import com.HyKj.UKeBao.viewModel.businessManage.payrecord.PayRecordViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PayRecordActivity extends BaseActiviy implements View.OnClickListener, AdapterView.OnItemClickListener {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, PayRecordActivity.class);

        return intent;
    }

    private final String TAG = "PayRecordActivity";

    private List<OrderRecord> mList = new ArrayList<OrderRecord>();

    private List<OrderRecord> mListCancle = new ArrayList<OrderRecord>();

    private List<OrderRecord> mListSuccess = new ArrayList<OrderRecord>();

    /**
     * 显示全部状态
     */
    private Button showAllOrderState;
    /**
     * 买点汇总
     */
    private RelativeLayout payRecordTotal;
    /**
     * 买点记录列表数据
     */
    private PullToRefreshListView payRecordListView;
    /**
     * 全部状态pop
     */
    private PopupWindow popupWindow;
    /**
     * popup内相应的筛选,全部订单，交易成功，退款订单
     */
    private TextView showAllOrder, showSuccessOrder, showRefundOrder;

    private RelativeLayout include_title_bar;

    private SharedPreferences sharedPreferences;

    private double recharge = 0;

    private int page = 1;

    private int rows = 20;

    private ListView listView;

    private int statuss = -1;

    private int rowsAll;

    private String ACTION_NAME = "BAIDU_TUISONG_TOUCHUAN";

    private SharedPreferences.Editor editor;

    private PayRecordAdapter mListAdapter;

    private PayRecordAdapter mListSuccessAdapter;

    private PayRecordAdapter mListCancleAdapter;

    private PayRecordViewModel viewModel;

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_payrecord);

        MyApplication.flag_pay = false;

        viewModel = new PayRecordViewModel(new PayRecordModel(), this);

        registerBoradcastReceiver();//注册广播

        showAllOrderState = (Button) findViewById(R.id.btn_all_order_state);

        payRecordTotal = (RelativeLayout) findViewById(R.id.rl_pay_record_total);

        payRecordListView = (PullToRefreshListView) findViewById(R.id.lv_pay_record);

        include_title_bar = (RelativeLayout) findViewById(R.id.include_title_bar);

        listView = payRecordListView.getRefreshableView();
    }

    @Override
    public void setUpView() {
        setTitleTheme("订单记录");

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        String recharges = sharedPreferences.getString("recharge", "");

        LogUtil.d("recharges" + recharges);

        recharge = Double.valueOf(recharges);

        mListAdapter = new PayRecordAdapter(this, mList, recharge);

        mListSuccessAdapter = new PayRecordAdapter(this, mListSuccess, recharge);

        mListCancleAdapter = new PayRecordAdapter(this, mListCancle, recharge);

        //默认显示全部订单
        listView.setAdapter(mListAdapter);

        viewModel.requireDataFromWeb(-1, page, rows);

        payRecordListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public void setListeners() {
        showAllOrderState.setOnClickListener(this);

        payRecordTotal.setOnClickListener(this);

        listView.setOnItemClickListener(this);

        payRecordListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                if (refreshView.isHeaderShown()) {
                    page = 1;

                    mList.clear();

                    mListSuccess.clear();

                    mListCancle.clear();

                    viewModel.requireDataFromWeb(statuss, page, rows);

                } else {
                    page++;

                    viewModel.requireDataFromWeb(statuss, page, rows);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = PayDetailsActivity.getStartIntent(this);

        OrderRecord orderRecord=null;

        switch (statuss){
            case -1:
                orderRecord=mList.get(position-1);

                break;
            case 1:
                orderRecord=mListSuccess.get(position-1);

                break;
            case 5:
                orderRecord=mListCancle.get(position-1);

                break;
        }
        Bundle bundle=new Bundle();

        bundle.putSerializable("orderRecord",orderRecord);

        intent.putExtras(bundle);

        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 全部状态
            case R.id.btn_all_order_state:
                showAllOrderStatePupWindow();
                break;
            // 今天汇总
            case R.id.rl_pay_record_total:
                startActivity(FinancialManagementActivity.getStartIntent(this));
                break;
            // 显示全部订单
            case R.id.tv_show_all_order:
                Log.i(TAG, "显示全部订单");

                showAllOrderState.setText("全部订单");

                showAllOrderState.setText("全部订单");

                page = 1;

                statuss = -1;

                //清空数据加载新的数据
                mList.clear();

                listView.setAdapter(mListAdapter);

                viewModel.requireDataFromWeb(-1, page, rows);

                popupWindow.dismiss();

                break;
            // 显示交易成功订单
            case R.id.tv_show_success_order:
                Log.i(TAG, "显示交易成功订单");

                showAllOrderState.setText("交易成功订单");

                listView.setAdapter(mListSuccessAdapter);

                //清空数据加载新的数据
                mListSuccess.clear();

                page = 1;

                statuss = 1;

                viewModel.requireDataFromWeb(1, page, rows);

                popupWindow.dismiss();

                break;
            // 显示退款订单
            case R.id.tv_show_refund_order:
                listView.setAdapter(mListCancleAdapter);

                //清空数据加载新的数据
                mListCancle.clear();

                showAllOrderState.setText("退款订单");

                Log.i(TAG, "显示退款订单");

                page = 1;

                statuss = 5;

                viewModel.requireDataFromWeb(5, page, rows);

                popupWindow.dismiss();

                break;
            default:
                break;
        }
    }

    /**
     * 显示全部状态pupwindow
     */
    private void showAllOrderStatePupWindow() {
        getPopupWindow();

        popupWindow.showAsDropDown(include_title_bar);

        //修改Activity背景为半透明
        WindowManager.LayoutParams params = getWindow().getAttributes();

        params.alpha = 0.7f;

        getWindow().setAttributes(params);

    }

    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件
        View popupWindow_view = getLayoutInflater().inflate(R.layout.item_pupwindow_pay_record, null, false);

        popupWindow = new PopupWindow(popupWindow_view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
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

        // 找到popup内控件
        showAllOrder = (TextView) popupWindow_view.findViewById(R.id.tv_show_all_order);

        showSuccessOrder = (TextView) popupWindow_view.findViewById(R.id.tv_show_success_order);

        showRefundOrder = (TextView) popupWindow_view.findViewById(R.id.tv_show_refund_order);
        // 设置点击事件
        showAllOrder.setOnClickListener(this);

        showSuccessOrder.setOnClickListener(this);

        showRefundOrder.setOnClickListener(this);

    }


    //注册广播
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();

        myIntentFilter.addAction(ACTION_NAME);

        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    //推送刷新数据
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                page = 1;

                viewModel.requireDataFromWeb(statuss, page, rows);
            }
        }
    };

    //获取订单数据
    public void getData(Object t) {

        switch (statuss) {
            //全部订单
            case -1:
                mList.addAll((Collection<? extends OrderRecord>) t);

                mListAdapter.notifyDataSetChanged();

                payRecordListView.onRefreshComplete();

                break;
            //交易成功订单
            case 1:
                mListSuccess.addAll((Collection<? extends OrderRecord>) t);

                mListSuccessAdapter.notifyDataSetChanged();

                payRecordListView.onRefreshComplete();

                break;
            //显示退款订单
            case 5:
                mListCancle.addAll((Collection<? extends OrderRecord>) t);

                mListCancleAdapter.notifyDataSetChanged();

                payRecordListView.onRefreshComplete();

                break;
        }
    }

    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }

    @Override
    protected void onDestroy() {
        MyApplication.flag_pay = true;

        super.onDestroy();

        editor.putInt("message_count", 0);

        editor.commit();

        unregisterReceiver(mBroadcastReceiver);
    }


}

package com.HyKj.UKeBao.view.activity.businessManage.giveIntegral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.bean.CashRecordInfo;
import com.HyKj.UKeBao.model.businessManage.bean.IntegralRecordInfo;
import com.HyKj.UKeBao.model.businessManage.giveIntegral.IntegralRecordModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.businessManage.CashRecordAdapter;
import com.HyKj.UKeBao.view.adapter.businessManage.IntegralRecordAdapter;
import com.HyKj.UKeBao.viewModel.businessManage.giveIntegral.IntegralRecordViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * 积分账户记录
 * Created by Administrator on 2016/9/27.
 */
public class IntegralRecordActivity extends BaseActiviy{
    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, IntegralRecordActivity.class);

        return intent;
    }

    private final String TAG = "IntegralRecordActivity";

    private ImageButton backImageButton;
    /**
     * 记录标题
     */
    private TextView titleBarName;
    /**
     * 记录数据单位：金额（元）
     */
    private TextView numberRecodeTitle;
    /**
     * 数据记录listview
     */
    private ListView mListView;
    /**
     * 记录数据集合
     */
    private List<IntegralRecordInfo> integralRecordList;

    private List<CashRecordInfo> cashRecordList;

    private LinearLayout ll_loadingTips;// 加载提示

    private SharedPreferences sp;

    private int businessStoreId;

    private IntegralRecordAdapter mAdapter;

    private CashRecordAdapter cash_mAdapter;

    private int page = 1;// 加载的页数

    private int rows = 40;// 加载的行数

    private int total;// 记录总数

    private PullToRefreshListView refreshListView;

    private String type;//筛选交易状态（true:筛选收入类型，false:筛选支出类型，不传：不筛选）

    private String isSend;//筛选赠送积分记录（true:筛选，false或不传:不筛选）

    private IntegralRecordViewModel viewModel;

    private boolean isCashRecord;//判断跳转页面是否为现金记录的标记

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_integral_record);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        numberRecodeTitle = (TextView) findViewById(R.id.tv_number_record_title);

        refreshListView = (PullToRefreshListView) findViewById(R.id.lv_data_record);

        ll_loadingTips = (LinearLayout) findViewById(R.id.ll_loadingTips);

        mListView = refreshListView.getRefreshableView();

        refreshListView.setMode(PullToRefreshBase.Mode.BOTH);//两端加载

        viewModel = new IntegralRecordViewModel(new IntegralRecordModel(), this);
    }

    @Override
    public void setUpView() {
        sp = getSharedPreferences("user_login", MODE_PRIVATE);

        isSend = getIntent().getStringExtra("isSend");

        type = getIntent().getStringExtra("type");

        isCashRecord = getIntent().getBooleanExtra("isCashRecord", false);

        businessStoreId = Integer.parseInt(sp.getString("businessStoreId", "-1"));

        if (!isCashRecord) {

            integralRecordList = new ArrayList<IntegralRecordInfo>();

            setTitleTheme("积分记录");

            numberRecodeTitle.setText("金额（积分）");

            BufferCircleDialog.show(this, "正在获取数据...", false, null);

            viewModel.getRecordData(page, rows, businessStoreId, type, isSend);
            // 填充数据
            mAdapter = new IntegralRecordAdapter(this, integralRecordList);

            mListView.setAdapter(mAdapter);
        } else {
            setTitleTheme("现金记录");

            numberRecodeTitle.setText("金额（元）");

            cashRecordList = new ArrayList<CashRecordInfo>();

            BufferCircleDialog.show(this, "正在获取数据...", false, null);

            viewModel.getCashRecordData(businessStoreId, page, rows, isCashRecord);

            // 填充数据
            cash_mAdapter = new CashRecordAdapter(this, cashRecordList);

            mListView.setAdapter(cash_mAdapter);
        }
    }

    @Override
    public void setListeners() {
        //上拉刷新，下拉加载
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                if (refreshView.isHeaderShown()) {
                    page = 1;
                    if (!isCashRecord) {
                        integralRecordList.clear();
                        viewModel.getRecordData(page, rows, businessStoreId, type, isSend);
                    } else {
                        cashRecordList.clear();
                        viewModel.getCashRecordData(businessStoreId, page, rows, isCashRecord);
                    }

                } else if (refreshView.isFooterShown()) {
                    page++;
                    if (!isCashRecord) {
                        viewModel.getRecordData(page, rows, businessStoreId, type, isSend);
                    } else {
                        viewModel.getCashRecordData(businessStoreId, page, rows, isCashRecord);
                    }
                }
            }
        });
    }
    @Override
    public void toast(String msg, Context context) {
        super.toast(msg, context);
    }

    public void getRecordData(List<IntegralRecordInfo> mList) {
        if (mList != null) {
            integralRecordList.addAll(mList);

            mAdapter.notifyDataSetChanged();

            refreshListView.onRefreshComplete();
        }
        BufferCircleDialog.dialogcancel();

        ll_loadingTips.setVisibility(View.GONE);
    }

    public void getCashRecord(List<CashRecordInfo> mList) {
        if (mList != null) {
            cashRecordList.addAll(mList);

            cash_mAdapter.notifyDataSetChanged();

            refreshListView.onRefreshComplete();
        }
        BufferCircleDialog.dialogcancel();

        ll_loadingTips.setVisibility(View.GONE);
    }
}

package com.HyKj.UKeBao.view.activity.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.userInfoManage.WithdrawalsRecordModel;
import com.HyKj.UKeBao.model.userInfoManage.bean.WithdrawalsRecord;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.userInfoManage.WithdrawalsRecordAdapter;
import com.HyKj.UKeBao.viewModel.userInfoManage.WithdrawalsRecordViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换记录
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsRecordActivity extends BaseActiviy {
    private PullToRefreshListView mListView;

    private ListView listView;

    private WithdrawalsRecordViewModel viewModel;

    private WithdrawalsRecordAdapter mAdapter;

    private int page = 1;

    private int rows = 20;

    private String businessStoreId;

    private SharedPreferences sp;

    private List<WithdrawalsRecord> withdrawalsRecord_list = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, WithdrawalsRecordActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_withdrawals_record);

        mListView = (PullToRefreshListView) findViewById(R.id.lv_withdrawals_record);

        initData();
    }

    //初始化数据
    private void initData() {
        viewModel = new WithdrawalsRecordViewModel(new WithdrawalsRecordModel(), this);

        listView = mListView.getRefreshableView();

        mListView.setMode(PullToRefreshBase.Mode.BOTH);

        sp = getSharedPreferences("user_login", MODE_PRIVATE);

        businessStoreId = sp.getString("businessStoreId", "");

        BufferCircleDialog.show(this, "正在获取数据，请稍候..", false, null);

        viewModel.getWithdrawlsRecord(businessStoreId, page, rows);

        mAdapter = new WithdrawalsRecordAdapter(this, withdrawalsRecord_list);

        listView.setAdapter(mAdapter);
    }

    @Override
    public void setUpView() {
        setTitleTheme("提现记录");
    }

    @Override
    public void setListeners() {
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    page = 1;

                    withdrawalsRecord_list.clear();

                    viewModel.getWithdrawlsRecord(businessStoreId, page, rows);
                } else if (refreshView.isFooterShown()) {
                    page++;

                    viewModel.getWithdrawlsRecord(businessStoreId, page, rows);
                }
            }
        });
    }

    //获取记录数据
    public void setRecordData(List<WithdrawalsRecord> list) {
        LogUtil.d("设置记录数据成功,提现记录数为"+list.size());

        if (list.size()==0||(list.size() != 0 && withdrawalsRecord_list.size() == list.get(0).getTotal())) {
            mListView.onRefreshComplete();

            toast("没有更多的数据啦~", this);
        } else {
            withdrawalsRecord_list.addAll(list);

            mListView.onRefreshComplete();

            mAdapter.notifyDataSetChanged();
        }
    }
}

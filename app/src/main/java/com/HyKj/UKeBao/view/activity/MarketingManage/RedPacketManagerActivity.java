package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityMarketingRedmanagerBinding;
import com.HyKj.UKeBao.model.marketingManage.RedPacketManagerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketListInfo;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.RedPacketManageAdapter;
import com.HyKj.UKeBao.viewModel.marketingManage.RedPacketManagerViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包管理
 * Created by Administrator on 2016/10/29.
 */
public class RedPacketManagerActivity extends BaseActiviy {
    private ActivityMarketingRedmanagerBinding mBinding;

    private ListView mListView;

    private RedPacketManagerViewModel viewModel;

    private int page = 1;

    private int rows = 10;

    private RedPacketManageAdapter adapter;

    private SharedPreferences sp;

    private List<RedPacketListInfo> redPacketList = new ArrayList<>();//红包记录集合

    private int businessStoreId;//店铺id

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, RedPacketManagerActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_marketing_redmanager);

        viewModel = new RedPacketManagerViewModel(new RedPacketManagerModel(), this);

        sp = getSharedPreferences("user_login", MODE_PRIVATE);

        businessStoreId = Integer.valueOf(sp.getString("businessStoreId", ""));

        viewModel.getAllRedPacketInfo(page, rows, businessStoreId);//获取全部卡劵信息

        mBinding.lvRedpacketManager.setMode(PullToRefreshBase.Mode.BOTH);

        mListView = mBinding.lvRedpacketManager.getRefreshableView();

        adapter = new RedPacketManageAdapter(this, redPacketList);

        mListView.setAdapter(adapter);

        mBinding.setViewModel(viewModel);
    }

    @Override
    public void setUpView() {
        setTitleTheme("红包记录");
    }

    @Override
    public void setListeners() {
        mBinding.lvRedpacketManager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshView.isHeaderShown()){
                    page=1;

                    redPacketList.clear();

                    viewModel.getAllRedPacketInfo(page,rows,businessStoreId);
                }else if(refreshView.isFooterShown()){
                    page++;

                    viewModel.getAllRedPacketInfo(page,rows,businessStoreId);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=RedPacketDetailActivity.getStartIntent(RedPacketManagerActivity.this);

                intent.putExtra("id",redPacketList.get(position-1).getId());

                startActivity(intent);

                BufferCircleDialog.show(RedPacketManagerActivity.this,"努力加载中,请稍候...",false,null);
            }
        });
    }

    public void setData(List<RedPacketListInfo> list) {
        if (list.get(0).getTotal() == list.size() && list.size() != 0) {
            mBinding.lvRedpacketManager.onRefreshComplete();

            toast("没有更多数据啦!~", this);
        } else {
            redPacketList.addAll(list);

            mBinding.lvRedpacketManager.onRefreshComplete();

            adapter.notifyDataSetChanged();

            BufferCircleDialog.dialogcancel();
        }
    }
}

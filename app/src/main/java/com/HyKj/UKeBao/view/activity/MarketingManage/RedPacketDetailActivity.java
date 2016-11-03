package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityRedpacketDetailBinding;
import com.HyKj.UKeBao.model.marketingManage.RedPacketDetailModel;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetailInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacket_collect_record;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.RedPacketDetailActivityAdapter;
import com.HyKj.UKeBao.viewModel.marketingManage.RedPacketDetailViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 * 红包详情
 * Created by Administrator on 2016/10/19.
 */
public class RedPacketDetailActivity extends BaseActiviy {

    private ActivityRedpacketDetailBinding mBinding;

    private RedPacketDetailViewModel viewModel;

    private ListView mListView;

    private int page = 1;

    private int rows = 10;

    private int id;

    private List<RedPacketDetailInfo> list = new ArrayList<RedPacketDetailInfo>();

    private RedPacketDetailActivityAdapter adapter;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, RedPacketDetailActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_redpacket_detail);

        viewModel = new RedPacketDetailViewModel(new RedPacketDetailModel(), this);

        id = getIntent().getIntExtra("id", 0);

        viewModel.getSingRedPacketDetail(id);//获取单个红包详情信息

        mBinding.lvRedPacketDetail.setMode(PullToRefreshBase.Mode.BOTH);

        mListView = mBinding.lvRedPacketDetail.getRefreshableView();

        viewModel.collect_records(id, page, rows);//获取领取记录

        mBinding.setViewModel(viewModel);

        adapter = new RedPacketDetailActivityAdapter(this, list);

        mListView.setAdapter(adapter);
    }

    @Override
    public void setUpView() {
        setTitleTheme("红包详情");
    }

    @Override
    public void setListeners() {
        mBinding.lvRedPacketDetail.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    page = 1;

                    list.clear();

                    viewModel.collect_records(id, page, rows);
                } else if (refreshView.isFooterShown()) {
                    page++;

                    viewModel.collect_records(id, page, rows);
                }
            }
        });
    }

    //设置数据到适配器上
    public void setDataList(RedPacket_collect_record recordList) {
        if (list.size() == recordList.getTotal() && list.size() != 0) {
            toast("没有数据啦~", this);

            mBinding.lvRedPacketDetail.onRefreshComplete();
        } else {
            list.addAll(recordList.getRows());

            mBinding.lvRedPacketDetail.onRefreshComplete();

            adapter.notifyDataSetChanged();

            BufferCircleDialog.dialogcancel();
        }
    }
}

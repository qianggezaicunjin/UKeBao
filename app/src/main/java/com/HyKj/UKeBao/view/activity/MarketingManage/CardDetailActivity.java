package com.HyKj.UKeBao.view.activity.MarketingManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityCardDetailBinding;
import com.HyKj.UKeBao.model.marketingManage.LanFragmentModel;
import com.HyKj.UKeBao.model.marketingManage.bean.MemberCardInfo;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.CardDetailAdapter;
import com.HyKj.UKeBao.viewModel.marketingManage.LanFragmentViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 卡劵详情
 * Created by Administrator on 2016/10/19.
 */
public class CardDetailActivity extends BaseActiviy {

    private ActivityCardDetailBinding mBinding;

    private LanFragmentViewModel viewModel;

    private ListView listView;

    private PullToRefreshListView mListView;

    private int id;

    private CardDetailAdapter adapter;

    private List<MemberCardInfo> datalist = new ArrayList<MemberCardInfo>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CardDetailActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        SystemBarUtil.changeColor(R.color.blue);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_card_detail);

        mBinding.lvCardDetail.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        listView=mBinding.lvCardDetail.getRefreshableView();

        id = Integer.valueOf(getIntent().getStringExtra("id"));

        viewModel = new LanFragmentViewModel(new LanFragmentModel(), this);

        mBinding.setViewModel(viewModel);

        viewModel.getSingCardDetail(id, false);

    }

    @Override
    public void setUpView() {

    }

    @Override
    public void setListeners() {
        //设置退出监听
        mBinding.imbTitleBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        //下拉刷新
        mBinding.lvCardDetail.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if (refreshView.isHeaderShown()) {
                    //重新加载数据
                    datalist.clear();

                    viewModel.getSingCardDetail(id, false);
                }
            }
        });
    }

    public void setDataList(List<MemberCardInfo> list) {
        datalist = list;

        adapter = new CardDetailAdapter(this, datalist);

        listView.setAdapter(adapter);

        mBinding.lvCardDetail.onRefreshComplete();

        adapter.notifyDataSetChanged();
    }
}

package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityCardDetailBinding;
import com.HyKj.UKeBao.model.marketingManage.LanFragmentModel;
import com.HyKj.UKeBao.model.marketingManage.bean.MemberCardInfo;
import com.HyKj.UKeBao.util.LogUtil;
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

    private int rows = 10;//条目显示数

    private int position = 0;

    private List<MemberCardInfo> datalist = new ArrayList<MemberCardInfo>();

    private List<MemberCardInfo> temporary_list = new ArrayList<>();

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, CardDetailActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        SystemBarUtil.changeColor(R.color.blue);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_card_detail);

        mBinding.lvCardDetail.setMode(PullToRefreshBase.Mode.BOTH);

        listView = mBinding.lvCardDetail.getRefreshableView();

        id = getIntent().getIntExtra("id", 0);

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
                    temporary_list.clear();

                    viewModel.getSingCardDetail(id, false);

                    position=0;
                } else if (refreshView.isFooterShown()) {
                    if (datalist.size() <= position) {

                        mBinding.lvCardDetail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBinding.lvCardDetail.onRefreshComplete();
                            }
                        },1000);

                        toast("没有更多数据啦~", CardDetailActivity.this);
                    } else {
                        temporary_list.addAll(viewModel.getDisplayNum(position, rows));

                        position += rows;

                        mBinding.lvCardDetail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mBinding.lvCardDetail.onRefreshComplete();

                                adapter.notifyDataSetChanged();
                            }
                        },1000);

                    }
                }
            }
        });
    }


    public void setDataList(List<MemberCardInfo> list) {
        datalist = list;

        temporary_list.addAll(viewModel.getDisplayNum(0, rows));

        adapter = new CardDetailAdapter(this, temporary_list);

        position += rows;

        listView.setAdapter(adapter);

        mBinding.lvCardDetail.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBinding.lvCardDetail.onRefreshComplete();
            }
        },1000);

        adapter.notifyDataSetChanged();

    }

}

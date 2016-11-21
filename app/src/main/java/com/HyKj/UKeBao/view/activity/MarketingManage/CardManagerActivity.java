package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityMarketingCardmanagerBinding;
import com.HyKj.UKeBao.model.marketingManage.CardManagerModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardListInfo;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.CardListAdapter;
import com.HyKj.UKeBao.viewModel.marketingManage.CardManagerViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

/**
 *  卡劵管理
 * Created by Administrator on 2016/10/28.
 */
public class CardManagerActivity extends BaseActiviy{

    private ActivityMarketingCardmanagerBinding mBinding;

    private ListView mListView;

    private CardManagerViewModel viewModel;

    private int page=1;

    private int rows=20;

    private List<CardListInfo> cardListInfo=new ArrayList<>();//卡劵列表数据

    private CardListAdapter adapter;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,CardManagerActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        BufferCircleDialog.dialogcancel();

        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_marketing_cardmanager);

        viewModel=new CardManagerViewModel(new CardManagerModel(),this);

        viewModel.getAllCardInfo(page,rows);//获取全部卡劵信息

        mBinding.lvCardManager.setMode(PullToRefreshBase.Mode.BOTH);

        mListView=mBinding.lvCardManager.getRefreshableView();

        adapter=new CardListAdapter(this,cardListInfo);

        mListView.setAdapter(adapter);

        mBinding.setViewModel(viewModel);
    }

    @Override
    public void setUpView() {
        setTitleTheme("卡劵管理");
    }

    @Override
    public void setListeners() {
        mBinding.lvCardManager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(refreshView.isHeaderShown()){
                    page=1;

                    cardListInfo.clear();

                    viewModel.getAllCardInfo(page,rows);
                }else if(refreshView.isFooterShown()){
                    page++;

                    viewModel.getAllCardInfo(page,rows);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent=CardDetailActivity.getStartIntent(CardManagerActivity.this);

                intent.putExtra("id",cardListInfo.get(position-1).getId());

                startActivity(intent);

            }
        });
    }

    //给适配器设置数据源
    public void setData(List<CardListInfo> cardListInfos) {
        LogUtil.d("设置记录数据成功,卡劵记录数为"+cardListInfos.size());

        if(cardListInfos.size()==0||(cardListInfos.size()!=0&&cardListInfos.get(0).getTotal()==cardListInfo.size())) {
            mBinding.lvCardManager.onRefreshComplete();

            toast("没有更多数据啦!~",this);
        }else {
            cardListInfo.addAll(cardListInfos);

            mBinding.lvCardManager.onRefreshComplete();

            adapter.notifyDataSetChanged();

            BufferCircleDialog.dialogcancel();
        }
    }
}

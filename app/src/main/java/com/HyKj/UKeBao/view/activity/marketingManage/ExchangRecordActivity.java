package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.ExchangRecordModel;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.model.marketingManage.bean.Rows;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.ExchangeRecordAdapter;
import com.HyKj.UKeBao.viewModel.ExchangRecordViewModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 *  兑换记录
 * Created by Administrator on 2016/11/2.
 */
public class ExchangRecordActivity extends BaseActiviy implements AdapterView.OnItemClickListener{
    private Context mContext;

    private ImageButton backImageButton;

    private int positonLast;
    /**
     * 标题
     */
    private TextView titleBarName;
    /**
     * 兑换记录(控件)
     */
    private ListView exchangeRecordListView;

    private PullToRefreshListView listview;
    /**
     * 兑换记录(集合数据)
     */
    private List<Rows> rowsList = new ArrayList<Rows>();

    private List<Rows> emptyList = new ArrayList<Rows>();

    private SharedPreferences sharedPreferences;

    private SharedPreferences.Editor editor;

    private String businessStoreIdValue = "";

    private List<ProductLists> product_list = new ArrayList<>();

    /**
     * page(页数)
     */
    private int page = 1;

    private int rows=20;

    private ExchangeRecordAdapter mAdapter;

    private ExchangRecordViewModel viewModel;

    private String businessStoreId;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,ExchangRecordActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mContext = this;

        setContentView(R.layout.activity_exchange_testing_record);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        listview = (PullToRefreshListView) findViewById(R.id.lv_exchange_record);

        initData();//初始化数据
    }

    private void initData() {
        exchangeRecordListView = listview.getRefreshableView();

        listview.setMode(PullToRefreshBase.Mode.BOTH);

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        mAdapter = new ExchangeRecordAdapter(mContext, rowsList);

        exchangeRecordListView.setAdapter(mAdapter);

        businessStoreId=sharedPreferences.getString("businessStoreId", businessStoreIdValue);

        viewModel=new ExchangRecordViewModel(this,new ExchangRecordModel());

        BufferCircleDialog.show(this,"正在获取记录..请稍候~",true,null);

        viewModel.getExchangRecord(page,rows,Integer.valueOf(businessStoreId));
    }

    @Override
    public void setUpView() {
        setTitleTheme("兑换记录");


    }

    @Override
    public void setListeners() {
        exchangeRecordListView.setOnItemClickListener(this);

        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // TODO Auto-generated method stub
                if (refreshView.isHeaderShown()) {
                    page = 1;

                    rowsList.clear();

                    viewModel.getExchangRecord(page,rows,Integer.valueOf(businessStoreId));

                } else {
                    page++;

                    viewModel.getExchangRecord(page,rows,Integer.valueOf(businessStoreId));
                }
            }
        });
    }

    public void setExchangRecordData(List<Rows> recordList){
        rowsList.addAll(recordList);

        mAdapter.notifyDataSetChanged();

        listview.onRefreshComplete();

        if(BufferCircleDialog.isShowDialog()){
            BufferCircleDialog.dialogcancel();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Rows aa = rowsList.get(position - 1);

        Intent intent = new Intent();

        intent.setClass(this, ExchangeDetailActivity.class);

        Bundle bundle = new Bundle();

        bundle.putSerializable("row", aa);

        intent.putExtras(bundle);

        this.startActivity(intent);
    }
}

package com.HyKj.UKeBao.view.activity.marketingManage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.model.marketingManage.bean.Rows;
import com.HyKj.UKeBao.util.SetSizeUtils;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.ExchangeDetailAdapters;
import com.HyKj.UKeBao.view.adapter.MarketingManage.ExchangeInfoAdapters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class ExchangeDetailActivity extends BaseActiviy{
    private Context mContext;

    private ImageButton backImageButton;
    /**
     * 标题
     */
    private TextView titleBarName;
    /**
     * 兑换验证码（从兑换验证页面获取，点击查询跳转）
     */
    private String exchangeCode;
    /**
     * 订单号（从兑换记录页面获取，点击记录item跳转）
     */
    private String orderNumber;
    /**
     * 订单类型(rl)
     */
    private LinearLayout orderTypeRelativeLayout;
    /**
     * 订单金额(rl)
     */
    private LinearLayout orderMoneyRelativeLayout;
    /**
     * 订单类型(tv)
     */
    private TextView orderType;
    /**
     * 订单金额(tv)
     */
    private TextView orderMoney;
    /**
     * 订单编号
     */
    private TextView orderNumberTextView;
    /**
     * 实结合计
     */
    private TextView reallyTotalMoney;
    /**
     * 确认收款
     */
    private Button confirmCollectButton;
    /**
     * 验证成功关闭按钮
     */
    private Button closeButton;
    /**
     * 验证成功对话框
     */
    private AlertDialog testingSuccessDialog;

    private Rows rows;

    private List<ProductLists> list = new ArrayList<ProductLists>();

    private ListView goodsList;

    private ExchangeDetailAdapters adapter;

    private TextView infactPrice;

    private TextView tv_exchangRecord_no;

    private LinearLayout linerout;

    @Override
    public void onCreateBinding() {
        mContext = this;

        Intent intent = this.getIntent();

        rows = (Rows) intent.getSerializableExtra("row");

        setContentView(R.layout.exchangdetail_activity);

        SystemBarUtil.initSystemBar(this);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        goodsList = (ListView) findViewById(R.id.listView_ExchangeDetailActivity);

        infactPrice = (TextView) findViewById(R.id.infactPrice_listView_exchangDetail_Activity);

        linerout = (LinearLayout) findViewById(R.id.linerout_itemListView_ExhangeDetailActivity);

        tv_exchangRecord_no= (TextView) findViewById(R.id.tv_exchangRecord_no);

        SetSizeUtils.setSizeOnlyHeight(mContext, linerout, 13, 0);

        initData();
    }

    private void initData() {
        if(rows==null){
            return ;
        }
        Double x = 0.00;
        for (int i = 0; i < rows.getProductLists().size(); i++) {
            x = x
                    + Double.valueOf(rows.getProductLists().get(i)
                    .getRealPrice())
                    * rows.getProductLists().get(i).getCount();
        }
        adapter = new ExchangeDetailAdapters(this, rows.getProductLists(), x);

        list = rows.getProductLists();

        infactPrice.setText("￥"+rows.getAllRealPrice()+"元");

        tv_exchangRecord_no.setText("订单号:"+rows.getNo());
    }

    @Override
    public void setUpView() {
        setTitleTheme("兑换详情");
    }

    @Override
    public void setListeners() {
        goodsList.setAdapter(adapter);
    }
}

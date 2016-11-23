package com.HyKj.UKeBao.view.activity.marketingManage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.ExchangDetailFromSearchModel;
import com.HyKj.UKeBao.model.marketingManage.bean.ExchangeInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.MarketingManage.ExchangeInfoAdapters;
import com.HyKj.UKeBao.viewModel.marketingManage.ExchangDetailFromSearchViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 兑换详情
 * Created by Administrator on 2016/11/1.
 */
public class ExchangDetaliFromSearchActivity extends BaseActiviy implements View.OnClickListener{

    private TextView scoreExchange;

    private TextView orderPrice;

    private ListView listView;

    private int code;

    private List<ProductLists> product_list = new ArrayList<ProductLists>();

    private LinearLayout linearLayout_Frist;

    private LinearLayout linearLayout_Second;

    private View view_thrid;

    private TextView noData_TextView;

    private Button certainToPay_;

    private TextView exchang_no;

    private TextView tv_title_bar_name;

    private ImageButton imb_title_bar_back;

    private View view_fourth;

    private AlertDialog yanZheng;

    private Button btn_confirm;

    private TextView tv_confirm_exit;

    protected double x;

    private Context mContext;

    private ExchangDetailFromSearchViewModel viewModel;
    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,ExchangDetaliFromSearchActivity.class);

        return intent;
    }


    @Override
    public void onCreateBinding() {
        SystemBarUtil.initSystemBar(this);

        mContext = this;

        setContentView(R.layout.exchangedetail_first_activity);

        scoreExchange = (TextView) findViewById(R.id.scoreExchagne_ExchangDetailFromSearchActivity);//积分兑换

        noData_TextView = (TextView) findViewById(R.id.noData_TextView);

        orderPrice = (TextView) findViewById(R.id.orderPrice_ExchangDetailFromSearchActivity);//金额

        exchang_no= (TextView) findViewById(R.id.tv_exchangInfo_no);//订单号

        listView = (ListView) findViewById(R.id.listView_ExchangDetailFromSearchActivity);

        linearLayout_Frist = (LinearLayout) findViewById(R.id.linearLayout_Frist);

        linearLayout_Second = (LinearLayout) findViewById(R.id.linearLayout_Second);

        view_thrid = (View) findViewById(R.id.view_thrid);

        view_fourth = (View) findViewById(R.id.view_fourth);

        View footerView = getLayoutInflater().inflate(R.layout.footview_listview, null);

        certainToPay_ = (Button) footerView.findViewById(R.id.certainToPay_);//确认收款

        listView.addFooterView(footerView);

        viewModel=new ExchangDetailFromSearchViewModel(new ExchangDetailFromSearchModel(),this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("兑换详情");

        Intent intent = getIntent();

        String codeString = intent.getStringExtra("exchange_code");

        code = Integer.parseInt(codeString);

        viewModel.getCodeInfo(code);
    }

    @Override
    public void setListeners() {
        certainToPay_.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.certainToPay_:
                viewModel.confirmReceipt(code);

                break;
            case R.id.close_dialog:
                yanZheng.dismiss();

                finish();

                break;
        }
    }


    //设置兑换页面数据
    public void setExchangInfo(ExchangeInfo info) {
        product_list=info.getProductList();

        exchang_no.setVisibility(View.VISIBLE);

        linearLayout_Frist.setVisibility(View.VISIBLE);

        linearLayout_Second.setVisibility(View.VISIBLE);

        view_thrid.setVisibility(View.VISIBLE);

        view_fourth.setVisibility(View.VISIBLE);

        noData_TextView.setVisibility(View.INVISIBLE);

        exchang_no.setText(info.getNo());

        if(info.getOrderType()!=-1&&info.getOrderType()==1){
            scoreExchange.setText("积分兑换");
        }else {
            scoreExchange.setText("其他");
        }
        orderPrice.setText(String.valueOf(info.getAllIntegral()/10)+"元");

        for (int i = 0; i < info.getProductList().size(); i++) {
            x = x+ Double.valueOf(product_list.get(i).getRealPrice()) * product_list.get(i).getProductAmount();
        }
        ExchangeInfoAdapters adapter = new ExchangeInfoAdapters(getApplicationContext(), product_list, x);

        if (product_list.size() > 0) {
            listView.setAdapter(adapter);
        } else {
            listView.setAdapter(null);

            toast("暂无订单或请求数据失败",getApplicationContext());

            linearLayout_Frist.setVisibility(View.INVISIBLE);

            linearLayout_Second.setVisibility(View.INVISIBLE);

            view_thrid.setVisibility(View.INVISIBLE);

            noData_TextView.setVisibility(View.VISIBLE);

            exchang_no.setVisibility(View.INVISIBLE);
        }
    }

    //确认收款
    public void confirmReceipt(String t) {
        toast(t,this);

        LogUtil.d("x的值等于"+x);

        if(x!=0.00){
            LogUtil.d("初始化Dialog");

            initUpdateDialog(String.valueOf(x));
        }
    }

    private void initUpdateDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogContentView = View.inflate(this, R.layout.yanzheng_dialog_excdefromsearch, null);

        btn_confirm = (Button)dialogContentView.findViewById(R.id.close_dialog);

        btn_confirm.setOnClickListener(this);

        tv_confirm_exit = (TextView) dialogContentView.findViewById(R.id.yanzheng_dialog);

        yanZheng = builder.create();

        yanZheng.setCancelable(false);

        yanZheng.setCanceledOnTouchOutside(false);

        yanZheng.show();

        // 设置dialog的大小
        // 将对话框的大小按屏幕大小的百分比设置
        Window dialogWindow = yanZheng.getWindow();

        dialogWindow.setContentView(dialogContentView);

        WindowManager windowManager = getWindowManager();

        Display display = windowManager.getDefaultDisplay(); // 获取屏幕

        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值

        lp.height = (int) (display.getHeight() * 0.6); // 高度设置为屏幕的0.6

        lp.width = (int) (display.getWidth() * 0.8); // 宽度设置为屏幕的0.95

        dialogWindow.setAttributes(lp);

        tv_confirm_exit.setText("本订单合计结算 : "+content+"元");
    }
}

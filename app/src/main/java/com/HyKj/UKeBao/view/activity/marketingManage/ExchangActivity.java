package com.HyKj.UKeBao.view.activity.marketingManage;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * Created by Administrator on 2016/11/2.
 */
public class ExchangActivity extends BaseActiviy implements View.OnClickListener{
    private TextView tv_title;//标题

    private ImageButton imb_back;//退出

    private Button bt_exchangRecord;//兑换记录

    private Button exchangeSearch;//（兑换）查询

    private EditText exchangeTestingInput;//验证码输入

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ExchangActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_exchange_testing);

        tv_title= (TextView) findViewById(R.id.tv_title_bar_name);

        imb_back= (ImageButton) findViewById(R.id.imb_title_bar_back);

        bt_exchangRecord= (Button) findViewById(R.id.btn_exchange_testing_record);

        exchangeSearch = (Button) findViewById(R.id.btn_exchange_search);

        exchangeTestingInput = (EditText) findViewById(R.id.et_exchange_testing_input);
    }

    @Override
    public void setUpView() {
        tv_title.setText("兑换验证");


    }

    @Override
    public void setListeners() {
        bt_exchangRecord.setOnClickListener(this);

        imb_back.setOnClickListener(this);

        exchangeSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //退出
            case R.id.imb_title_bar_back:
                finish();

                break;
            //验证
            case R.id.btn_exchange_search:
                Intent intent=ExchangDetaliFromSearchActivity.getStartIntent(this);

                intent.putExtra("exchange_code",exchangeTestingInput.getText().toString());

                startActivity(intent);

                break;
            //兑换记录
            case R.id.btn_exchange_testing_record:
                startActivity(ExchangRecordActivity.getStartIntent(this));

                break;
        }
    }
}

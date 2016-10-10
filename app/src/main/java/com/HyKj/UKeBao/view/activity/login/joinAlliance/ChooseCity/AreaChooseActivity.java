package com.HyKj.UKeBao.view.activity.login.joinAlliance.ChooseCity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.longin.ChooseProvinceAdapter;

import java.util.ArrayList;
import java.util.List;

public class AreaChooseActivity extends BaseActiviy implements android.view.View.OnClickListener, OnItemClickListener {

    private TextView titleBarName;

    private ImageButton backImageButton;

    private List<String> areaList = new ArrayList<String>();

    private ListView lv_areaChoose;

    private Context mContext;


    @Override
    public void onCreateBinding() {
        mContext = this;

        setContentView(R.layout.activity_choose_area);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        lv_areaChoose = (ListView) findViewById(R.id.lv_areaChoose);
    }

    @Override
    public void setUpView() {
        initData();
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);
        lv_areaChoose.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb_title_bar_back:
                this.finish();
                break;


            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String area = areaList.get(position);
        Intent intent = new Intent();
        intent.putExtra("area", area);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    public void initData() {
        titleBarName.setText("选择地区");
        Intent intent = getIntent();
        areaList = intent.getStringArrayListExtra("areaList");
        lv_areaChoose.setAdapter(new ChooseProvinceAdapter(mContext, areaList));
    }
}

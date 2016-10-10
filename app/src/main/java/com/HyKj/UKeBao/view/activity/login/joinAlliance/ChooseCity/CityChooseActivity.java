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
import com.HyKj.UKeBao.model.login.baen.City;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.longin.ChooseProvinceAdapter;

import java.util.ArrayList;
import java.util.List;

public class CityChooseActivity extends BaseActiviy implements android.view.View.OnClickListener, OnItemClickListener {

    private TextView titleBarName;

    private ImageButton backImageButton;

    private List<City> cityList;

    private List<String> cityNameList = new ArrayList<String>();

    private ListView lv_cityChoose;

    private Context mContext;

    private String provinceName;

    private String cityName;

    @Override
    public void onCreateBinding() {
        mContext = this;
        setContentView(R.layout.activity_choose_city);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        lv_cityChoose = (ListView) findViewById(R.id.lv_cityChoose);
    }

    @Override
    public void setUpView() {
        initData();
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);
        lv_cityChoose.setOnItemClickListener(this);

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
        City city = cityList.get(position);
        cityName = city.name;
        ArrayList<String> areaList = (ArrayList<String>) city.areaList;
        Intent intent = new Intent(mContext, AreaChooseActivity.class);
        intent.putStringArrayListExtra("areaList", areaList);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String area = data.getStringExtra("area");
            Intent intent = new Intent();
            intent.putExtra("area", area);
            intent.putExtra("cityName", cityName);
            setResult(RESULT_OK, intent);
            this.finish();
        }

    }
    @SuppressWarnings("unchecked")
    public void initData() {
        titleBarName.setText("选择城市");
        Intent intent = getIntent();
        cityList = (List<City>) intent.getSerializableExtra("cityList");
//		provinceName=intent.getStringExtra("provinceName");
        if (cityList != null) {
            for (int i = 0; i < cityList.size(); i++) {
                String name = cityList.get(i).name;
                cityNameList.add(name);
            }
        }
        lv_cityChoose.setAdapter(new ChooseProvinceAdapter(mContext, cityNameList));
    }
}

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
import com.HyKj.UKeBao.model.login.baen.Province;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.longin.ChooseProvinceAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProvinceChooseActivity extends BaseActiviy implements android.view.View.OnClickListener, OnItemClickListener {

    private TextView titleBarName;

    private ImageButton backImageButton;

    private List<Province> provinceList;

    private List<String> provinceNameList = new ArrayList<String>();

    private ListView lv_provinceChoose;

    private Context mContext;

    private String provinceName;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, ProvinceChooseActivity.class);

        return intent;
    }


    @Override
    public void onCreateBinding() {

        mContext = this;

        setContentView(R.layout.activity_choose_province);

        titleBarName = (TextView) findViewById(R.id.tv_title_bar_name);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        lv_provinceChoose = (ListView) findViewById(R.id.lv_provinceChoose);
    }

    @Override
    public void setUpView() {
        initData();
    }

    @Override
    public void setListeners() {

        backImageButton.setOnClickListener(this);

        lv_provinceChoose.setOnItemClickListener(this);

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
        List<City> cityList = provinceList.get(position).cityList;
        provinceName = provinceList.get(position).name;
        Intent intent = new Intent(mContext, CityChooseActivity.class);
        intent.putExtra("cityList", (Serializable) cityList);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String area = data.getStringExtra("area");
            String cityName = data.getStringExtra("cityName");
            Intent intent = new Intent();
            intent.putExtra("area", area);
            intent.putExtra("cityName", cityName);
            intent.putExtra("provinceName", provinceName);
            setResult(RESULT_OK, intent);
            this.finish();
        }

    }

    @SuppressWarnings("unchecked")
    public void initData() {
        titleBarName.setText("选择省份");
        provinceList = (List<Province>) getIntent().getSerializableExtra("provinceList");
        if (provinceList != null) {
            for (int i = 0; i < provinceList.size(); i++) {
                String name = provinceList.get(i).name;
                provinceNameList.add(name);
            }
        }
        lv_provinceChoose.setAdapter(new ChooseProvinceAdapter(mContext, provinceNameList));
    }
}

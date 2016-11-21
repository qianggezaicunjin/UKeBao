package com.HyKj.UKeBao.view.activity.login.joinAlliance.ChooseCity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.joinAlliance.ChooseCity.ChooseCityModel;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.login.joinAlliance.ChooseCity.ChooseCityViewModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ChooseCityActivity extends BaseActiviy implements View.OnClickListener {

    private LinearLayout ll_choose_province;

    private ImageButton backImageButton;

    private TextView tv_province;

    private EditText et_address_detail;

    private Button btn_confirmAddress;

    private ChooseCityViewModel viewModel;

    private String  provinceName;//省

    private String cityName;//市

    private String area;

    private String address;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, ChooseCityActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_choosecity);

        ll_choose_province = (LinearLayout) findViewById(R.id.ll_choose_province);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        btn_confirmAddress = (Button) findViewById(R.id.btn_confirmAddress);

        tv_province= (TextView) findViewById(R.id.tv_province);

        et_address_detail = (EditText) findViewById(R.id.et_address_detail);

        viewModel=new ChooseCityViewModel(new ChooseCityModel(),this);

        //获取地址信息
        getAddressInfo();
    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺地址");
    }

    @Override
    public void setListeners() {

        ll_choose_province.setOnClickListener(this);

        backImageButton.setOnClickListener(this);

        btn_confirmAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imb_title_bar_back:
                this.finish();
                break;

            case R.id.btn_confirmAddress:
                confirmAddress();//提交地址
                break;

            case R.id.ll_choose_province:
                openChooseProvince();//跳转到选择城市页面
                break;

            default:
                break;
        }
    }

    private void getAddressInfo() {
        Intent intent=getIntent();

        BusinessInfo businessInfo= (BusinessInfo) intent.getSerializableExtra("businessInfo_address");

        provinceName=businessInfo.province;

        cityName=businessInfo.city;

        area=businessInfo.area;

        if(provinceName!=null||cityName!=null||area!=null) {
            tv_province.setText(provinceName + cityName + area);

            et_address_detail.setText(businessInfo.address);
        }
    }

    //跳转到选择城市页面
    private void openChooseProvince() {
        viewModel.chooseCity();
    }

    public void chooseCity(){
        if(viewModel.provinceList!=null){
            Intent intent =ProvinceChooseActivity.getStartIntent(this);

            intent.putExtra("provinceList",(Serializable)viewModel.provinceList);

            startActivityForResult(intent, 1);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==RESULT_OK){
            cityName=data.getStringExtra("cityName");
            provinceName=data.getStringExtra("provinceName");
            area=data.getStringExtra("area");
            //过滤信息
            area=area.replaceAll("(县级市|地级市|、|)", "");
            cityName=cityName.replaceAll("(县级市|地级市|、|)", "");
            tv_province.setText(provinceName+cityName+area);
        }
    }

    //提交按钮执行的方法
    private void confirmAddress() {
        address=et_address_detail.getText().toString().trim();
        if(provinceName==null){
            Toast.makeText(this, "请选择省份", Toast.LENGTH_SHORT).show();
            return;
        }
        if(cityName==null){
            Toast.makeText(this, "请选择城市", Toast.LENGTH_SHORT).show();
            return;
        }
        if(address==null){
            Toast.makeText(this, "请输入详细地址", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent =new Intent();

        intent.putExtra("cityName",cityName);

        intent.putExtra("provinceName",provinceName);

        intent.putExtra("area",area);

        intent.putExtra("areaDetail",address);

        setResult(RESULT_OK,intent);

        finish();

    }
}

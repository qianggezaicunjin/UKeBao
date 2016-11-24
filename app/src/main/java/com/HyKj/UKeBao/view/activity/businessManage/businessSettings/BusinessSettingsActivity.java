package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessSettingsBinding;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessSettingsModel;
import com.HyKj.UKeBao.model.businessManage.businessSettings.bean.GoodsInfo;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.ChooseCity.ChooseCityActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreCoord.StoreCoordActivity;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessSettingsViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/8.
 */
public class BusinessSettingsActivity extends BaseActiviy implements View.OnClickListener{

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BusinessSettingsActivity.class);

        return intent;
    }

    private ActivityBusinessSettingsBinding mBinding;

    private BusinessSettingsViewModel viewModel;

    private BusinessInfo businessInfo;

    public final int RESULT_Settings_Success = 9;

    public final int RESULT_Settings_ChooseCity = 8;

    public final int RESULT_Settings_Coordinate =7 ;

    public final int RESULT_Settings_GoodsImage =6 ;

    private int page=1;//商品页

    private int rows=5;//商品数据

    @Override
    public void onCreateBinding() {

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_business_settings);

        BufferCircleDialog.show(this, "正在获取店铺数据...", false, null);

        viewModel = new BusinessSettingsViewModel(this, new BusinessSettingsModel());

        viewModel.getBusinessInfo();

        mBinding.setViewModel(viewModel);

    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺设置");
    }

    @Override
    public void setListeners() {
        mBinding.rlPhotoAlbumSetting.setOnClickListener(this);

        mBinding.rlStoreAddressSetting.setOnClickListener(this);

        mBinding.rlGeographyCoordinateSetting.setOnClickListener(this);

        mBinding.rlStoreGoodsImage.setOnClickListener(this);

        mBinding.btnStoreSettingSave.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //店铺照片
            case R.id.rl_photo_album_setting:
                Intent intent = BusinessStoreImageActivity.getStartIntent(BusinessSettingsActivity.this);

                LogUtil.d("点击了进入店铺相册页面，获取数据:" + businessInfo.getPictures());

                intent.putExtra("pictures", (Serializable) businessInfo.getPictures());

                startActivityForResult(intent, RESULT_Settings_Success);

                break;
            //店铺地址
            case R.id.rl_store_address_setting:
                Intent intent_chooseCity= ChooseCityActivity.getStartIntent(this);

                intent_chooseCity.putExtra("province",businessInfo.province);

                intent_chooseCity.putExtra("city",businessInfo.city);

                intent_chooseCity.putExtra("area",businessInfo.area);

                intent_chooseCity.putExtra("address",businessInfo.address);

                startActivityForResult(intent_chooseCity,RESULT_Settings_ChooseCity);

                break;
            //店铺坐标
            case R.id.rl_geography_coordinate_setting:
                Intent intent_coordinate= StoreCoordActivity.getStartIntent(this);

                intent_coordinate.putExtra("longitude",businessInfo.getLongitude());

                intent_coordinate.putExtra("latitude",businessInfo.getLatitude());

                startActivityForResult(intent_coordinate,RESULT_Settings_Coordinate);

                break;
            //商品相册
            case R.id.rl_store_goods_image:
                List<String> goodsImage_list=new ArrayList<>();

                List<String> goodImageName_list=new ArrayList<>();

                List<Integer> goodsImageid_list=new ArrayList<>();

                for(int i=0;i<businessInfo.getPiList().size();i++){
                    goodsImage_list.add(businessInfo.getPiList().get(i).getSrc());

                    goodImageName_list.add(businessInfo.getPiList().get(i).getName());

                    goodsImageid_list.add(businessInfo.getPiList().get(i).getId());
                }

                Intent goodsImage_intent=BusinessStoreGoodsActivity.getStartIntent(this);

                goodsImage_intent.putExtra("goods_list", (Serializable) goodsImage_list);

                goodsImage_intent.putExtra("goodImageName_list", (Serializable) goodImageName_list);

                goodsImage_intent.putExtra("goodsImageid_list", (Serializable) goodsImageid_list);

                startActivityForResult(goodsImage_intent,RESULT_Settings_GoodsImage);

                break;

            //保存提交
            case R.id.btn_store_setting_save:
                BufferCircleDialog.show(this,"正在提交~请稍候",false,null);

                businessInfo.setTel(mBinding.etStorePhoneNumberInput.getText().toString());

                businessInfo.setName(mBinding.etContactsNameInput.getText().toString());

                viewModel.commit(businessInfo);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //店铺照片
                case RESULT_Settings_Success:

                    LogUtil.d("BusinessSettings回调成功");

                    ArrayList<String> pictures = data.getStringArrayListExtra("pictures");

                    if (pictures != null) {
                        businessInfo.setPictures(pictures);

                        mBinding.tvPhotoAlbumSetting.setText("修改/已设置");
                    }

                    break;
                //店铺地址
                case RESULT_Settings_ChooseCity:

                    businessInfo.setProvince(data.getStringExtra("provinceName"));

                    businessInfo.setCity(data.getStringExtra("cityName"));

                    businessInfo.setArea(data.getStringExtra("area"));

                    businessInfo.setAddress(data.getStringExtra("areaDetail"));

                    mBinding.tvStoreAddress.setText("修改/已设置");

                    break;
                //店铺坐标
                case RESULT_Settings_Coordinate:

                    businessInfo.setLatitude(data.getDoubleExtra("latitude",-1));

                    businessInfo.setLongitude(data.getDoubleExtra("longitude",-1));

                    mBinding.tvGeographyCoordinate.setText("修改/已设置");

                    break;
                //商品相册
                case RESULT_Settings_GoodsImage:
                    List<GoodsInfo> goodsInfoList=new ArrayList<>();

                    ArrayList<String> updata_goodsImage = data.getStringArrayListExtra("updata_goodsImage");

                    ArrayList<String> updata_goodsName = data.getStringArrayListExtra("updata_goodsName");

                    ArrayList<Integer> updata_goodsId=data.getIntegerArrayListExtra("goodsImageid_list");

                    for(int i=0;i<updata_goodsImage.size();i++){
                        if(updata_goodsImage.get(i).equals("end")){
                            break;
                        }

                        try {
                            GoodsInfo goodsInfo = new GoodsInfo();

                            goodsInfo.setName(updata_goodsName.get(i));

                            goodsInfo.setSrc(updata_goodsImage.get(i));

                            goodsInfo.setId(updata_goodsId.get(i));

                            goodsInfoList.add(goodsInfo);
                        }catch (Exception e){
                            LogUtil.d("BusinessSettings"+e.toString());

                            GoodsInfo goodsInfo = new GoodsInfo();

                            goodsInfo.setName(updata_goodsName.get(i));

                            goodsInfo.setSrc(updata_goodsImage.get(i));

                            goodsInfoList.add(goodsInfo);
                        }

                    }
                    businessInfo.setPiList(goodsInfoList);

                    LogUtil.d("回调商品相册成功，返回数据为"+"goodsInfoList"+goodsInfoList);

                    mBinding.tvStoreGoodsImageSettings.setText("修改/已设置");

                    break;
            }
        }
    }
    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }


}

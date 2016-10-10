package com.HyKj.UKeBao.viewModel.login.joinAlliance.ChooseCity;


import com.HyKj.UKeBao.model.login.baen.Province;
import com.HyKj.UKeBao.model.login.joinAlliance.ChooseCity.ChooseCityModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.ChooseCity.ChooseCityActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ChooseCityViewModel extends BaseViewModel {

    public List<Province> provinceList=new ArrayList<>();

    private ChooseCityModel mModel;

    private ChooseCityActivity mActivity;

    public ChooseCityViewModel(ChooseCityModel model, ChooseCityActivity activity){
        mModel=model;

        mModel.setView(this);

        mActivity=activity;
    }

    //获取省份数据
    public void chooseCity() {
        mModel.chooseCity();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.Login_SettledAlliance_chooseCity){

            List<Province> mlist= (List<Province>) data.t;
            LogUtil.d("ChooseCityViewModel"+mlist.toString());
            provinceList.addAll(mlist);
            mActivity.chooseCity();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast(erroinfo);
    }
}

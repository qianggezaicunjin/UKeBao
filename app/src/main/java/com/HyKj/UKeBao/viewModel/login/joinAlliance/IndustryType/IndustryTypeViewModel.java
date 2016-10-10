package com.HyKj.UKeBao.viewModel.login.joinAlliance.IndustryType;


import com.HyKj.UKeBao.model.login.baen.ProductCategory;
import com.HyKj.UKeBao.model.login.joinAlliance.IndustryType.IndustryTypeModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.IndustryType.IndustryTypeActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/9/1.
 */
public class IndustryTypeViewModel extends BaseViewModel {

    private IndustryTypeModel mModel;

    public ProductCategory productCategory;

    private IndustryTypeActivity mActivity;
    public IndustryTypeViewModel(IndustryTypeModel model, IndustryTypeActivity activity) {
        mModel=model;
        mModel.setView(this);
        mActivity=activity;
    }

    public void getIndustryTypeData() {
        mModel.getIndustryTypeData();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action== Action.Login_SettledAlliance_industryTypeData){
            productCategory= (ProductCategory) data.t;
            LogUtil.d("IndustryTypeViewModel"+productCategory.getCategory().toString());
            mActivity.setAdapterData();
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mActivity.toast("网络连接失败");
    }
}

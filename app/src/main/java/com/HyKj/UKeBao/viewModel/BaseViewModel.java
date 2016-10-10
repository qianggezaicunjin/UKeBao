package com.HyKj.UKeBao.viewModel;

import android.databinding.BaseObservable;

import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.customView.RequestView;


/**
 * Created by Administrator on 2016/8/22.
 */
public class BaseViewModel extends BaseObservable implements RequestView<ModelAction> {
    @Override
    public void onRequestFinished() {

    }

    @Override
    public void onRequestSuccess(ModelAction data) {

    }


    @Override
    public void onRequestErroInfo(String erroinfo) {

    }
}

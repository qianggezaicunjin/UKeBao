package com.HyKj.UKeBao.view.fragment.userInfoManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.fragment.BaseFragment;


/**
 * Created by Administrator on 2016/9/21.
 */
public class LeftMenuFragment extends BaseFragment implements OnClickListener {
    private View contentView;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            contentView=inflater.inflate((R.layout.fragment_left_menu),container,false);
        }
        return contentView;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View v) {

    }
}

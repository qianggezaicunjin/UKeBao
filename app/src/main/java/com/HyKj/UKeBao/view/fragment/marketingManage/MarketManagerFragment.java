package com.HyKj.UKeBao.view.fragment.marketingManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;


/**
 * Created by Administrator on 2016/9/21.
 */
public class MarketManagerFragment extends BaseFragment implements View.OnClickListener{
    private View contentView;

    private MainFragmentListener imagListener;

    public MarketManagerFragment(){
        super();
    }

    public MarketManagerFragment(MainFragmentListener listener){
        imagListener=listener;
    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            contentView=inflater.inflate((R.layout.fragment_market_manager),container,false);
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

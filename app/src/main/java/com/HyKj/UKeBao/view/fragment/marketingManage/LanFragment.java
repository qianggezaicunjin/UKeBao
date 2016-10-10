package com.HyKj.UKeBao.view.fragment.marketingManage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;

/**
 * Created by Administrator on 2016/9/21.
 */
public class LanFragment extends BaseFragment implements View.OnClickListener,OnGetPoiSearchResultListener {
    private View contentView;

    private Context mContext;

    private MainFragmentListener imagListener;

    public LanFragment(){
        super();
    }

    public LanFragment(Context context,MainFragmentListener listener){
        imagListener=listener;
        mContext=context;
    }
    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            contentView=inflater.inflate((R.layout.fragment_lan),container,false);
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

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }
}

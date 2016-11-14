package com.HyKj.UKeBao.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.HyKj.UKeBao.model.marketingManage.bean.WXPayResult;

public abstract class BaseFragment extends Fragment {

    public Activity mContext;

    /**
     * fragment Initialization monitoring interface
     */
    public View layout_morefragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        findViews();
        initData();
        setListeners();
        initViews();
    }

    /**
     * 填充布局 order:----1----
     * Initialization debris control, add layout debris Execution
     */
    public abstract View inflateView(LayoutInflater inflater,
                                     ViewGroup container, Bundle savedInstanceState);

    /**
     * Execution order:----2----
     */
    public abstract void findViews();

    /**
     * Execution order:----3----
     */
    public abstract void initData();

    /**
     * Execution order:----4----
     */

    public abstract void setListeners();

    /**
     * Execution order:----5----
     */
    public abstract void initViews();

    public void toast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}

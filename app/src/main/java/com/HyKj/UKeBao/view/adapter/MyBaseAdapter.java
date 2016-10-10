package com.HyKj.UKeBao.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public abstract class MyBaseAdapter<E> extends BaseAdapter {

    protected Context mContext;
    protected LayoutInflater mInflater;
    protected List<E> dataList;

    public MyBaseAdapter(Context mContext, List<E> dataList) {
        initBaseData(mContext);
        this.dataList = dataList;
    }

    public MyBaseAdapter(Context mContext) {
        initBaseData(mContext);
    }

    private void initBaseData(Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            throw new RuntimeException("dataList fill data can not be null");
        } else {
            return dataList.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if (dataList == null) {
            throw new RuntimeException("dataList fill data and the datelist can not be null");
        } else {
            return dataList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

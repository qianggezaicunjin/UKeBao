package com.HyKj.UKeBao.model.bean;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 用于BindingAdapter中的girdview方法的数据存储
 * Created by Administrator on 2016/10/11.
 */
public class RecycleViewBaen {
    public int num;

    public Context mContext;

    public RecyclerView.Adapter adapter;

    public int mode;

    public RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String toString() {
        return "RecycleViewBaen{" +
                "mContext=" + mContext +
                ", num=" + num +
                ", mode='" + mode + '\'' +
                '}';
    }
}

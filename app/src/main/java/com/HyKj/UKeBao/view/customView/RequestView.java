package com.HyKj.UKeBao.view.customView;

/**
 * Created by Administrator on 2016/8/19.
 */
public interface RequestView<T> {
    void onRequestFinished();

    void onRequestSuccess(T data);


    void onRequestErroInfo(String erroinfo);
}

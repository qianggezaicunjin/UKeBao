package com.HyKj.UKeBao.viewModel.businessManage;


import com.HyKj.UKeBao.model.businessManage.StoreManagerFragmentModel;
import com.HyKj.UKeBao.model.businessManage.bean.NotifyInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.fragment.businessManage.StoreManagerFragment;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/9/24.
 */
public class StoreManagerFragmentViewModel extends BaseViewModel {

    public StoreManagerFragmentModel mModel;

    public StoreManagerFragment mFragment;

    public List<NotifyInfo> mNotifyInfo;

    public StoreManagerFragmentViewModel(StoreManagerFragmentModel model, StoreManagerFragment fragment) {
        mModel = model;

        mFragment = fragment;

        mModel.setView(this);
    }

    //获取系统公告信息
    public void getNoticeInfo() {
        mModel.getNoticeInfo();
    }

    //消息循环
    public void notifyRunning() {
        final List<String> mlist = new ArrayList<>(2);
        Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                LogUtil.d("进入设置消息循环方法");
                while (true) {
                    for (int i = 0; i < mNotifyInfo.size(); i++) {
                        if (i == mNotifyInfo.size() - 1) {
                            mlist.add(0, mNotifyInfo.get(i).title);
                            mlist.add(1, mNotifyInfo.get(0).title);
                        } else {
                            mlist.add(0, mNotifyInfo.get(i).title);
                            mlist.add(1, mNotifyInfo.get(i + 1).title);
                        }
                        try {
                            Thread.sleep(3000);
                            subscriber.onNext(mlist);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d("轮询公告栏消息错误" + e.toString());
                    }

                    @Override
                    public void onNext(List<String> list) {
                        mFragment.refreshNotice(list.get(0), list.get(1));
                    }
                });
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.Main_getNoticeInfo) {
            //获取系统公告，并放入数组中
            mNotifyInfo = (List<NotifyInfo>) data.t;

            mFragment.setNoticeInfo(mNotifyInfo);
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        mFragment.toast(erroinfo, mFragment.mContext);
    }


}

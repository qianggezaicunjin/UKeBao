package com.HyKj.UKeBao.viewModel;

import android.support.v4.widget.DrawerLayout;

import com.HyKj.UKeBao.model.MainModel;
import com.HyKj.UKeBao.model.bean.ToUpDateInfo;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.fragment.userInfoManage.LeftMenuFragment;


/**
 * Created by Administrator on 2016/9/21.
 */
public class MainViewModel extends BaseViewModel {
    private MainActivity mActivty;

    private MainModel mModel;

    private ToUpDateInfo toUpDateInfo;

    private int version;//版本号

    public boolean isOpen = false;

    private boolean clickOpen = false;

    public MainViewModel(MainActivity activity, MainModel model) {
        mModel = model;

        mActivty = activity;

        mModel.setView(this);
    }

    //网络获取当前版本号(version为当前版本号)
    public void whetheToUpDate(int version) {
        this.version = version;
        mModel.whetheToOpDate();
    }

    //判断是否打开或者关闭侧边栏
    public boolean isOpenleft(LeftMenuFragment leftFragment, int arg0) {
        if (!isOpen) {
            if (arg0 == DrawerLayout.STATE_DRAGGING) {
                if (!clickOpen) {
                    if (leftFragment != null) {
//                                leftFragment.reflishData();
                    }
                }
                clickOpen = false;
            }
        }
        return clickOpen;
    }

    //判断是否是vip
    public void isVip() {
        mModel.isVip();
    }

    //申请vip
    public void applyVip() {
        mModel.applyVip();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if (data.action == Action.Main_getVersion_num) {
            toUpDateInfo = (ToUpDateInfo) data.t;

            //当状态为0时，请求成功拿到版本号
            if (toUpDateInfo.getStatus().equals("0")) {
                int new_version = Integer.valueOf(toUpDateInfo.getRows().getVersion().replace(".", ""));

                if (new_version > version) {

                    LogUtil.d("当前本地版本号为" + version);

                    LogUtil.d("拿到url更新地址" + toUpDateInfo.getRows().getUrl());

                    mActivty.initUpdateDialog("发现新版本！", toUpDateInfo.getRows().getUrl());
                }
            } else {
                mActivty.toast("未知错误!");
            }
        } else if (data.action == Action.MarketingManage_isVip) {

            mActivty.isVip((int) data.t);
        } else if (data.action == Action.MarketingManage_ApplyVip) {
            BufferCircleDialog.dialogcancel();

            mActivty.setVipPayInfo((int) data.t);

        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {
        if (BufferCircleDialog.isShowDialog()) {

            BufferCircleDialog.dialogcancel();

        }
        mActivty.toast(erroinfo, mActivty);
    }


}

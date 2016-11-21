package com.HyKj.UKeBao.viewModel.login.examine;


import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.model.bean.BaseInfo;
import com.HyKj.UKeBao.model.login.examine.ExamineModel;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.login.examine.ExamineActivity;
import com.HyKj.UKeBao.viewModel.BaseViewModel;

/**
 * Created by Administrator on 2016/9/19.
 */
public class ExamineViewModel extends BaseViewModel {
    private ExamineModel mModel;

    private ExamineActivity mActivity;

    private BaseInfo baseInfo;

    public ExamineViewModel(ExamineActivity activity,ExamineModel model){
        mModel=model;

        mActivity=activity;

        mModel.setView(this);
    }

    //注销登陆
    public void loginOut() {
        mModel.loginOut();
    }

    //获取客服电话
    public void getCostomerService() {
        mModel.getCostomerService();
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        if(data.action == Action.Login_Examine_loginout){
            boolean success = (boolean) data.t;
            if (success) {
                //当注销成功时，通知界面清空token并返回登陆界面
                mActivity.loginOut();
                LogUtil.d("注销成功"+ MyApplication.token);
            }else {
                mActivity.loginoutFail();
            }
        }
        //获取客服电话
        else if(data.action == Action.Login_Examine_getCostomerService){
            String phone= (String) data.t;

            mActivity.service_tel=phone;
        }
    }

    @Override
    public void onRequestErroInfo(String erroinfo) {

        LogUtil.d("ExamineViewModel"+erroinfo);

        mActivity.toast("网络连接失败!");
    }

}

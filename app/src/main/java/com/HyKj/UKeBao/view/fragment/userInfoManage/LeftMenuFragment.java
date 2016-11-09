package com.HyKj.UKeBao.view.fragment.userInfoManage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.FragmentLeftMenuBinding;
import com.HyKj.UKeBao.model.userInfoManage.LeftMenuFragmentModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.businessManage.giveIntegral.IntegralRecordActivity;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;
import com.HyKj.UKeBao.view.activity.login.forgetPassword.ForgetPasswordActivity;
import com.HyKj.UKeBao.view.activity.userInfoManage.ModifyPasswordActivity;
import com.HyKj.UKeBao.view.activity.userInfoManage.MyBankCardActivity;
import com.HyKj.UKeBao.view.activity.userInfoManage.WithdrawalsActivity;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.viewModel.userInfoManage.LeftMenuFragmentViewModel;


/**
 * 侧边栏页面
 * Created by Administrator on 2016/9/21.
 */
public class LeftMenuFragment extends BaseFragment implements OnClickListener {
    private View contentView;

    private FragmentLeftMenuBinding mBinding;

    private LeftMenuFragmentViewModel viewModel;

    private Context mContext;

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel=new LeftMenuFragmentViewModel(new LeftMenuFragmentModel(),this);

        if(contentView == null){
            mBinding=FragmentLeftMenuBinding.inflate(inflater);

            mBinding.setViewModel(viewModel);

            contentView=mBinding.getRoot();
        }

        return contentView;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void initData() {
        mContext=getActivity();

        viewModel.getBusinessInfo();//获取店铺信息

        viewModel.getCustomerPhone();//获取客服电话

    }

    @Override
    public void setListeners() {
        mBinding.llIntegralAccount.setOnClickListener(this);

        mBinding.llCashAccount.setOnClickListener(this);

        mBinding.llPasswordSetting.setOnClickListener(this);

        mBinding.llApplyCash.setOnClickListener(this);

        mBinding.btnExitUser.setOnClickListener(this);

        mBinding.llApplyCashAccount.setOnClickListener(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //积分账户
            case R.id.ll_integral_account:
                Intent integral_intent= IntegralRecordActivity.getStartIntent(mContext);

                integral_intent.putExtra("isCashRecord",false);

                startActivity(integral_intent);
                break;
            //现金账户
            case R.id.ll_cash_account:
                Intent cash_intent= IntegralRecordActivity.getStartIntent(mContext);

                cash_intent.putExtra("isCashRecord",true);

                startActivity(cash_intent);

                break;
            //密码设置
            case R.id.ll_password_setting:
                startActivity(ModifyPasswordActivity.getStartIntent(mContext));

                break;
            //申请提现
            case R.id.ll_apply_cash:
                Intent intent_withdrawls=WithdrawalsActivity.getStartIntent(mContext);

                intent_withdrawls.putExtra("cash",viewModel.businessInfo.cash);

                intent_withdrawls.putExtra("all_money",viewModel.businessInfo.getCash());

                intent_withdrawls.putExtra("businessStoreName",viewModel.businessInfo.getBusinessName());

                startActivity(intent_withdrawls);

                break;
            //提现账户
            case R.id.ll_apply_cash_account:

                startActivity(MyBankCardActivity.getStartIntent(mContext));

                break;
            //退出
            case R.id.btn_exit_user:
                BufferCircleDialog.show(getActivity(),"正在注销，请稍候~",false,null);

                //注销
                viewModel.cancellation();

                break;
        }
    }
    //注销回调
    public void cancellation(String msg) {
        toast(msg,mContext);

        getActivity().finish();

        MyApplication.token=null;

        SharedPreferences sp=mContext.getSharedPreferences("user_login", getActivity().MODE_PRIVATE);

        SharedPreferences.Editor editor=sp.edit();

        editor.putString("token",null);//注意,clear后也要commit才能生效

        editor.commit();

        startActivity(LoginActivity.getStartIntent(mContext));
    }

    @Override
    public void onResume() {
        super.onResume();

        viewModel.getBusinessInfo();//获取店铺信息
    }
}

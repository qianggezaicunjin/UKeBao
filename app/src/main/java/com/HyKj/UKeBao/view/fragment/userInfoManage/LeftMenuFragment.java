package com.HyKj.UKeBao.view.fragment.userInfoManage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.FragmentLeftMenuBinding;
import com.HyKj.UKeBao.model.UserInfoManage.LeftMenuFragmentModel;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.viewModel.UserInfoManage.LeftMenuFragmentViewModel;


/**
 * 侧边栏页面
 * Created by Administrator on 2016/9/21.
 */
public class LeftMenuFragment extends BaseFragment implements OnClickListener {
    private View contentView;

    private FragmentLeftMenuBinding mBinding;

    private LeftMenuFragmentViewModel viewModel;
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
        viewModel.getBusinessInfo();//获取店铺信息
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

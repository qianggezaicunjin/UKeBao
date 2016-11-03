package com.HyKj.UKeBao.view.fragment.marketingManage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.view.activity.marketingManage.CardManagerActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.RedPacketManagerActivity;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;


/**
 *  营销管理
 * Created by Administrator on 2016/9/21.
 */
public class MarketManagerFragment extends BaseFragment implements View.OnClickListener{


    private View contentView;

    private MainFragmentListener imagListener;

    private ImageButton ib_user;

    private TextView tv_title;

    private LinearLayout ll_cardManager;//卡劵管理

    private LinearLayout ll_redManager;//红包管理

    private LinearLayout ll_value_exchange;//超值兑换

    public MarketManagerFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    public MarketManagerFragment(MainFragmentListener listener){
        imagListener=listener;
    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(contentView == null){
            contentView=inflater.inflate((R.layout.fragment_market_manager),container,false);
        }
        return contentView;
    }

    @Override
    public void findViews() {
        ib_user= (ImageButton) contentView.findViewById(R.id.imb_user_icon);

        tv_title= (TextView) contentView.findViewById(R.id.tv_mainTitle);

        ll_cardManager= (LinearLayout) contentView.findViewById(R.id.ll_manager_cardJuan);

        ll_redManager= (LinearLayout) contentView.findViewById(R.id.ll_manager_redPacket);

        ll_value_exchange= (LinearLayout) contentView.findViewById(R.id.ll_manager_overflow_exchange);

    }

    @Override
    public void initData() {
        tv_title.setText("营销管理");
    }

    @Override
    public void setListeners() {
        ib_user.setOnClickListener(this);

        ll_cardManager.setOnClickListener(this);

        ll_redManager.setOnClickListener(this);

        ll_value_exchange.setOnClickListener(this);
    }

    @Override
    public void initViews() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //弹出左侧边栏
            case R.id.imb_user_icon:

                imagListener.toastOutLeftFragment();

                break;
            //卡劵管理
            case R.id.ll_manager_cardJuan:
                BufferCircleDialog.show(getActivity(),"努力加载中,请稍候...",false,null);

                startActivity(CardManagerActivity.getStartIntent(getActivity()));

                break;
            //红包管理
            case R.id.ll_manager_redPacket:
                BufferCircleDialog.show(getActivity(),"努力加载中,请稍候...",false,null);

                startActivity(RedPacketManagerActivity.getStartIntent(getActivity()));

                break;
            //超值兑换
            case R.id.ll_manager_overflow_exchange:

                break;
        }
    }
}

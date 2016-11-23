package com.HyKj.UKeBao.viewModel.marketingManage;

import android.databinding.Bindable;
import android.os.Bundle;
import android.text.TextUtils;

import com.HyKj.UKeBao.BR;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.LanFragmentModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardAndRedPacketInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.CardDetail;
import com.HyKj.UKeBao.model.marketingManage.bean.MemberCardInfo;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetail;
import com.HyKj.UKeBao.util.Action;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.MapZoomUtils;
import com.HyKj.UKeBao.util.ModelAction;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.CardDetailActivity;
import com.HyKj.UKeBao.view.fragment.marketingManage.LanFragment;

import com.HyKj.UKeBao.viewModel.BaseViewModel;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17.
 */
public class LanFragmentViewModel extends BaseViewModel {
    public LanFragmentModel mModel;

    public MainActivity mActivity;

    public CardDetailActivity cActivity;

    public int memberCount;

    public LanFragment mFragment;

    public BusinessInfo businessInfo;

    public List<CardAndRedPacketInfo> redPacketAndCardList;//卡劵与红包信息集合

    @Bindable
    public CardDetail cardDetail;//卡劵详情

    public RedPacketDetail redPacketDetail;//红包详情

    private boolean onMapFlag=true;//判断获取卡劵信息来源是否在地图中

    public LanFragmentViewModel(LanFragmentModel model, LanFragment mFragment) {
        mModel = model;

        this.mFragment=mFragment;

        mActivity = (MainActivity) mFragment.getActivity();

        mModel.setView(this);
    }

    public LanFragmentViewModel(LanFragmentModel model, CardDetailActivity activity) {
        mModel = model;

        cActivity = activity;

        mModel.setView(this);
    }

    //获取会员数量
    public void getMemberCount(int discount, double longitude, double latitude) {
        mModel.getMemberCount(discount, longitude, latitude);
    }

    //获取店铺数据信息
    public void getBusinessInfo() {
        mModel.getBusinessInfo();
    }

    //获取红包和卡劵信息
    public void getRedPacketsAndCardInfo(){
        mModel.getRedPacketsAndCardInfo();
    }

    //获取单个红包详情
    public void getSingRedPacketDetail(Integer integer) {
        mModel.getSingRedPacketDetail(integer);
    }

    //获取单个卡劵详情
    public void getSingCardDetail(int id,boolean onMapFlag){
        this.onMapFlag=onMapFlag;

        mModel.getSingCardDetail(id);
    }

    //限制领取卡劵用户列表的显示数
    public List<MemberCardInfo> getDisplayNum(int position,int rows){
        LogUtil.d("position初始值" + position);

        if((cardDetail.getMenberCouponsList().size()-position)>rows) {
            List<MemberCardInfo> list = new ArrayList<>(rows);

            for (int i = 0; i < rows; i++) {
                list.add(i, cardDetail.getMenberCouponsList().get(position++));

                LogUtil.d("cardDetail_position" + position);
            }

            LogUtil.d("cardDetail_list" + list.toString());

            return list;
        }else{
            int size=cardDetail.getMenberCouponsList().size()-position;//如果有余数，则集合采用余数作为size

            List<MemberCardInfo> list = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                list.add(i, cardDetail.getMenberCouponsList().get(position++));

                LogUtil.d("cardDetail_position" + position);
            }
            LogUtil.d("cardDetail_list" + list.toString());

            return list;
        }
    }

    //初始化Mark数据
    private void initMarkData() {
        for (int i = 0; i < redPacketAndCardList.size(); i++) {

            if (redPacketAndCardList.get(i).getType().equals("0")) {

                if (redPacketAndCardList.get(i).getLatitude() != null) {
                    double la = Double.valueOf(redPacketAndCardList.get(i).getLatitude());

                    double lo = Double.valueOf(redPacketAndCardList.get(i).getLongitude());

                    LatLng point = new LatLng(la, lo);

                    // 构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.card_info_lan);

                    // 构建MarkerOption，用于在地图上添加Marker
                    Bundle cardinfo = new Bundle();

                    // cardinfo.put
                    cardinfo.putString("id", redPacketAndCardList.get(i).getId());

                    cardinfo.putString("type", redPacketAndCardList.get(i).getType());

                    mFragment.setMark(point,bitmap,cardinfo);
                }
            }
            if (redPacketAndCardList.get(i).getType().equals("1")) {
                if (redPacketAndCardList.get(i).getLatitude() != null) {
                    double la = Double.valueOf(redPacketAndCardList.get(i).getLatitude());

                    double lo = Double.valueOf(redPacketAndCardList.get(i).getLongitude());

                    LatLng point = new LatLng(la, lo);

                    // 构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.red_packet_icon);

                    // 构建MarkerOption，用于在地图上添加Marker
                    MarkerOptions m = new MarkerOptions();

                    Bundle cardinfo = new Bundle();

                    cardinfo.putString("id", redPacketAndCardList.get(i).getId());

                    cardinfo.putString("count", redPacketAndCardList.get(i).getCount());

                    cardinfo.putString("surplusCount", redPacketAndCardList.get(i).getSurplusCount());

                    cardinfo.putString("imageUrl", redPacketAndCardList.get(i).getImage());

                    cardinfo.putString("surplusQuota", redPacketAndCardList.get(i).getSurplusQuota());

                    cardinfo.putString("integralQuota", redPacketAndCardList.get(i).getIntegralQuota());

                    cardinfo.putString("context", redPacketAndCardList.get(i).getContext());

                    cardinfo.putString("type", redPacketAndCardList.get(i).getType());

                    mFragment.setMark(point,bitmap,cardinfo);
                }
            }
        }
    }

    //设置单个卡劵详情
    private void setCardDetail(CardDetail cardDetail) {
        if(!TextUtils.isEmpty(cardDetail.getDistance())){
            mFragment.setCardDetail(0,cardDetail);
        }else {
            mFragment.setCardDetail(1,cardDetail);
        }
    }

    //设置单个红包详情
    private void setRedPacketDetail(RedPacketDetail redPacketDetail) {
        if(!redPacketDetail.getDistance().equals("")){
            mFragment.setRedPacketDetail(0,redPacketDetail);
        }else {
            mFragment.setRedPacketDetail(1,redPacketDetail);
        }
    }

    //获取当前地图比例尺
    public double getZoom(int grade) {
        if (MapZoomUtils.getZoomMap(grade) > 3000) {
            return MapZoomUtils.getZoomMap(grade) / 1000;
        } else {
            return 3;
        }
    }

    @Override
    public void onRequestSuccess(ModelAction data) {
        //获取会员数量
        if (data.action == Action.MarketingManage_GetMemberCount) {
            memberCount = (int) data.t;

            mFragment.setMemberCount(memberCount);
        }
        //获取会员信息
        else if (data.action == Action.MarketingManage_GetBusinessInfo) {
            businessInfo = (BusinessInfo) data.t;

            mFragment.setBusinessInfo(businessInfo);

        }
        //获取红包和卡劵信息
        else if (data.action==Action.MarketingManage_GetRedPacketsAndCard) {
            redPacketAndCardList= (List<CardAndRedPacketInfo>) data.t;

            initMarkData();
        }
        //获取单个卡劵详情
        else if(data.action==Action.MarketingManage_GetSingCardDetail){
            cardDetail= (CardDetail) data.t;

            notifyPropertyChanged(BR.cardDetail);

            if(onMapFlag) {
                setCardDetail(cardDetail);
            }else {
                cActivity.setDataList(cardDetail.getMenberCouponsList());
            }
        }
        //获取单个红包详情
        else if(data.action==Action.MarketingManage_GetSingRedPacketDetail){
            redPacketDetail= (RedPacketDetail) data.t;

            setRedPacketDetail(redPacketDetail);
        }
    }




    @Override
    public void onRequestErroInfo(String erroinfo) {
        if(mFragment==null) {
            cActivity.toast(erroinfo, cActivity);
        }else {
            mActivity.toast(erroinfo, mActivity);
        }

    }


}

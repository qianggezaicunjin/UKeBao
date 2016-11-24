package com.HyKj.UKeBao.view.fragment.marketingManage;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.bean.CardAndRedPacketInfo;
import com.HyKj.UKeBao.model.businessManage.bean.CardBackInfo;
import com.HyKj.UKeBao.model.businessManage.bean.RedBackInfo;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.marketingManage.LanFragmentModel;
import com.HyKj.UKeBao.model.marketingManage.bean.CardDetail;
import com.HyKj.UKeBao.model.marketingManage.bean.LanBean;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetail;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.MapZoomUtils;
import com.HyKj.UKeBao.view.activity.marketingManage.CardCustomerActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.CardDetailActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.RedPacketAttractCustomeActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.RedPacketDetailActivity;
import com.HyKj.UKeBao.view.fragment.BaseFragment;
import com.HyKj.UKeBao.view.listener.MainFragmentListener;
import com.HyKj.UKeBao.viewModel.marketingManage.LanFragmentViewModel;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Projection;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 揽
 * Created by Administrator on 2016/9/21.
 */
public class LanFragment extends BaseFragment implements OnClickListener, OnGetPoiSearchResultListener {
    private View contentView;

    private Context mContext;

    private MainFragmentListener imagListener;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private LocationClient mLocClient;

    private LatLng lotlat;

    private MylocationListener mLocListener = new MylocationListener();

    private BMapManager mMapManager;

    private LocationClient mLocationClient;

    private LatLng startLng, finishLng;

    public double moveDist;

    private BDLocation myBDLocation;

    private TextView currentLocation_LanFragment_MainActivity;

    private ImageView currentLocationImg_LanFrament_MainActivity;

    private double x;

    private double y;

    private TextView redPacket_lanFragment_mainActivity;

    private ReverseGeoCodeOption reverseCode;

    private GeoCoder geoCoder;

    protected ReverseGeoCodeOption result;

    private TextView mapGradeRange_lanFragment_mainActivity;

    public double curryentLatitude = -0.0;

    public double currryentLongtitude = -0.0;

    private int grade;

    private LanBean bean;

    private RelativeLayout circle_LanFragment_Show;

    private TextView ventureName;

    private LinearLayout hideOrShow;

    private boolean showOrHideFlag = false;

    private View popupWindow_View;

    private TextView contextAdd;

    private TextView count_popUpWindow;

    private PopupWindow popupWindow;

    private int[] location;

    private int popupWidth;

    private int popupHeight;

    protected BusinessInfo businessInfo;

    private TextView memberCount;

    private int widthAlls;

    private int heightAlls;

    private List<int[]> pointList = new ArrayList<int[]>();

    private TextView cardGuid;

    private int countX = 120;

    private int countY = 400;

    private Handler mHandler = new Handler();

    private String fromWhere;

    /**
     * 发红包或者卡劵后返回地图判断是否改变中心点的标记
     */
    private boolean isSetCenter = true;

    private int[] imagerArr = {R.drawable.a1_03, R.drawable.b1_03, R.drawable.c1_03,
            R.drawable.a, R.drawable.b, R.drawable.c,
            R.drawable.d, R.drawable.e, R.drawable.f,
            R.drawable.g, R.drawable.i};

    private int countCheck;

    private LinearLayout viewTop_lanfragment;

    private LinearLayout toastPopUp_Lanfragment;

    // 红包，卡券已领取
    private TextView useAfter;
    // 红包，卡券已经使用
    private TextView getAfter;

    private int page = 1, rows = 10;

    private List<RedBackInfo> redList = new ArrayList<RedBackInfo>();

    private List<CardBackInfo> cardList = new ArrayList<CardBackInfo>();

    private String locationgCurrent = "";

    protected LocationClientOption optst;

    private boolean locationFlag = false;

    private List<CardAndRedPacketInfo> cardAndRedPacketInfosList = new ArrayList<>();

    // 卡券红包图标是否可以被点击
    private boolean clicImageFlag = true;

    private MapStatus mapStatusss;

    private ImageButton imb_user;

    private TextView mainTitle;

    private int distance;

    public LanFragmentViewModel viewModel;

    public LanFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public LanFragment(Context context, MainFragmentListener listener) {
        imagListener = listener;

        mContext = context;
    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (contentView == null) {
            contentView = inflater.inflate((R.layout.fragment_lan), container, false);
        }
        return contentView;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    public void findViews() {
        imb_user = (ImageButton) contentView.findViewById(R.id.imb_user_icon);

        mainTitle = (TextView) contentView.findViewById(R.id.tv_mainTitle);

        ventureName = (TextView) contentView.findViewById(R.id.ventureName_lanFragment_maintAcitivy);

        ventureName = (TextView) contentView.findViewById(R.id.ventureName_lanFragment_maintAcitivy);

        viewTop_lanfragment = (LinearLayout) contentView.findViewById(R.id.viewTop_lanfragment);

        cardGuid = (TextView) contentView.findViewById(R.id.cardGuid_lanFragment_mainActivity);

        memberCount = (TextView) contentView.findViewById(R.id.memberCount_LanFragment_MainActivity);

        hideOrShow = (LinearLayout) contentView.findViewById(R.id.hideOrShow_Lanfragment);

        mMapView = (MapView) contentView.findViewById(R.id.bmapView);

        currentLocation_LanFragment_MainActivity = (TextView) contentView.findViewById(R.id.currentLocation_LanFragment_MainActivity);

        currentLocationImg_LanFrament_MainActivity = (ImageView) contentView.findViewById(R.id.currentLocationImg_LanFrament_MainActivity);

        circle_LanFragment_Show = (RelativeLayout) contentView.findViewById(R.id.listCircle_lanFrament);

        redPacket_lanFragment_mainActivity = (TextView) contentView.findViewById(R.id.redPacket_lanFragment_mainActivity);

        mapGradeRange_lanFragment_mainActivity = (TextView) contentView.findViewById(R.id.mapGradeRange_lanFragment_mainActivity);
    }

    @Override
    public void initData() {
        x = currentLocationImg_LanFrament_MainActivity.getX();

        y = currentLocationImg_LanFrament_MainActivity.getY();

        mainTitle.setText("优积分揽客");

        viewModel = new LanFragmentViewModel(new LanFragmentModel(), this);

        inintPopUpWindow();

        viewTop_lanfragment.setAlpha(225);

        circle_LanFragment_Show.getBackground().setAlpha(50);

        BMapManager.init();

        viewModel.getRedPacketsAndCardInfo();

        initMap();

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

        widthAlls = wm.getDefaultDisplay().getWidth();

        heightAlls = wm.getDefaultDisplay().getHeight();

        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) circle_LanFragment_Show.getLayoutParams();

        linearParams.height = widthAlls * 4 / 5;

        linearParams.width = widthAlls * 4 / 5;

        circle_LanFragment_Show.setLayoutParams(linearParams);

        moveThePositon();
    }

    //移动位置
    public void moveThePositon() {
        if (currentLocation_LanFragment_MainActivity != null) {
            currentLocation_LanFragment_MainActivity.setText("正在获取位置信息...");
        }
        isSetCenter = true;

        viewModel.getBusinessInfo();
    }

    //初始化地图
    private void initMap() {
        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mBaiduMap.setMyLocationEnabled(true);

        UiSettings uiSettings = mBaiduMap.getUiSettings();

        uiSettings.setZoomGesturesEnabled(true);

        // 初始化地图位置
        mLocationClient = new LocationClient(mContext);

        mLocationClient.registerLocationListener(mLocListener);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mBaiduMap.hideInfoWindow();

                String type = marker.getExtraInfo().getString("type");

                switch (Integer.valueOf(type)) {
                    case 0:
                        fromWhere = "卡券";

                        showWindowAtView(currentLocationImg_LanFrament_MainActivity);

                        setMapCenter(marker.getPosition().latitude, marker.getPosition().longitude);

                        bean = new LanBean();

                        bean.setId(Integer.valueOf(marker.getExtraInfo().getString("id")));

                        viewModel.getSingCardDetail(Integer.valueOf(marker.getExtraInfo().getString("id")),true);

                        break;
                    case 1:
                        fromWhere = "红包";

                        setMapCenter(marker.getPosition().latitude, marker.getPosition().longitude);

                        showWindowAtView(currentLocationImg_LanFrament_MainActivity);

                        bean = new LanBean();

                        String context = marker.getExtraInfo().getString("context");

                        String id = marker.getExtraInfo().getString("id");

                        double curryentLatitude = marker.getPosition().latitude;

                        double currryentLongtitude = marker.getPosition().longitude;

                        String count = marker.getExtraInfo().getString("count");// 总数量

                        String surplusCount = marker.getExtraInfo().getString("surplusCount");// 剩余数量

                        String imageUrl = marker.getExtraInfo().getString("imageUrl");

                        String integralQuota = marker.getExtraInfo().getString("integralQuota");// 总积分

                        String surplusQuota = marker.getExtraInfo().getString("surplusQuota");// 剩余积分

                        bean.setContext(context);

                        bean.setId(Integer.valueOf(id));

                        bean.setCurryentLatitude(Double.valueOf(curryentLatitude));

                        bean.setCurrryentLongtitude(Double.valueOf(currryentLongtitude));

                        bean.setCount(Integer.valueOf(count));

                        bean.setSurplusCount(surplusCount);

                        bean.setImageUrl(imageUrl);

                        bean.setIntegralQuota(integralQuota);

                        bean.setSurplusQuota(surplusQuota);

                        viewModel.getSingRedPacketDetail(Integer.valueOf(id));

                        break;

                }
                return true;
            }
        });
        LocationClientOption opt = new LocationClientOption();

        opt.setIsNeedAddress(true);

        mLocationClient.setLocOption(opt);

        opt.setOpenGps(true);

        mLocationClient.start();

        mLocationClient.requestLocation();

        reverseCode = new ReverseGeoCodeOption();

        geoCoder = GeoCoder.newInstance();

        geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                if (result.getAddressDetail() != null) {

                    locationgCurrent = result.getAddressDetail().district
                            + result.getAddressDetail().street
                            + result.getAddressDetail().streetNumber + "";

                    if (locationgCurrent.equals("")) {
                        currentLocation_LanFragment_MainActivity.setText("正在获取位置信息...");
                    } else {
                        currentLocation_LanFragment_MainActivity.setText(locationgCurrent + "");
                    }
                }
            }
        });
    }

    @Override
    public void setListeners() {
        imb_user.setOnClickListener(this);

        cardGuid.setOnClickListener(this);

        redPacket_lanFragment_mainActivity.setOnClickListener(this);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            private int grade;

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                startLng = mapStatus.target;

                currentLocationImg_LanFrament_MainActivity.setVisibility(View.VISIBLE);

                circle_LanFragment_Show.setBackgroundResource(R.drawable.d1_03);
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                Random r = new Random();

                countCheck = r.nextInt(imagerArr.length);

                circle_LanFragment_Show.setBackgroundResource(imagerArr[countCheck]);

                grade = (int) mBaiduMap.getMapStatus().zoom;

                int zoom = (int) viewModel.getZoom(grade);

                viewModel.getMemberCount(zoom, mapStatus.target.longitude, mapStatus.target.latitude);

                finishLng = mapStatus.target;

                if (startLng.latitude != finishLng.latitude || startLng.longitude != finishLng.longitude) {
                    Projection ject = mBaiduMap.getProjection();

                    Point startPoint = ject.toScreenLocation(startLng);

                    Point finishPoint = ject.toScreenLocation(finishLng);

                    double x = Math.abs(finishPoint.x - startPoint.x);

                    double y = Math.abs(finishPoint.y - startPoint.y);
                    if (x > moveDist || y > moveDist) {
                        currentLocation_LanFragment_MainActivity.setText("正在获取位置信息...");
                        // 设置滑动监听事件
                        if (myBDLocation != null) {

                            LatLng latlng = mBaiduMap.getMapStatus().target;

                            if (latlng != null) {
                                result = reverseCode.location(new LatLng(latlng.latitude, latlng.longitude));

                                curryentLatitude = latlng.latitude;

                                currryentLongtitude = latlng.longitude;

                                LogUtil.d("移动后纬度:" + curryentLatitude + "精度：" + currryentLongtitude);

                                geoCoder.reverseGeoCode(result);
                            }
                        }
                    }
                }
            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                grade = (int) mapStatus.zoom;

                distance = MapZoomUtils.getZoomMap(grade) / 1000;

                if (MapZoomUtils.getZoomMap(grade) > 1000) {
                    mapGradeRange_lanFragment_mainActivity.setText("范围<" + MapZoomUtils.getZoomMap(grade) / 1000 + "公里");
                } else {
                    mapGradeRange_lanFragment_mainActivity.setText("范围<" + MapZoomUtils.getZoomMap(grade) + "米");
                }
            }
        });

        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(mBaiduMap.getMapStatus().target, 18);

        mBaiduMap.setMapStatus(msu);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.redPacket_lanFragment_mainActivity:
                if (null != mBaiduMap && !currentLocation_LanFragment_MainActivity.getText()
                        .equals("")) {
                    if (curryentLatitude != -1) {
                        Intent intent = RedPacketAttractCustomeActivity.getStartIntent(getActivity());

                        intent.putExtra("currylocation", locationgCurrent + "");//当前位置

                        intent.putExtra("membercount", memberCount.getText() + "");//会员人数

                        intent.putExtra("gradearrange", mapGradeRange_lanFragment_mainActivity.getText() + "");

                        intent.putExtra("curryentLatitude", curryentLatitude);//当前纬度

                        intent.putExtra("currryentLongtitude", currryentLongtitude);//当前精度

                        intent.putExtra("zoomMap", MapZoomUtils.getZoomMap(grade) + "");//地图放大倍数

                        intent.putExtra("distance", distance);//距离

                        intent.putExtra("businessInfo", businessInfo);

                        startActivity(intent);
                    } else {
                        toast("定位失败，无法根据位置发送揽客", getActivity());
                    }
                } else {
                    toast("正在获取位置...", getActivity());
                }
                break;
            case R.id.currentLocationImg_LanFrament_MainActivity:
                if (clicImageFlag) {
                    if (showOrHideFlag) {
                        hideOrShow.setVisibility(View.INVISIBLE);

                        v.getLocationOnScreen(location);

                        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                                (location[0] + v.getWidth() / 2) - popupWidth / 2,
                                location[1] - popupHeight + 20);

                        showOrHideFlag = false;

                        currentLocation_LanFragment_MainActivity.setFocusable(true);
                    } else {
                        hideOrShow.setVisibility(View.VISIBLE);

                        popupWindow.dismiss();

                        showOrHideFlag = true;
                    }
                }
                break;
            case R.id.cardGuid_lanFragment_mainActivity:

                if (mBaiduMap != null && !currentLocation_LanFragment_MainActivity.getText().equals("")) {

                    if (curryentLatitude != -0.0) {
                        Intent i = CardCustomerActivity.getStartIntent(getActivity());

                        i.putExtra("currylocation", locationgCurrent + "");

                        i.putExtra("membercount", memberCount.getText() + "");

                        i.putExtra("gradearrange", mapGradeRange_lanFragment_mainActivity.getText() + "");

                        i.putExtra("curryentLatitude", curryentLatitude);

                        i.putExtra("currryentLongtitude", currryentLongtitude);

                        i.putExtra("businessInfo", businessInfo);

                        startActivity(i);
                    } else {
                        toast("定位失败，无法根据位置发送揽客", getActivity());
                    }
                } else {
                    toast("正在获取位置...", getActivity());
                }
                break;
            case R.id.toastPopUp_Lanfragment:
                if (fromWhere.equals("红包")) {
                    BufferCircleDialog.show(getActivity(),"努力加载中,请稍候...",false,null);

                    Intent intents = RedPacketDetailActivity.getStartIntent(getActivity());

                    intents.putExtra("id", bean.getId());

                    intents.putExtra("count", bean.getCount());

                    intents.putExtra("context", bean.getContext() + "");

                    intents.putExtra("surplusCount", bean.getSurplusCount());

                    intents.putExtra("imageUrl", bean.getImageUrl());

                    intents.putExtra("integralQuota", bean.getIntegralQuota());

                    intents.putExtra("surplusQuota", bean.getSurplusQuota());

                    startActivity(intents);
                } else if (fromWhere.equals("卡券")) {
                    Intent intents = CardDetailActivity.getStartIntent(getActivity());

                    intents.putExtra("id", bean.getId());

                    startActivity(intents);
                } else {
                    return;
                }
                break;
            case R.id.imb_user_icon:
                imagListener.toastOutLeftFragment();

                break;
            default:
                break;
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
        poiDetailResult.getLocation();
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    //设置地图的中心点
    public void setMapCenter(double curryentLatitudes, double currryentLongtitudes) {
        curryentLatitude = curryentLatitudes;

        currryentLongtitude = currryentLongtitudes;

        LatLng center = new LatLng(curryentLatitude, currryentLongtitude);

        mapStatusss = new MapStatus.Builder().target(center).zoom(18).build();

        if (optst == null) {
            optst = new LocationClientOption();
        }

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatusss);

        optst.setIsNeedAddress(true);

        mLocationClient.setLocOption(optst);

        optst.setOpenGps(true);

        result = reverseCode.location(new LatLng(curryentLatitude, currryentLongtitude));

        geoCoder.reverseGeoCode(result);


        if (mBaiduMap != null) {
            mBaiduMap.setMapStatus(mapStatusUpdate);
        }
    }

    // 初始化popupwindow
    public void inintPopUpWindow() {
        popupWindow_View = LayoutInflater.from(getActivity()).inflate(R.layout.mytoast_popupwindow, null);

        contextAdd = (TextView) popupWindow_View.findViewById(R.id.contextAdd_popUpWindow_Lanfragment);

        useAfter = (TextView) popupWindow_View.findViewById(R.id.useAfter_popUpWindow_Lanfragment);

        getAfter = (TextView) popupWindow_View.findViewById(R.id.getAfter_popUpWindow_Lanfragment);

        toastPopUp_Lanfragment = (LinearLayout) popupWindow_View.findViewById(R.id.toastPopUp_Lanfragment);

        toastPopUp_Lanfragment = (LinearLayout) popupWindow_View.findViewById(R.id.toastPopUp_Lanfragment);

        toastPopUp_Lanfragment.setOnClickListener(this);

        popupWindow = new PopupWindow(popupWindow_View, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        ColorDrawable dw = new ColorDrawable(00000);

        popupWindow.setBackgroundDrawable(dw);

        popupWindow.setOutsideTouchable(true);

        popupWindow.setFocusable(true);

        popupWindow_View.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);

        popupWidth = popupWindow_View.getMeasuredWidth();

        popupHeight = popupWindow_View.getMeasuredHeight();

        location = new int[2];

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // TODO Auto-generated method stub
                hideOrShow.setVisibility(View.VISIBLE);

                currentLocationImg_LanFrament_MainActivity.setImageResource(R.drawable.location);

                clicImageFlag = false;

                currentLocationImg_LanFrament_MainActivity.setVisibility(View.VISIBLE);

            }
        });
        currentLocationImg_LanFrament_MainActivity.getLocationOnScreen(location);

        popupWindow.showAtLocation(currentLocationImg_LanFrament_MainActivity, Gravity.NO_GRAVITY,
                (location[0] + currentLocationImg_LanFrament_MainActivity
                        .getWidth() / 2) - popupWidth / 2, location[1]
                        - popupHeight + 20);

        popupWindow.dismiss();

        currentLocationImg_LanFrament_MainActivity.setOnClickListener(this);

    }

    //在控件正上方显示popUpWindow
    public void showWindowAtView(View v) {
        currentLocationImg_LanFrament_MainActivity.setVisibility(View.INVISIBLE);

        currentLocationImg_LanFrament_MainActivity.getLocationOnScreen(location);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + currentLocationImg_LanFrament_MainActivity
                .getWidth() / 2) - popupWidth / 2, location[1] - popupHeight + 20);

        hideOrShow.setVisibility(View.INVISIBLE);
    }

    //配置Mark并显示
    public void setMark(LatLng point, BitmapDescriptor bitmap, Bundle cardinfo) {
        OverlayOptions option = new MarkerOptions().position(point)
                .icon(bitmap).extraInfo(cardinfo);

        // 在地图上添加Marker，并显示
        mBaiduMap.addOverlay(option);
    }

    //设置单个卡劵数据
    public void setCardDetail(int i, CardDetail cardDetail) {
        switch (i) {
            case 0:
                contextAdd.setText(cardDetail.price + "元代金券-满" + cardDetail.deduction
                        + "元减" + cardDetail.getPrice() + "元");

                useAfter.setText("已领 :" + cardDetail.getMenberGetCount() + "/"
                        + cardDetail.getInventory() + "个");

                getAfter.setText("已使用 :" + cardDetail.getMenberUseCount() + "/"
                        + cardDetail.getMenberGetCount() + "个");

                mapGradeRange_lanFragment_mainActivity.setText("范围<" + cardDetail.getDistance() + "公里");

                break;
            case 1:
                contextAdd.setText(cardDetail.price + "元代金券-满" + cardDetail.deduction
                        + "元减" + cardDetail.getPrice() + "元");

                useAfter.setText("已领 :" + cardDetail.getMenberGetCount() + "/"
                        + cardDetail.getInventory() + "个");

                getAfter.setText("已使用 :" + cardDetail.getMenberUseCount() + "/"
                        + cardDetail.getMenberGetCount() + "个");

                break;
        }
    }

    //设置单个红包数据
    public void setRedPacketDetail(int i, RedPacketDetail redPacketDetail) {
        switch (i) {
            case 0:
                mapGradeRange_lanFragment_mainActivity.setText("范围<" + redPacketDetail.getDistance() + "公里");

                contextAdd.setText(redPacketDetail.getContext() + "");

                useAfter.setText("已领 :" + redPacketDetail.surplusCount + "/"
                        + redPacketDetail.count + "个");

                getAfter.setText("");

                break;
            case 1:
                contextAdd.setText(redPacketDetail.getContext() + "");

                useAfter.setText("已领 :" + redPacketDetail.surplusCount + "/"
                        + redPacketDetail.count + "个");

                getAfter.setText("");

                break;
        }
    }

    //获取会员数据成功后更新会员人数
    public void setMemberCount(int num) {
        memberCount.setText("会员人数" + num + "人");
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;

        ventureName.setText(businessInfo.getBusinessName() + "");

        if (isSetCenter) {
            setMapCenter(businessInfo.getLatitude(), businessInfo.getLongitude());
        }
    }

    //生成卡劵图标
    public void setToMessageFromCardDetail(LanBean beans) {
        bean = beans;

        fromWhere = "卡券";

        currentLocationImg_LanFrament_MainActivity.setVisibility(View.INVISIBLE);

        LatLng point = new LatLng(bean.getCurryentLatitude(), bean.getCurrryentLongtitude());

        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.card_info_lan);

        // 构建MarkerOption，用于在地图上添加Marker
        Bundle cardinfo = new Bundle();

        cardinfo.putString("id", bean.getId() + "");

        cardinfo.putString("type", "0");

        setMark(point,bitmap,cardinfo);

        contextAdd.setText(bean.getNameTiltle() + "");

        currentLocationImg_LanFrament_MainActivity.setImageResource(R.drawable.card);

        showOrHideFlag = true;

        useAfter.setText("已领 ：" + 0 + "/" + bean.getCount());

        getAfter.setText("");

        currentLocationImg_LanFrament_MainActivity.getLocationOnScreen(location);

        popupWindow.showAtLocation(
                currentLocationImg_LanFrament_MainActivity,
                Gravity.NO_GRAVITY,
                (location[0] + currentLocationImg_LanFrament_MainActivity
                        .getWidth() / 2) - popupWidth / 2, location[1]
                        - popupHeight + 20);

        currentLocationImg_LanFrament_MainActivity.setOnClickListener(this);

        hideOrShow.setVisibility(View.INVISIBLE);

        viewModel.getSingCardDetail(beans.getId(),true);
    }

    private class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation loc) {
            if (null == loc) {
                return;
            }
            myBDLocation = loc;

            LatLng center = new LatLng(loc.getLatitude(), loc.getLongitude());

            MapStatus mapStatus = new MapStatus.Builder().target(center).zoom(18).build();

            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

            mBaiduMap.setMapStatus(mapStatusUpdate);

            result = reverseCode.location(new LatLng(loc.getLatitude(), loc.getLongitude()));

            geoCoder.reverseGeoCode(result);

            curryentLatitude = loc.getLatitude();

            currryentLongtitude = loc.getLongitude();

            viewModel.getMemberCount(MapZoomUtils.getZoomMap(mapStatus.zoom) / 1000, curryentLatitude, curryentLatitude);

            viewModel.getMemberCount(3, curryentLatitude, curryentLatitude);

            Random r = new Random();

            countCheck = r.nextInt(imagerArr.length);

            circle_LanFragment_Show.setBackgroundResource(imagerArr[countCheck]);

            if (currentLocation_LanFragment_MainActivity != null) {
                currentLocation_LanFragment_MainActivity.setText("正在获取位置信息...");
            }
            isSetCenter = true;

            viewModel.getBusinessInfo();

        }

    }

    public void setToMessageFromRedPacket(LanBean beans) {
        isSetCenter=false;

        viewModel.getBusinessInfo();

        fromWhere = "红包";

        currentLocationImg_LanFrament_MainActivity.setVisibility(View.INVISIBLE);

        bean = beans;

        LatLng point = new LatLng(bean.getCurryentLatitude(), bean.getCurrryentLongtitude());
        // 构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.red_packet_icon);
        // 构建MarkerOption，用于在地图上添加Marker
        MarkerOptions m = new MarkerOptions();

        Bundle redInfo = new Bundle();

        redInfo.putString("distance", bean.getDistance() + "");

        mapGradeRange_lanFragment_mainActivity.setText("范围小于" + bean.getDistance() + "公里");

        redInfo.putString("id", bean.getId() + "");

        redInfo.putString("count", bean.getCount() + "");

        redInfo.putString("surplusCount", bean.getSurplusCount());

        redInfo.putString("imageUrl", bean.getImageUrl());

        redInfo.putString("surplusQuota", bean.getSurplusQuota());

        redInfo.putString("integralQuota", bean.getIntegralQuota());

        redInfo.putString("context", bean.getContext());

        redInfo.putString("type", "1");

        setMark(point, bitmap, redInfo);

        contextAdd.setText(bean.getContext() + "");

        currentLocationImg_LanFrament_MainActivity.setImageResource(R.drawable.red_packet);

        showOrHideFlag = true;

        useAfter.setText("已领 :" + 0 + "/" + bean.getCount() + "个");

        getAfter.setText("");

        currentLocationImg_LanFrament_MainActivity.getLocationOnScreen(location);

        popupWindow.showAtLocation(
                currentLocationImg_LanFrament_MainActivity,
                Gravity.NO_GRAVITY,
                (location[0] + currentLocationImg_LanFrament_MainActivity
                        .getWidth() / 2) - popupWidth / 2, location[1]
                        - popupHeight + 20);

        currentLocationImg_LanFrament_MainActivity.setOnClickListener(this);

        hideOrShow.setVisibility(View.INVISIBLE);

        viewModel.getSingRedPacketDetail(beans.getId());
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        mMapView.onDestroy();

        mLocationClient.stop();

        mLocationClient.unRegisterLocationListener(mLocListener);
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();

        mBaiduMap.clear();

        mBaiduMap = null;

        reverseCode = null;

        geoCoder = null;

        result = null;
    }
}

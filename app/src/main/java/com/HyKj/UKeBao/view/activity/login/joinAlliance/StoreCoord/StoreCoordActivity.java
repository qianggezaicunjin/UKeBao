package com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreCoord;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
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
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * 商店坐标
 * Created by Administrator on 2016/8/30.
 */
public class StoreCoordActivity extends BaseActiviy implements OnClickListener {

    private final String TAG = "CoordinateSettingActivity";

    private Context mContext;

    private TextView titleBarName;

    private ImageButton backImageButton;

    private Button btn_confirmCoordinate;

    private MapView mMapView;

    private BaiduMap mBaiduMap;

    private LocationClient mLocClient;

    private LatLng lotlat;

    private MylocationListener mLocListener = new MylocationListener();

    private Context context;

    private BMapManager mMapManager;

    private LocationClient mLocationClient;

    private LatLng startLng, finishLng;

    public double moveDist;

    private BDLocation myBDLocation;

    private ImageView currentLocationImg;

    private TextView tv_locationText;

    private double latitude, longitude;// 经度，维度

    private boolean hasCoordinate;

    private ReverseGeoCodeOption reverseCode;

    private GeoCoder geoCoder;

    protected ReverseGeoCodeOption result;

    public double curryentLatitude;

    public double currryentLongtitude;

    private int flagTag = 0;

    private int cutNum;

    private double latitudes;

    private double longitudes;

    private float zoom;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, StoreCoordActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_store_coord);

        mMapView = (MapView) findViewById(R.id.bmapView_coordinateSetting);

        backImageButton = (ImageButton) findViewById(R.id.imb_title_bar_back);

        btn_confirmCoordinate = (Button) findViewById(R.id.btn_confirmCoordinate);

        tv_locationText = (TextView) findViewById(R.id.tv_locationText);

        currentLocationImg = (ImageView) findViewById(R.id.currentLocationImg_coordinateSetting);

        mContext=this;
    }

    @Override
    public void setUpView() {
        setTitleTheme("商店坐标");

        Intent intent = getIntent();

        hasCoordinate = intent.getBooleanExtra("hasCoordinate", false);

        longitude=intent.getDoubleExtra("longitude", -1);

        latitude=intent.getDoubleExtra("latitude", -1);

        zoom=intent.getFloatExtra("zoom",19);

        BMapManager.init();

        initData();
    }

    @Override
    public void setListeners() {
        backImageButton.setOnClickListener(this);

        btn_confirmCoordinate.setOnClickListener(this);

        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
                // TODO Auto-generated method stub
                startLng = arg0.target;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                // TODO Auto-generated method stub
                finishLng = arg0.target;
                if (startLng.latitude != finishLng.latitude
                        || startLng.longitude != finishLng.longitude) {
                    Projection ject = mBaiduMap.getProjection();
                    Point startPoint = ject.toScreenLocation(startLng);
                    Point finishPoint = ject.toScreenLocation(finishLng);
                    double x = Math.abs(finishPoint.x - startPoint.x);
                    double y = Math.abs(finishPoint.y - startPoint.y);
                    if (x > moveDist || y > moveDist) {
                        // 设置滑动监听事件
                        if (myBDLocation != null) {
                            LatLng latlng = mBaiduMap.getMapStatus().target;

                            latitude = latlng.latitude;
                            longitude = latlng.longitude;
                            zoom=arg0.zoom;
                            result = reverseCode.location(new LatLng(
                                    latlng.latitude, latlng.longitude));
                            curryentLatitude = latlng.latitude;
                            currryentLongtitude = latlng.longitude;
                            geoCoder.reverseGeoCode(result);

                        }
                    }

                }
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 返回
            case R.id.imb_title_bar_back:
                this.finish();
                break;
            // 确定
            case R.id.btn_confirmCoordinate:
                Intent intent = new Intent();
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("zoom", zoom);
                setResult(RESULT_OK, intent);
                this.finish();
                break;

            default:
                break;
        }

    }
    private void initData() {
        {
            mBaiduMap = mMapView.getMap();
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
            mBaiduMap.setMyLocationEnabled(true);
            UiSettings uiSettings = mBaiduMap.getUiSettings();
            uiSettings.setZoomGesturesEnabled(true);
            mLocationClient = new LocationClient(mContext);
            mLocationClient.registerLocationListener(mLocListener);
            LocationClientOption opt = new LocationClientOption();
            opt.setIsNeedAddress(true);
            opt.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            opt.setCoorType("bd09ll");
            mLocationClient.setLocOption(opt);
            opt.setOpenGps(true);
            opt.setAddrType("all");
            mLocationClient.start();
            mLocationClient.requestLocation();
            // 根据经纬度获取当前详细地址
            reverseCode = new ReverseGeoCodeOption();
            geoCoder = GeoCoder.newInstance();
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
                    String locationgCurrent = result.getAddressDetail().district
                            + result.getAddressDetail().street
                            + result.getAddressDetail().streetNumber + "";
                    if (locationgCurrent.length() >= 15) {
                        tv_locationText.setText(locationgCurrent.subSequence(0, 9)
                                + "...");
                    } else {
                        tv_locationText.setText(locationgCurrent);
                    }
                }

                @Override
                public void onGetGeoCodeResult(GeoCodeResult result) {

                }
            });
            // 根据经纬度定位到当前位置
            if (latitude != -1) {
                LatLng center = new LatLng(latitude, longitude);
                MapStatus mapStatus = new MapStatus.Builder().target(center)
                        .zoom(zoom).build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                        .newMapStatus(mapStatus);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                result = reverseCode.location(new LatLng(latitude, longitude));
                curryentLatitude = latitude;
                currryentLongtitude = latitude;
                geoCoder.reverseGeoCode(result);
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mLocationClient.unRegisterLocationListener(mLocListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    private class MylocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation loc) {
            if (null == loc) {
                return;
            }
            myBDLocation = loc;
            LatLng point = new LatLng(loc.getLatitude(), loc.getLongitude());
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.location);
            OverlayOptions opt = new MarkerOptions().position(point)
                    .icon(bitmap).zIndex(9).draggable(true);
            mBaiduMap.addOverlay(opt);
            if (longitude != -1) {
                LatLng center = new LatLng(latitude, longitude);
                MapStatus mapStatus = new MapStatus.Builder().target(center)
                        .zoom(zoom).build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                        .newMapStatus(mapStatus);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                result = reverseCode.location(new LatLng(latitude, longitude));
                curryentLatitude = latitude;
                currryentLongtitude = longitude;
                geoCoder.reverseGeoCode(result);

            } else {
                LatLng center = new LatLng(loc.getLatitude(), loc.getLongitude());
                MapStatus mapStatus = new MapStatus.Builder().target(center).zoom(19)
                        .build();
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                        .newMapStatus(mapStatus);
                mBaiduMap.setMapStatus(mapStatusUpdate);
                result = reverseCode.location(new LatLng(loc.getLatitude(), loc.getLongitude()));
                curryentLatitude = latitude;
                currryentLongtitude = longitude;
                geoCoder.reverseGeoCode(result);
            }
        }

    }
}

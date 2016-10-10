package com.HyKj.UKeBao.view.activity.login.joinAlliance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.baoyz.actionsheet.ActionSheet;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.BusinessInfo;
import com.HyKj.UKeBao.model.login.joinAlliance.SettledAllianceModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.ChooseCity.ChooseCityActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.IndustryType.IndustryTypeActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreCoord.StoreCoordActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreSignage.StoreSignageActivity;
import com.HyKj.UKeBao.view.activity.login.joinAlliance.VerifyInfo.VerifyInfoActivity;
import com.HyKj.UKeBao.viewModel.login.joinAlliance.SettledAllianceViewModel;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 入驻联盟填写资料页面
 * Created by Administrator on 2016/8/25.
 */
public class SettledAllianceActivity extends BaseActiviy implements View.OnClickListener {
    private DisplayImageOptions options;

    private EditText storeNameInput;

    private EditText contactsNameInput;

    private EditText contactsNumberInput;

    private EditText regisNumberInput;

    private TextView tv_title;

    private TextView tv_industryType_detail;

    private TextView tv_industryType;

    private TextView tv_chooseCity_detail;

    private TextView tv_storeSignage_status;

    private TextView tv_setCoordinate;

    private TextView tv_city;

    private TextView review_feedback;

    private ImageButton ib_back;

    private ImageView iv_identityCard_obverse;

    private ImageView iv_businessLicense;

    private ImageView iv_identityCard_reverse;

    private Button bt_next;

    private RelativeLayout setStoreSignage;

    private RelativeLayout chooseCity;

    private RelativeLayout setStoreCoordinate;

    private RelativeLayout chooseIndustryType;

    private LinearLayout ll_feedBack;

    private List<PhotoInfo> mPhotoList;

    private final int REQUEST_CODE_CAMERA = 1000;

    private final int REQUEST_CODE_GALLERY = 1001;

    private int photoNo = 9;//判断点击哪张相片标记（0：上传营业执照  1：手持身份证  2：身份证背面）

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    public BusinessInfo businessInfo;

    private float mapZoom = 19;

    public boolean hasSetCoordinate;

    public final static int INDUSTRY_TPYE = 0;

    public final static int CHOOSE_CITY = 1;

    public final static int SET_COORDINATE = 2;

    public final static int SET_SIGNAGE = 3;

    public final static int COMMIT_APPLY = 4;

    public final static int UPLOAD_PICTURE = 5;

    public SettledAllianceViewModel viewModel;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, SettledAllianceActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mPhotoList = new ArrayList<>();

        setContentView(R.layout.activity_settled_alliance);

        bt_next = (Button) findViewById(R.id.btn_fillNext);

        storeNameInput = (EditText) findViewById(R.id.et_storeName_input);

        contactsNameInput = (EditText) findViewById(R.id.et_contactsName_input);

        contactsNumberInput = (EditText) findViewById(R.id.et_contactsNumber_input);

        regisNumberInput = (EditText) findViewById(R.id.et_regisNumber_input);

        tv_title = (TextView) findViewById(R.id.tv_title_bar_name);

        tv_city = (TextView) findViewById(R.id.tv_city);

        tv_industryType = (TextView) findViewById(R.id.tv_industryType);

        tv_chooseCity_detail = (TextView) findViewById(R.id.tv_chooseCity_detail);

        tv_setCoordinate = (TextView) findViewById(R.id.tv_setCoordinate);//坐标栏textview

        tv_industryType_detail = (TextView) findViewById(R.id.tv_industryType_detail);

        tv_storeSignage_status = (TextView) findViewById(R.id.tv_storeSignage_status);

        review_feedback = (TextView) findViewById(R.id.review_feedback);

        ib_back = (ImageButton) findViewById(R.id.imb_title_bar_back);

        iv_businessLicense = (ImageView) findViewById(R.id.iv_businessLicense);//上传营业执照

        iv_identityCard_obverse = (ImageView) findViewById(R.id.iv_identityCard_obverse);//上传手持身份证照

        iv_identityCard_reverse = (ImageView) findViewById(R.id.iv_identityCard_reverse);//上传身份证背面照

        chooseIndustryType = (RelativeLayout) findViewById(R.id.rl_industryType);//行业类型布局

        viewModel = new SettledAllianceViewModel(new SettledAllianceModel(), this);

        chooseCity = (RelativeLayout) findViewById(R.id.rl_chooseCity);

        setStoreCoordinate = (RelativeLayout) findViewById(R.id.rl_storeCoordinate);

        setStoreSignage = (RelativeLayout) findViewById(R.id.rl_storeSignage);

        ll_feedBack = (LinearLayout) findViewById(R.id.ll_feedBack);


        businessInfo = new BusinessInfo();

        initGalleryFinal();

        String feedBack=getIntent().getStringExtra("feedBack");

        if (!TextUtils.isEmpty(feedBack)){
            BufferCircleDialog.show(SettledAllianceActivity.this,"正在获取数据...",false,null);

            //当审核不成功时，来到该页面时，请求商家数据
            ll_feedBack.setVisibility(View.VISIBLE);

            review_feedback.setText(feedBack);

            viewModel.getBusinessInfo();
        }
    }

    @Override
    public void setUpView() {
        tv_title.setText("加入联盟");

        businessInfo.businessStoreImages=new ArrayList<>();

        businessInfo.businessStoreImages.add(0,null);

        businessInfo.identityPicture=new ArrayList<>(3);

        businessInfo.identityPicture.add(0,null);

        businessInfo.identityPicture.add(1,null);

        businessInfo.identityPicture.add(2,null);

        PhotoInfo photoInfo=new PhotoInfo();

        mPhotoList.add(0,photoInfo);

        mPhotoList.add(1,photoInfo);

        mPhotoList.add(2,photoInfo);

    }

    @Override
    public void setListeners() {

        //设置返回按钮监听
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chooseIndustryType.setOnClickListener(this);

        chooseCity.setOnClickListener(this);

        setStoreSignage.setOnClickListener(this);

        setStoreCoordinate.setOnClickListener(this);

        iv_businessLicense.setOnClickListener(this);

        iv_identityCard_obverse.setOnClickListener(this);

        iv_identityCard_reverse.setOnClickListener(this);

        bt_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //选择行业类型
            case R.id.rl_industryType:
                startActivityForResult(IndustryTypeActivity.getStartIntent(this), INDUSTRY_TPYE);
                break;
            //选择商店坐标
            case R.id.rl_storeCoordinate:
                Intent my_intent = StoreCoordActivity.getStartIntent(this);
                if (hasSetCoordinate) {
                    my_intent.putExtra("latitude", businessInfo.latitude);
                    my_intent.putExtra("longitude", businessInfo.longitude);
                    my_intent.putExtra("zoom", mapZoom);
                }
                startActivityForResult(my_intent, SET_COORDINATE);
                break;
            //选择商店招牌
            case R.id.rl_storeSignage:
                Intent mIntent = StoreSignageActivity.getStartIntent(this);
                if (businessInfo.businessStoreImages != null) {
                    if (businessInfo.businessStoreImages.size() != 0) {
                        mIntent.putExtra("businessStoreImages",
                                businessInfo.businessStoreImages.get(0));
                    }
                }
                startActivityForResult(mIntent, UPLOAD_PICTURE);
                break;
            //选择城市
            case R.id.rl_chooseCity:

                Intent intent = ChooseCityActivity.getStartIntent(this);

                intent.putExtra("province", businessInfo.province);

                intent.putExtra("city", businessInfo.city);

                intent.putExtra("area", businessInfo.area);

                intent.putExtra("address", businessInfo.address);

                startActivityForResult(intent, CHOOSE_CITY);

                break;
            //上传营业执照
            case R.id.iv_businessLicense:
                go();//弹出冒泡框选择跳转方向
                photoNo = 0;
                break;
            //手持身份证
            case R.id.iv_identityCard_obverse:
                go();//弹出冒泡框选择跳转方向
                photoNo = 1;
                break;
            //身份证背面
            case R.id.iv_identityCard_reverse:
                go();//弹出冒泡框选择跳转方向
                photoNo = 2;
                break;
            //下一步
            case R.id.btn_fillNext:
                //拿到EditText中的数据(店铺姓名，注册工商号，联系电话，联系人)
                BufferCircleDialog.show(SettledAllianceActivity.this,"请稍候....",false,null);
                if(TextUtils.isEmpty(getIntent().getStringExtra("feedBack"))) {
                    String name = storeNameInput.getText().toString();

                    String contacts_name = contactsNameInput.getText().toString();

                    String phone_num = contactsNumberInput.getText().toString();

                    String regis_number = regisNumberInput.getText().toString();

                    viewModel.submitData(name, contacts_name, phone_num, regis_number, businessInfo, hasSetCoordinate, mPhotoList);
                }

                if (businessInfo.identityPicture.get(0)!=null&&businessInfo.identityPicture.get(1)!=null&&businessInfo.identityPicture.get(2)!=null) {
                    Intent next_intent = VerifyInfoActivity.getStartIntent(this);

                    businessInfo.identityPicture.set(0,mPhotoList.get(0).getPhotoPath());

                    businessInfo.identityPicture.set(1,mPhotoList.get(1).getPhotoPath());

                    businessInfo.identityPicture.set(2,mPhotoList.get(2).getPhotoPath());

                    next_intent.putExtra("businessInfo", businessInfo);

                    next_intent.putExtra("feedBack",getIntent().getStringExtra("feedBack"));

                    startActivityForResult(next_intent, COMMIT_APPLY);

                    BufferCircleDialog.dialogcancel();
                }
                break;
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //获取店铺数据成功后加载到页面上
    public void setData() {
        hasSetCoordinate = true;

        storeNameInput.setText(businessInfo.businessName);

        contactsNameInput.setText(businessInfo.name);

        contactsNumberInput.setText(businessInfo.tel);

        regisNumberInput.setText(businessInfo.businessRegistrationNo);

        if (businessInfo.identityPicture.size() == 3) {
            Picasso.with(this).load(businessInfo.identityPicture.get(0)).into(iv_businessLicense);

            Picasso.with(this).load(businessInfo.identityPicture.get(1)).into(iv_identityCard_obverse);

            Picasso.with(this).load(businessInfo.identityPicture.get(2)).into(iv_identityCard_reverse);
        }
        if (businessInfo.businessStoreImages != null) {
            tv_storeSignage_status.setText("已上传");
        }
        tv_industryType_detail.setText(businessInfo.ptype + "-"
                + businessInfo.stype);
        tv_chooseCity_detail
                .setText(businessInfo.province
                        + businessInfo.city + businessInfo.area + businessInfo.address);
        tv_city.setText("已选择");
        tv_industryType.setText("已选择");
        if (businessInfo.latitude != -1) {
            tv_setCoordinate.setText("已设置");
        }
        BufferCircleDialog.dialogcancel();
    }

    //初始化GalleryFinal
    public void initGalleryFinal() {
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(false)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .build();

        //配置imageloader
        imageloade = new PicassoImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloade, MyApplication.themeConfig)
                .setFunctionConfig(functionConfig)
                .setNoAnimcation(false)
                .setPauseOnScrollListener(MyApplication.pauseOnScrollListener)
                .build();

        GalleryFinal.init(coreConfig);
    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                options = new DisplayImageOptions.Builder()
                        .showImageOnFail(R.drawable.ic_gf_default_photo)
                        .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
                        .showImageOnLoading(R.drawable.ic_gf_default_photo).build();
                switch (photoNo) {
                    case 0:
                        mPhotoList.set(photoNo, resultList.get(0));
                        LogUtil.d("文件路径为:" + mPhotoList.get(photoNo).getPhotoPath());
                        ImageLoader.getInstance().displayImage("file:/" + mPhotoList.get(photoNo).getPhotoPath(), iv_businessLicense, options);
                        break;
                    case 1:
                        mPhotoList.set(photoNo, resultList.get(0));
                        LogUtil.d("文件路径为:" + mPhotoList.get(photoNo).getPhotoPath());
                        ImageLoader.getInstance().displayImage("file:/" + mPhotoList.get(photoNo).getPhotoPath(), iv_identityCard_obverse, options);
                        break;
                    case 2:
                        mPhotoList.set(photoNo, resultList.get(0));
                        LogUtil.d("文件路径为:" + mPhotoList.get(photoNo).getPhotoPath());
                        ImageLoader.getInstance().displayImage("file:/" + mPhotoList.get(photoNo).getPhotoPath(), iv_identityCard_reverse, options);
                        break;
                }
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(SettledAllianceActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            LogUtil.d("错误..........");
        }
    };


    //弹出对话框
    public void go() {
        ActionSheet.createBuilder(SettledAllianceActivity.this, getSupportFragmentManager())
                .setOtherButtonTitles("打开相册(Open Gallery)", "拍照(Camera)")
                .setCancelableOnTouchOutside(true)
                .setListener(new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        switch (index) {
                            case 0:
                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                                break;
                            case 1:
                                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
                                break;
                        }
                    }
                }).show();
        initImageLoader(this);
        initFresco();
    }

    //初始化图片加载器
    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    //初始化图像
    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // 行业类型
        if (resultCode == RESULT_OK
                && requestCode == INDUSTRY_TPYE) {
            // businessInfo.industryType = data.getStringExtra("industryType");

            businessInfo.category = data.getIntExtra("category", -1);

            businessInfo.ptype = data.getStringExtra("parentName");

            businessInfo.stype = data.getStringExtra("industryType");

            businessInfo.businessDiscount = data.getIntExtra("discount", -1);

            businessInfo.integral = data.getIntExtra("integral", 0);

            tv_industryType_detail.setText(businessInfo.ptype + "-"
                    + businessInfo.stype);

            tv_industryType.setText("已选择");
        }
        // 选择城市
        else if (resultCode == RESULT_OK
                && requestCode == CHOOSE_CITY) {
            String area = data.getStringExtra("area");

            String areaDetail = data.getStringExtra("areaDetail");

            String provinceName = data.getStringExtra("provinceName");

            String cityName = data.getStringExtra("cityName");

            businessInfo.address = areaDetail;

            businessInfo.area = area;

            businessInfo.province = provinceName;

            businessInfo.city = cityName;

            tv_chooseCity_detail.setText(provinceName + cityName);

            tv_city.setText("已选择");
        }
        //上传图片
        else if (resultCode == RESULT_OK && requestCode == UPLOAD_PICTURE) {

            String imagePrefix = data.getStringExtra("imagePrefix");

            String imageSrc = data.getStringExtra("imageSrc");

            if (businessInfo.businessStoreImages == null) {
                businessInfo.businessStoreImages = new ArrayList<String>();
            }

            businessInfo.businessStoreImages.add(0, imagePrefix + imageSrc);

            tv_storeSignage_status.setText("已上传");
        }
        //设置坐标
        else if (resultCode == RESULT_OK && requestCode == SET_COORDINATE) {
            tv_setCoordinate.setText("已设置");

            businessInfo.latitude = data.getExtras().getDouble("latitude");

            businessInfo.longitude = data.getExtras().getDouble("longitude");

            mapZoom = data.getExtras().getFloat("zoom");

            hasSetCoordinate = true;
        }
        //提交
        else if (resultCode == RESULT_OK && requestCode == COMMIT_APPLY) {
            finish();
        }
    }
}

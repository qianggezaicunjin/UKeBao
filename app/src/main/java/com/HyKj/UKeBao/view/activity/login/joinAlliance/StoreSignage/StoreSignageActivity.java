package com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreSignage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.baoyz.actionsheet.ActionSheet;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.joinAlliance.StoreSignage.StoreSignageModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.viewModel.login.joinAlliance.StoreSignage.StoreSignageViewModel;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 店铺招牌页面
 * Created by Administrator on 2016/8/30.
 */
public class StoreSignageActivity extends BaseActiviy implements View.OnClickListener {

    private ImageView iv_chooseSignage;

    private Button btn_uploadPhoto;

    private final int REQUEST_CODE_CAMERA = 1000;

    private final int REQUEST_CODE_GALLERY = 1001;

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    private PhotoInfo phoneInfo;//图片路径

    private StoreSignageViewModel viewModel;

    private String businessStoreImages;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, StoreSignageActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_store_signage);

        iv_chooseSignage = (ImageView) findViewById(R.id.iv_choose_signIcon);

        btn_uploadPhoto= (Button) findViewById(R.id.btn_uploadPhoto);

        viewModel=new StoreSignageViewModel(new StoreSignageModel(),this);

        phoneInfo=new PhotoInfo();

        businessStoreImages = getIntent().getStringExtra("businessStoreImages");

        LogUtil.d("店铺招牌图片数据"+businessStoreImages);

        if (businessStoreImages != null
                && !TextUtils.isEmpty(businessStoreImages)) {
            Picasso.with(this)
                    .load(businessStoreImages)
                    .into(iv_chooseSignage);
        }
    }

    @Override
    public void setUpView() {
        setTitleTheme("上传图片");

        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(false)
                .setEnableEdit(true)
                .setEnableCrop(true)
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

    @Override
    public void setListeners() {
        iv_chooseSignage.setOnClickListener(this);
        btn_uploadPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加图片按钮
            case R.id.iv_choose_signIcon:
                go();
                break;
            case R.id.btn_uploadPhoto:
                //提交图片到服务器
                if (!TextUtils.isEmpty(phoneInfo.getPhotoPath())) {
                    LogUtil.d("点击了提交按钮,图片路径为"+phoneInfo.getPhotoPath());
                    try {
                        BufferCircleDialog.show(StoreSignageActivity.this,"正在上传..",false,null);
                        viewModel.uploadPictures(new File(phoneInfo.getPhotoPath()), 2);
                    }catch (Exception exception){
                        LogUtil.d("文件传输错误");
                    }

                }else {
                    toast("请添加一张图片~");
                }
        }
    }

    //弹出对话框
    public void go() {
        ActionSheet.createBuilder(StoreSignageActivity.this, getSupportFragmentManager())
                .setCancelButtonTitle("取消(Cancel)")
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

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .showImageOnFail(R.drawable.ic_gf_default_photo)
                        .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
                        .showImageOnLoading(R.drawable.ic_gf_default_photo).build();
                phoneInfo = resultList.get(0);
                LogUtil.d("文件路径为:" + phoneInfo.getPhotoPath());
                ImageLoader.getInstance().displayImage("file:/" + phoneInfo.getPhotoPath(), iv_chooseSignage, options);

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(StoreSignageActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            LogUtil.d("错误..........");
        }
    };

    /**
     * 设置返回参数
     */
    public  void setBackResult() {
        String imagePrefix=viewModel.storeSignage.getRows().imagePrefix;
        String imageSrc=viewModel.storeSignage.getRows().imageSrc;
        if (imagePrefix != null && imageSrc != null) {
            Intent intent = new Intent();

            intent.putExtra("imagePrefix", imagePrefix);

            intent.putExtra("imageSrc", imageSrc);

            setResult(RESULT_OK, intent);

            BufferCircleDialog.dialogcancel();

            finish();
        }
    }
    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}

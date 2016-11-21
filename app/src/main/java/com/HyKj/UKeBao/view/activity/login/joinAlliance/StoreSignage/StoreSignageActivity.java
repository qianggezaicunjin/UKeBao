package com.HyKj.UKeBao.view.activity.login.joinAlliance.StoreSignage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.HyKj.UKeBao.util.ImageCacheUtils;
import com.HyKj.UKeBao.util.SaveBitMapToSD;
import com.HyKj.UKeBao.view.customView.SelectPhotoDialog;
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
import java.io.FileNotFoundException;
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

    private static final int FLAG_CHOOSE_IMG = 0x11;
    private static final int FLAG_MODIFY_FINISH = 7;
    private static final int FLAG_CHOOSE_CAMERA = 0x17;
    private static int PHOTO_REQUEST_CUT = 0x88;

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    private PhotoInfo phoneInfo;//图片路径

    private StoreSignageViewModel viewModel;

    private String businessStoreImages;

    private SelectPhotoDialog photoview;

    private Bitmap photo;

    private Uri uritempFile;

    private String uploadFilePath;

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

        photoview = new SelectPhotoDialog(this);

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

//        //配置功能
//        functionConfig = new FunctionConfig.Builder()
//                .setEnableCamera(false)
//                .setEnableEdit(true)
//                .setEnableCrop(true)
//                .setEnableRotate(true)
//                .setCropSquare(true)
//                .setEnablePreview(true)
//                .build();
//
//        //配置imageloader
//        imageloade = new PicassoImageLoader();
//        CoreConfig coreConfig = new CoreConfig.Builder(this, imageloade, MyApplication.themeConfig)
//                .setFunctionConfig(functionConfig)
//                .setNoAnimcation(false)
//                .setPauseOnScrollListener(MyApplication.pauseOnScrollListener)
//                .build();
//
//        GalleryFinal.init(coreConfig);

    }

    @Override
    public void setListeners() {
        iv_chooseSignage.setOnClickListener(this);
        btn_uploadPhoto.setOnClickListener(this);
        photoview.btn_qx.setOnClickListener(this);
        photoview.btn_pz.setOnClickListener(this);
        photoview.btn_xc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //添加图片按钮
            case R.id.iv_choose_signIcon:
                photoview.show();
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

                break;
                // 拍照
            case R.id.btn_pz:
                photoview.dismiss();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg")));
                startActivityForResult(intent, FLAG_CHOOSE_CAMERA);

                break;
            case R.id.btn_xc:
                photoview.dismiss();
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, FLAG_CHOOSE_IMG);

                break;
        }
    }

    //弹出对话框
//    public void go() {
//        ActionSheet.createBuilder(StoreSignageActivity.this, getSupportFragmentManager())
//                .setCancelButtonTitle("取消(Cancel)")
//                .setOtherButtonTitles("打开相册(Open Gallery)", "拍照(Camera)")
//                .setCancelableOnTouchOutside(true)
//                .setListener(new ActionSheet.ActionSheetListener() {
//                    @Override
//                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {
//
//                    }
//
//                    @Override
//                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
//                        switch (index) {
//                            case 0:
//                                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
//                                break;
//                            case 1:
//                                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
//                                break;
//                        }
//                    }
//                }).show();
//        initImageLoader(this);
//        initFresco();
//    }

//    //初始化图片加载器
//    private void initImageLoader(Context context) {
//        // This configuration tuning is custom. You can tune every option, you may tune some of them,
//        // or you can create default configuration by
//        //  ImageLoaderConfiguration.createDefault(this);
//        // method.
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
//
//        // Initialize ImageLoader with configuration.
//        ImageLoader.getInstance().init(config.build());
//    }
//
//    //初始化图像
//    private void initFresco() {
//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setBitmapsConfig(Bitmap.Config.RGB_565)
//                .build();
//        Fresco.initialize(this, config);
//    }
//
//    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
//        @Override
//        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//            if (resultList != null) {
//                DisplayImageOptions options = new DisplayImageOptions.Builder()
//                        .showImageOnFail(R.drawable.ic_gf_default_photo)
//                        .showImageForEmptyUri(R.drawable.ic_gf_default_photo)
//                        .showImageOnLoading(R.drawable.ic_gf_default_photo).build();
//                phoneInfo = resultList.get(0);
//                LogUtil.d("文件路径为:" + phoneInfo.getPhotoPath());
//                ImageLoader.getInstance().displayImage("file:/" + phoneInfo.getPhotoPath(), iv_chooseSignage, options);
//
//            }
//        }
//
//        @Override
//        public void onHanlderFailure(int requestCode, String errorMsg) {
//            Toast.makeText(StoreSignageActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
//            LogUtil.d("错误..........");
//        }
//    };

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
            imageCut(data.getData());

        } else if (requestCode == FLAG_CHOOSE_CAMERA && resultCode == RESULT_OK) {
            File temp = new File(Environment.getExternalStorageDirectory()
                    + "/test.jpg");
            imageCut(Uri.fromFile(temp));

        } else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {

            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                }
                String path = SaveBitMapToSD.saveBitmap(photo, StoreSignageActivity.this)
                        .getPath();
                iv_chooseSignage.setImageBitmap(ImageCacheUtils
                        .decodeBitmap(path));
                phoneInfo.setPhotoPath(path) ;
            }
        } else if (requestCode == PHOTO_REQUEST_CUT && resultCode == RESULT_OK) {
            // 将Uri图片转换为Bitmap
            try {
                //获取uri的路径
//				uploadFilePath = getRealFilePath(mContext, uritempFile);
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                // 将裁剪的bitmap显示在imageview控件上
                String path = SaveBitMapToSD.saveBitmap(bitmap, this)
                        .getPath();
                Log.d("上传图片得路径为", path);
                iv_chooseSignage.setImageBitmap(ImageCacheUtils
                        .decodeBitmap(path));
                phoneInfo.setPhotoPath(path) ;
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    private void imageCut(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 开启裁剪功能
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        // aspectX aspectY 是宽高的比例,2:1
        intent.putExtra("aspectX", 2);

        // outputX outputY 是裁剪图片宽高
        intent.putExtra("aspectY", 1);

        intent.putExtra("aspectX", 440);

        intent.putExtra("aspectY", 267);

        // intent.putExtra("outputX", 440);
        // intent.putExtra("outputY", 267);
        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故将图片保存在Uri中，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        // intent.putExtra("return-data", true);

        // uritempFile为Uri类变量，实例化uritempFile
        uritempFile = Uri.parse("file://" + "/"
                + Environment.getExternalStorageDirectory().getPath() + "/"
                + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, PHOTO_REQUEST_CUT);

    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}

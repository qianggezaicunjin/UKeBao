package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessstoreImageBinding;
import com.HyKj.UKeBao.model.bean.RecycleViewBaen;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreImageModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.util.databinding.DatabindingAdapter;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.MyRecycleViewAdapter;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessStoreImageViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 店铺相册页面
 * Created by Administrator on 2016/10/11.
 */
public class BusinessStoreImageActivity extends BaseActiviy {

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BusinessStoreImageActivity.class);

        return intent;
    }
    private ActivityBusinessstoreImageBinding mBinding;

    private BusinessStoreImageViewModel viewModel;

    public RecycleViewBaen bean = new RecycleViewBaen();

    public FunctionConfig functionConfig;

    public  cn.finalteam.galleryfinal.ImageLoader imageloade;

    public List<String> data;//店铺照片集合

    private MyRecycleViewAdapter adapter;

    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 0x16;//请求码，自己定义

    private int position=0;//需要添加照片的下标

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_businessstore_image);

        viewModel = new BusinessStoreImageViewModel(this, new BusinessStoreImageModel());

        //获取图片数据
        Intent intent = getIntent();

        initRecycleView(intent);

        mBinding.setView(this);

        mBinding.setViewModel(viewModel);

        //照片数量
        viewModel.refresh(data);
    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺相册");
    }

    @Override
    public void setListeners() {
        //上传图片
        mBinding.btImageToWebStorePhotoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.updataImage(data);
            }
        });
    }

    //初始化RecycleView
    public void initRecycleView(Intent intent) {
        data = viewModel.isAddImage((List<String>) intent.getSerializableExtra("pictures"));

        adapter = new MyRecycleViewAdapter(data, this);

        bean.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyRecycleViewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                switch (v.getId()) {
                    case R.id.delephoto:
                        BufferCircleDialog.show(BusinessStoreImageActivity.this,"正在执行删除操作,请稍候~",false,null);

                        data = adapter.removeData(postion);

                        viewModel.refresh(data);

                        BufferCircleDialog.dialogcancel();

                        break;
                    case R.id.addphoto:
                        position=postion;

                        //检查权限
                        if (ContextCompat.checkSelfPermission(BusinessStoreImageActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            //如果没有授权，则请求授权
                            ActivityCompat.requestPermissions(BusinessStoreImageActivity.this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
                        } else {
                            //有授权，直接开启摄像头
                            initGalleryFinal();

                            viewModel.addData(adapter, postion, data, functionConfig, mOnHanlderResultCallback);
                        }
                }
            }
        });

        bean.setNum(3);//设置每行显示图片数

        bean.setmContext(this);

        //设置RecycleView的显示模式
        bean.setMode(DatabindingAdapter.GIRDVIEW);
    }

    //初始化GalleryFinal
    public void initGalleryFinal() {
        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setMutiSelectMaxSize(6 - data.size())
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

    //选择相册图片后回调
    public GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            //添加相册或者相机图片到RecycleView中
            data = viewModel.addLocalImage(data, resultList);

            viewModel.refresh(data);

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    public void updataSuccess(List<String> pictureList) {
        Intent intent = new Intent();

        intent.putStringArrayListExtra("pictures", (ArrayList<String>) pictureList);

        LogUtil.d("店铺相册dataList:"+pictureList.toString());

        setResult(RESULT_OK, intent);

        toast("上传成功!",this);

        BufferCircleDialog.dialogcancel();

        finish();
    }


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //判断请求码
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_CAMERA) {
            //grantResults授权结果
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //成功，开启摄像头
                initGalleryFinal();

                viewModel.addData(adapter, position, data, functionConfig, mOnHanlderResultCallback);
            } else {
                //授权失败
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}

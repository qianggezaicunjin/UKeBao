package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.Bindable;
import android.databinding.DataBindingUtil;
import android.view.View;

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
                        initGalleryFinal();

                        viewModel.addData(adapter, postion, data, functionConfig, mOnHanlderResultCallback);
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
}

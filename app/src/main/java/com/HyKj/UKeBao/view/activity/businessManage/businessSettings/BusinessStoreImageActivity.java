package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessstoreImageBinding;
import com.HyKj.UKeBao.model.bean.RecycleViewBaen;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreImageModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.PicassoImageLoader;
import com.HyKj.UKeBao.util.databinding.DatabindingAdapter;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.MyRecycleViewAdapter;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessStoreImageViewModel;

import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 店铺相册页面
 * Created by Administrator on 2016/10/11.
 */
public class BusinessStoreImageActivity extends BaseActiviy{

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,BusinessStoreImageActivity.class);

        return intent;
    }

    private ActivityBusinessstoreImageBinding mBinding;

    private BusinessStoreImageViewModel viewModel;

    public RecycleViewBaen bean=new RecycleViewBaen();

    private FunctionConfig functionConfig;

    private cn.finalteam.galleryfinal.ImageLoader imageloade;

    private List<String> data;

    private MyRecycleViewAdapter adapter;
    @Override
    public void onCreateBinding() {
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_businessstore_image);

        viewModel=new BusinessStoreImageViewModel(this,new BusinessStoreImageModel());

        //获取图片数据
        Intent intent=getIntent();

        initRecycleView(intent);

        mBinding.setView(this);
    }

    @Override
    public void setUpView() {
        setTitleTheme("店铺相册");
    }

    @Override
    public void setListeners() {

    }

    //初始化RecycleView
    private void initRecycleView(Intent intent) {
        data=viewModel.isAddImage((List<String>) intent.getSerializableExtra("pictures"));

        adapter=new MyRecycleViewAdapter(data,this);

        bean.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyRecycleViewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                switch (v.getId()){
                    case R.id.delephoto:
                        data=adapter.removeData(postion);
                        break;
                    case R.id.addphoto:
                        initGalleryFinal();

                        viewModel.addData(adapter,postion,data,functionConfig,mOnHanlderResultCallback);
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
                .setMutiSelectMaxSize(6-data.size())
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
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback= new GalleryFinal.OnHanlderResultCallback(){
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            //添加相册或者相机图片
            data=viewModel.addLocalImage(data,resultList);

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };
}

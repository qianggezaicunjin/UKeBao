package com.HyKj.UKeBao.view.activity.businessManage.businessSettings;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.EditText;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.databinding.ActivityBusinessstoreGoodsImagesBinding;
import com.HyKj.UKeBao.model.businessManage.businessSettings.BusinessStoreGoodsModel;
import com.HyKj.UKeBao.util.BufferCircleDialog;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.NetWorkService;
import com.HyKj.UKeBao.util.databinding.DatabindingAdapter;
import com.HyKj.UKeBao.view.adapter.businessManage.businessSettings.GoodsImageRecycleViewAdapter;
import com.HyKj.UKeBao.viewModel.businessManage.businessSettings.BusinessStoreGoodsViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 商品相册
 * Created by Administrator on 2016/11/13.
 */
public class BusinessStoreGoodsActivity extends BusinessStoreImageActivity {
    private BusinessStoreGoodsViewModel viewModel;

    private GoodsImageRecycleViewAdapter adapter;

    private ActivityBusinessstoreGoodsImagesBinding mBinding;

    private List<String> goodsName_list = new ArrayList<>();

    private List<Integer> goodsImageid_list=new ArrayList<>();

    public int addGoods_position;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, BusinessStoreGoodsActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_businessstore_goods_images);

        viewModel = new BusinessStoreGoodsViewModel(this, new BusinessStoreGoodsModel());

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
        setTitleTheme("商品相册");
    }

    @Override
    public void setListeners() {
        //上传图片
        mBinding.btImageToWebStorePhotoActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> updataName_List = new ArrayList<String>();//获取修改后的商品名称集合

                for (int i = 0; i < data.size(); i++) {
                    EditText et_goods_name = (EditText) mBinding.rvBusinessstoreImage.getChildAt(i).findViewById(R.id.et_goodsName);

                    updataName_List.add(et_goods_name.getText().toString());

                    LogUtil.d("goodsName" + et_goods_name.getText().toString());
                }

                //检验用户是否输入正确商品名称
                if (!viewModel.inspect_GoodsName(updataName_List)) {
                    toast("商品名称未输入~", BusinessStoreGoodsActivity.this);

                    return;
                }

                goodsName_list = updataName_List;

                LogUtil.d("delete_List"+adapter.delete_list.toString()+data);

                BufferCircleDialog.show(BusinessStoreGoodsActivity.this, "上传中，请稍候..", false, null);

                viewModel.delete_phone(adapter.delete_list,data);
            }
        });


    }

    //初始化RecycleView
    public void initRecycleView(Intent intent) {
        data = viewModel.isAddImage((List<String>) intent.getSerializableExtra("goods_list"));

        goodsName_list = (List<String>) intent.getSerializableExtra("goodImageName_list");

        goodsImageid_list = (List<Integer>) intent.getSerializableExtra("goodsImageid_list");

        adapter = new GoodsImageRecycleViewAdapter(data, goodsName_list, goodsImageid_list,this);

        bean.setAdapter(adapter);

        adapter.setOnItemClickListener(new GoodsImageRecycleViewAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View v, int postion) {
                switch (v.getId()) {
                    case R.id.goods_delephoto:
                        BufferCircleDialog.show(BusinessStoreGoodsActivity.this,"正在执行删除操作,请稍候~",false,null);

                        data = adapter.removeData(postion);

                        viewModel.refresh(data);

                        LogUtil.d("BusinessStoreGoodsDelete点击了删除按钮");

                        BufferCircleDialog.dialogcancel();

                        break;
                    case R.id.addphoto:
                        initGalleryFinal();

                        addGoods_position = postion;

                        viewModel.addData(adapter, postion, data, functionConfig, mOnHanlderResultCallback);
                }
            }
        });

        bean.setNum(3);//设置每行显示图片数

        bean.setmContext(this);

        //设置RecycleView的显示模式
        bean.setMode(DatabindingAdapter.GIRDVIEW);
    }

    //选择相册图片后回调
    public GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            //添加相册或者相机图片到RecycleView中
            data = viewModel.addLocalImage(data, resultList);

            viewModel.refresh(data);

            for (int i = 0; i < resultList.size(); i++) {
                goodsName_list.add("请输入商品名称");
            }

            adapter.notifyDataSetChanged();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

    public void updataSuccess(List<String> pictureList) {
        Intent intent = new Intent();

        intent.putStringArrayListExtra("updata_goodsImage", (ArrayList<String>) pictureList);

        intent.putStringArrayListExtra("updata_goodsName", (ArrayList<String>) goodsName_list);

        intent.putIntegerArrayListExtra("goodsImageid_list", (ArrayList<Integer>) adapter.goodId_list);

        LogUtil.d("updata_goodsImage"+pictureList);

        LogUtil.d("updata_goodsName"+goodsName_list);

        LogUtil.d("goodsImageid_list"+adapter.goodId_list);

        setResult(RESULT_OK, intent);

        toast("上传成功!", this);

        BufferCircleDialog.dialogcancel();

        finish();
    }
}

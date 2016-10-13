package com.HyKj.UKeBao.util.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.bean.RecycleViewBaen;
import com.HyKj.UKeBao.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/10/7.
 */
public class DatabindingAdapter {
    public  final static int LISTVIEW=0x00;

    public  final static int GIRDVIEW=0x01;

    //ImageVie 设置网络图片
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        LogUtil.d("iamgeUrl" + imageUrl);
        if (imageUrl != null) {
            String tag = imageUrl.substring(0, 4);
            if (tag.equals("http")) {
                Picasso.with(iv.getContext())
                        .load(imageUrl)
                        .placeholder(R.drawable.default_picture)
                        .error(R.drawable.default_picture)
                        .into(iv);
            } else {
                Picasso.with(iv.getContext())
                        .load(new File(imageUrl))
                        .placeholder(R.drawable.default_picture)
                        .config(Bitmap.Config.RGB_565)
                        .into(iv);
            }
        } else {
            Picasso.with(iv.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.default_picture)
                    .error(R.drawable.default_picture)
                    .into(iv);
        }
    }

    //RecycleView的GirdView排列模式
    @BindingAdapter({"setData"})
    public static void setGirdViewData(RecyclerView ry,RecycleViewBaen data){
        switch (data.getMode()){
            case GIRDVIEW:
                ry.setLayoutManager(new GridLayoutManager(data.getmContext(),data.getNum()));

                LogUtil.d("配置GirdView_RecycleView适配器，获取数据:"+data.toString());

                ry.setAdapter(data.getAdapter());

                break;
            case LISTVIEW:
                ry.setLayoutManager(new LinearLayoutManager(data.getmContext()));

                LogUtil.d("配置ListView_RecycleView适配器，获取数据:"+data.toString());

                ry.setAdapter(data.getAdapter());
        }
    }
}

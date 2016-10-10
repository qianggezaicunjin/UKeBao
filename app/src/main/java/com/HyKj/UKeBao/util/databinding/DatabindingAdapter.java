package com.HyKj.UKeBao.util.databinding;

import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/10/7.
 */
public class DatabindingAdapter {
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
}

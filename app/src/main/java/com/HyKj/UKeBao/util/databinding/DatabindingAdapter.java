package com.HyKj.UKeBao.util.databinding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.bean.RecycleViewBaen;
import com.HyKj.UKeBao.model.login.baen.SplashBean;
import com.HyKj.UKeBao.util.CustomToast;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.NetWorkUtils;
import com.HyKj.UKeBao.view.activity.MainActivity;
import com.HyKj.UKeBao.view.activity.login.GuideActivity;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;
import com.HyKj.UKeBao.view.activity.login.SplashActivity;
import com.HyKj.UKeBao.view.activity.marketingManage.PayVipActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2016/10/7.
 */
public class DatabindingAdapter {
    public final static int LISTVIEW = 0x00;

    public final static int GIRDVIEW = 0x01;

    //ImageVie 设置网络图片
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        LogUtil.d("iamgeUrl" + imageUrl);
        if (!TextUtils.isEmpty(imageUrl)) {
            String tag = imageUrl.substring(0, 4);
            if (tag.equals("http")) {
                Picasso.with(iv.getContext())
                        .load(imageUrl)
                        .config(Bitmap.Config.RGB_565)
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
            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.default_1)
                    .showImageForEmptyUri(R.drawable.default_1)
                    .showImageOnFail(R.drawable.default_1).cacheInMemory(true)
                    .cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .displayer(new FadeInBitmapDisplayer(388)).build();

            ImageLoader.getInstance().displayImage(imageUrl, iv, options);
        }
    }

    //RecycleView的排列模式
    @BindingAdapter({"setData"})
    public static void setRecycleViewData(RecyclerView ry, RecycleViewBaen data) {
        switch (data.getMode()) {
            case GIRDVIEW:
                ry.setLayoutManager(new GridLayoutManager(data.getmContext(), data.getNum()));

                LogUtil.d("配置GirdView_RecycleView适配器，获取数据:" + data.toString());

                ry.setAdapter(data.getAdapter());

                break;
            case LISTVIEW:
                ry.setLayoutManager(new LinearLayoutManager(data.getmContext()));

                LogUtil.d("配置ListView_RecycleView适配器，获取数据:" + data.toString());

                ry.setAdapter(data.getAdapter());
        }
    }

    @BindingAdapter({"erroInfo"})
    public static void toastErroInfo(View view, String erroInfo) {
        LogUtil.d("databinding冒泡调用成功");

        if (erroInfo != null) {
            CustomToast.makeText(view.getContext(), erroInfo, Toast.LENGTH_SHORT).show();
        }

    }


    //动态闪屏页
    @BindingAdapter({"background"})
    public static void getbackground(ImageView view, String text) {
        LogUtil.d("进入设置动态背景方法");

        SharedPreferences sp = view.getContext().getSharedPreferences("splash_cache", view.getContext().MODE_PRIVATE);

        String new_imageUrl = sp.getString("new_imageUrl", null);

        if (TextUtils.isEmpty(new_imageUrl)) {
            LogUtil.d("new_imageUrl———__—new_imageUrl——__—new_imageUrl" + new_imageUrl);

            view.setBackgroundResource(R.drawable.guide);
        } else {
            WindowManager wm = (WindowManager) view.getContext().getSystemService(Context.WINDOW_SERVICE);

            LogUtil.d("-----——————————" + new_imageUrl + "——————————");

            //获取屏幕宽
            int width = wm.getDefaultDisplay().getWidth();
            //获取屏幕高
            int height = wm.getDefaultDisplay().getHeight();

            Picasso.with(view.getContext())
                    .load(new_imageUrl + "@" + height + "h_" + width + "w_1e_1c")
                    .error(R.drawable.guide1)
                    .config(Bitmap.Config.RGB_565)
                    .into(view);

            LogUtil.d("闪屏页设置成功:" + width + "height" + height);
        }
    }

    //设置缓存闪屏页
    @BindingAdapter({"setCache"})
    public static void setCacheData(ImageView view, String imageUrl) {
        LogUtil.d("setCache————setCache————setCache");

        //当服务器返回图片地址为空时,清空数据
        if (imageUrl.equals("noting")) {

            SharedPreferences sp = view.getContext().getSharedPreferences("splash_cache", view.getContext().MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.clear();

            editor.commit();
        } else if (!imageUrl.equals("noting") && !TextUtils.isEmpty(imageUrl)) {
            LogUtil.d("setCache————success————success" + imageUrl);

            SharedPreferences sp = view.getContext().getSharedPreferences("splash_cache", view.getContext().MODE_PRIVATE);

            SharedPreferences.Editor editor = sp.edit();

            editor.putString("new_imageUrl", imageUrl);

            editor.commit();
        }
    }

    //判断是否跳转
    @BindingAdapter({"isJump"})
    public static void jump(ImageView view, SplashActivity activity) {
        LogUtil.d("33333333333333333333_______3333333");

        if (activity.viewModel.jump_flag) {

            Context context = activity;

            SplashBean bean = activity.splashBean;

            if (bean.isFirst) {
                //第一次登陆跳转到新手引导页
                context.startActivity(GuideActivity.getStartIntent(context));

                ((SplashActivity) context).finish();

                ((SplashActivity) context).overridePendingTransition(0, 0);
            } else {
                //非第一次登陆，判断是否存在用户信息
                if (!TextUtils.isEmpty(bean.userName) && !TextUtils.isEmpty(bean.userPassword) && !TextUtils.isEmpty(bean.token) && bean.isExamine == 1) {
                    //用户信息存在，判断是否有网络
                    if (NetWorkUtils.networkStatusOK(context)) {
                        MyApplication.token = bean.token;
                        context.startActivity(MainActivity.getStartIntent(context));
                        ((SplashActivity) context).finish();
                    } else {
                        context.startActivity(LoginActivity.getStartIntent(context));

                        Toast.makeText(context, "登陆信息过期~请重新登陆!", Toast.LENGTH_SHORT).show();

                        ((SplashActivity) context).finish();
                    }
                } else {
                    context.startActivity(LoginActivity.getStartIntent(context));

                    ((SplashActivity) context).finish();
                }
            }
        }
    }

    //获取客服电话并跳转到拨号界面
    @BindingAdapter({"callOffice"})
    public static void callOfficePhone(View view, final String phoneNumber) {
        final Context context = view.getContext();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(phoneNumber)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);
                }
            }
        });

    }

    //申请vip资格回调
    @BindingAdapter({"applyVip"})
    public static void applyVip(View view, int orderId) {
        SharedPreferences sp = view.getContext().getSharedPreferences("user_login", view.getContext().MODE_PRIVATE);

        int id = sp.getInt("vipPayId", 0);

        if (orderId != 0 && id == 0) {

            SharedPreferences.Editor editor = sp.edit();

            //vip支付id
            editor.putInt("vipPayId", orderId);

            editor.commit();

            view.getContext().startActivity(PayVipActivity.getStartIntent(view.getContext()));

        } else if (id != 0) {
            view.getContext().startActivity(PayVipActivity.getStartIntent(view.getContext()));
        }
    }
}

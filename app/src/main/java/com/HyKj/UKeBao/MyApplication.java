package com.HyKj.UKeBao;

import android.app.Application;
import android.graphics.Color;

import com.HyKj.UKeBao.util.PicassoPauseOnScrollListener;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;


import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;


/**
 * Created by Administrator on 2016/8/22.
 */
public class MyApplication extends Application {
    public static String token;

    public static String channelId = "";

    public static ThemeConfig themeConfig = null;

    public static PauseOnScrollListener pauseOnScrollListener = null;

    public static boolean flag_pay=true;//判断点击通知栏是否打开订单界面的标记

    public static Application mApplication;

    public static int payTpye=-1;

    @Override
    public void onCreate() {
        super.onCreate();

        //CrashHandler catchHandler = CrashHandler.getInstance();

        //catchHandler.init(getApplicationContext());
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);

        mApplication=this;

        pauseOnScrollListener = new PicassoPauseOnScrollListener(false, true);


        //设置拍照主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(R.color.status_color)
                .setTitleBarTextColor(Color.WHITE)
                .setTitleBarIconColor(Color.WHITE)
                .setFabNornalColor(R.color.status_color)
                .setFabPressedColor(Color.WHITE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .setIconBack(R.drawable.ic_gf_back)
                .setIconRotate(R.drawable.ic_gf_rotate)
                .setIconCamera(R.drawable.ic_gf_camera)
                .build();
        themeConfig = theme;
    }
}

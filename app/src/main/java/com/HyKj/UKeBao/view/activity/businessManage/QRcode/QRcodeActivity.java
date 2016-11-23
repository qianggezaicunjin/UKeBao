package com.HyKj.UKeBao.view.activity.businessManage.QRcode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.data.RetrofitHelp;
import com.HyKj.UKeBao.util.QRCodeUtil;
import com.HyKj.UKeBao.util.SetSizeUtils;
import com.HyKj.UKeBao.view.activity.BaseActiviy;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 二维码扫描
 * Created by Administrator on 2016/11/1.
 */
public class QRcodeActivity extends BaseActiviy{
    private SharedPreferences sp;

    private LinearLayout viewPicture;

    private TextView saveImage_toSD;

    private TextView businessstoreName;

    private ImageButton imb_title_bar_back;

    private TextView textTitle;

    private TextView tv_title_bar_name;

    private ImageView iamge_twoCode;

    private String content;

    private String filePath;//文件存储路径

    private String businessName;//店铺名称

    private String businessImage;//店铺头像

    private boolean canClickFlag=true;

    public static Intent getStartIntent(Context context){
        Intent intent=new Intent(context,QRcodeActivity.class);

        return intent;
    }

    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            canClickFlag=true;
        };
    };
    @Override
    public void onCreateBinding() {
        setContentView(R.layout.activity_two_code);

        sp=getSharedPreferences("user_login", MODE_PRIVATE);

        iamge_twoCode = (ImageView) findViewById(R.id.iamge_twoCode);

        imb_title_bar_back = (ImageButton) findViewById(R.id.imb_title_bar_back);

        tv_title_bar_name = (TextView) findViewById(R.id.tv_title_bar_name);

        saveImage_toSD = (TextView) findViewById(R.id.saveImage_toSD);

        textTitle = (TextView) findViewById(R.id.textTitle_TwoCodeActivity);

        businessstoreName = (TextView) findViewById(R.id.businessstoreName_TwoCodeActivity);

        viewPicture = (LinearLayout) findViewById(R.id.viewPicture);

        String businessId = sp.getString("businessStoreId", "");

        businessName=sp.getString("businessName","");

        businessImage=sp.getString("businessStoreImage","");

        final String url = RetrofitHelp.BASE_URL+"mobile/payingBill.html?id=";

        content = url + businessId;

        filePath = getFileRoot(this) + File.separator + "qr_" + System.currentTimeMillis() + ".jpg";

        TextPaint tp = textTitle.getPaint();

        tp.setFakeBoldText(true);
    }



    @Override
    public void setUpView() {
        setTitleTheme("收款二维码");

        businessstoreName.setText(businessName);

        SetSizeUtils.setSizeWidth(this, iamge_twoCode, 3, 3);
    }

    @Override
    public void setListeners() {
        ImageLoader.getInstance().loadImage(businessImage,new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(
                            String arg0, View arg1) {
                        // TODO Auto-generated method
                        // stub

                    }

                    @Override
                    public void onLoadingFailed(
                            String arg0, View arg1,
                            FailReason arg2) {
                        // TODO Auto-generated method
                        // stub

                    }

                    @Override
                    public void onLoadingComplete(
                            String arg0, View arg1,
                            Bitmap arg2) {
                        // TODO Auto-generated method
                        // stub
                        QRCodeUtil.createQRImage(content,
                                        800, 800, arg2,
                                        filePath);
                        iamge_twoCode
                                .setImageBitmap(BitmapFactory
                                        .decodeFile(filePath));
                        // saveCustomerViewBitmap();
                    }

                    @Override
                    public void onLoadingCancelled(
                            String arg0, View arg1) {
                        // TODO Auto-generated method
                        // stub

                    }
                });

        saveImage_toSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(canClickFlag){
                    saveImageToGallery(QRcodeActivity.this, getViewBitmap(viewPicture));

                    toast("图片保存完成",QRcodeActivity.this);

                    canClickFlag=false;

                    mHandler.sendEmptyMessageDelayed(1, 2500);
                }else{
                    toast("正在保存图片，请稍后...",QRcodeActivity.this);
                }
            }
        });
    }
    //文件存储根目录
    private String getFileRoot(Context context) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File external = context.getExternalFilesDir(null);
            if (external != null) {
                return external.getAbsolutePath();
            }
        }

        return context.getFilesDir().getAbsolutePath();
    }

    // 保存图片
    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "YKB");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + file.getPath())));
    }

    public Bitmap getViewBitmap(View v) {
        v.clearFocus();

        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();

        v.setWillNotCacheDrawing(false);

        int color = v.getDrawingCacheBackgroundColor();

        v.setDrawingCacheBackgroundColor(Color.WHITE);

        if (color != 0) {
            v.destroyDrawingCache();
        }

        v.buildDrawingCache();

        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {

            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        v.destroyDrawingCache();

        v.setWillNotCacheDrawing(willNotCache);

        v.setDrawingCacheBackgroundColor(Color.WHITE);

        return bitmap;
    }
}

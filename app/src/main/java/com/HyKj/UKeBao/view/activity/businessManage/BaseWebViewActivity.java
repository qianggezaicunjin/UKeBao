package com.HyKj.UKeBao.view.activity.businessManage;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.BaseActiviy;

/**
 * Created by Administrator on 2016/9/23.
 */
public class BaseWebViewActivity extends BaseActiviy {
    private String url;

    private String title;

    private WebView mWebView;

    public static Intent getStartIntent(Context context) {

        Intent intent = new Intent(context, BaseWebViewActivity.class);

        return intent;
    }

    @Override
    public void onCreateBinding() {
        setContentView(R.layout.webview_base);

        mWebView= (WebView) findViewById(R.id.base_wv);

        title=getIntent().getExtras().getString("title");

        url=getIntent().getExtras().getString("url");
    }

    @Override
    public void setUpView() {
        //设置标题
        setTitleTheme(title);

        //读取网址
        mWebView.loadUrl(url);

        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                // 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);

        settings.setBlockNetworkImage(false);//是否显示网络图像

        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//设置缓冲模式

        mWebView.getSettings().setBlockNetworkImage(false);
    }

    @Override
    public void setListeners() {

    }
}

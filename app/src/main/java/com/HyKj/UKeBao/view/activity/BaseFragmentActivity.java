package com.HyKj.UKeBao.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.BaseFragmentModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.util.SystemBarUtil;
import com.HyKj.UKeBao.view.activity.businessManage.payrecord.PayRecordActivity;
import com.HyKj.UKeBao.viewModel.BaseFragmentViewModel;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SynthesizerPlayer;
import com.iflytek.speech.SynthesizerPlayerListener;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends FragmentActivity implements SynthesizerPlayerListener {
    private TextView tv_title;

    private ImageButton ib_back;

    private static Context mContext;

    private static BaseFragmentActivity mActivity;

    public static SynthesizerPlayer mSynthesizerPlayer;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemBarUtil.initSystemBar(this);
        // 软键盘弹出问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mContext = this;

        mActivity = this;

        sharedPreferences = getSharedPreferences("user_login", MODE_PRIVATE);

        editor = sharedPreferences.edit();

        onCreateBinding();//初始化绑定视图

        setUpView();//初始化数据

        setListeners();//点击事件
    }


    public abstract void onCreateBinding();

    //初始化使用该方法
    public abstract void setUpView();

    public abstract void setListeners();

    public void toast(String msg) {
    }

    public void toast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }

    public void setTitleTheme(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title_bar_name);

        ib_back = (ImageButton) findViewById(R.id.imb_title_bar_back);

        //更改标题
        tv_title.setText(title);

        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //拿到服务器推送的JSON数据
    private static void getContent(String data) {
        BaseFragmentViewModel viewModel=new BaseFragmentViewModel(new BaseFragmentModel(), mActivity);
        //解析数据并播报推送信息
        viewModel.getContent(data);
    }

    //将接收到的推送消息播放为语音
    public  static void getVoice(String content) {

        String voiceName = "xiaoqi";
        //设置播放声音的人物
        mSynthesizerPlayer.setVoiceName(voiceName);
        //设置播放语速
        mSynthesizerPlayer.setSpeed((40));

        mSynthesizerPlayer.setVolume(50);

        String music = "0";

        mSynthesizerPlayer.setBackgroundSound(music);

        mSynthesizerPlayer.playText(content, null, mActivity);
    }

    public static class MyPushMessageReceiver extends PushMessageReceiver {
        @Override
        public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
            LogUtil.d("百度云推送绑定结果"+i+"设备号为:"+s2);
            //将百度云返回的推送识别id存储起来
            MyApplication.channelId=s2;
        }

        @Override
        public void onUnbind(Context context, int i, String s) {

        }

        @Override
        public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

        }

        @Override
        public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

        }

        @Override
        public void onListTags(Context context, int i, List<String> list, String s) {

        }

        @Override
        public void onMessage(Context context, String s, String s1) {
            LogUtil.d("接收到透传消息"+s);
        }

        @Override
        public void onNotificationClicked(Context context, String s, String s1, String s2) {
            if(MyApplication.flag_pay) {
                Intent mIntent=new Intent();

                mIntent.setAction("CLEAR_NUM");

                mActivity.sendBroadcast(mIntent);

                editor.putInt("message_count", 0);

                editor.commit();

                Intent intent=PayRecordActivity.getStartIntent(mContext);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        }

        @Override
        public void onNotificationArrived(Context context, String s, String s1, String s2) {
            //将接收到的推送消息播放为语音
//            getVoice(s1);//测试用于接收百度控制台推送的消息
            LogUtil.d("接收到服务器消息"+s2);
            getContent(s2);
        }
    }

    @Override
    public void onBufferPercent(int arg0, int arg1, int arg2) {

    }

    @Override
    public void onEnd(SpeechError arg0) {

    }

    @Override
    public void onPlayBegin() {

    }

    @Override
    public void onPlayPaused() {

    }

    @Override
    public void onPlayPercent(int arg0, int arg1, int arg2) {

    }

    @Override
    public void onPlayResumed() {

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        if (null != mSynthesizerPlayer) {
            mSynthesizerPlayer.cancel();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}


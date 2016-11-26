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
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;


import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
@SuppressLint("NewApi")
public abstract class BaseFragmentActivity extends FragmentActivity {
    private TextView tv_title;

    private ImageButton ib_back;

    private static Context mContext;

    private static BaseFragmentActivity mActivity;

    public static SpeechSynthesizer mSynthesizerPlayer;

    private static SharedPreferences sharedPreferences;

    private static SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SystemBarUtil.initSystemBar(this);

        SystemBarUtil.changeColor(R.color.status_color);
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
        BaseFragmentViewModel viewModel = new BaseFragmentViewModel(new BaseFragmentModel(), mActivity);
        //解析数据并播报推送信息
        viewModel.getContent(data);
    }

    //将接收到的推送消息播放为语音
    public static void getVoice(String content) {
        //设置播放声音的人物
        mSynthesizerPlayer.setParameter(SpeechConstant.VOICE_NAME, "xiaoqi");
        //设置播放语速
        mSynthesizerPlayer.setParameter(SpeechConstant.SPEED, "40");
        ////设置音量，范围0~100
        mSynthesizerPlayer.setParameter(SpeechConstant.VOLUME, "80");
        //设置云端
        mSynthesizerPlayer.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        mSynthesizerPlayer.startSpeaking(content, new com.iflytek.cloud.SynthesizerListener() {
            //会话结束回调接口，没有错误时，error为null
            public void onCompleted(SpeechError error) {
            }

            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
            }

            //开始播放
            public void onSpeakBegin() {
            }

            //暂停播放
            public void onSpeakPaused() {
            }

            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
            }

            //恢复播放回调接口
            public void onSpeakResumed() {
            }

            //会话事件回调接口
            public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
            }
        });
    }

    public static class MyPushMessageReceiver extends PushMessageReceiver {
        @Override
        public void onBind(Context context, int i, String s, String s1, String s2, String s3) {
            LogUtil.d("百度云推送绑定结果" + i + "设备号为:" + s2);
            //将百度云返回的推送识别id存储起来
            MyApplication.channelId = s2;
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
            LogUtil.d("接收到透传消息" + s);
        }

        @Override
        public void onNotificationClicked(Context context, String s, String s1, String s2) {
            if (MyApplication.flag_pay) {
                Intent mIntent = new Intent();

                mIntent.setAction("CLEAR_NUM");

                mActivity.sendBroadcast(mIntent);

                editor.putInt("message_count", 0);

                editor.commit();

                Intent intent = PayRecordActivity.getStartIntent(mContext);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(intent);
            }
        }

        @Override
        public void onNotificationArrived(Context context, String s, String s1, String s2) {
            //将接收到的推送消息播放为语音
//            getVoice(s1);//测试用于接收百度控制台推送的消息
            LogUtil.d("接收到服务器消息" + s2);
            getContent(s2);
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (null != mSynthesizerPlayer) {
            mSynthesizerPlayer.destroy();
        }
        super.onDestroy();
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


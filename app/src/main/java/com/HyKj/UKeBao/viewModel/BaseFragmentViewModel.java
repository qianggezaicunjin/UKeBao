package com.HyKj.UKeBao.viewModel;


import android.content.Intent;

import com.HyKj.UKeBao.model.BaseFragmentModel;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.activity.BaseFragmentActivity;
import com.iflytek.cloud.SpeechSynthesizer;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/22.
 */
public class BaseFragmentViewModel extends BaseViewModel {
    private BaseFragmentActivity mActivity;

    private BaseFragmentModel mModel;

    private static String ACTION_NAME = "BAIDU_TUISONG_TOUCHUAN";

    public BaseFragmentViewModel(BaseFragmentModel model, BaseFragmentActivity activity) {
        mModel = model;

        mActivity = activity;
    }

    public void getContent(String data) {
        try {
            JSONObject obj = new JSONObject(data);

            String voiceContext = obj.getString("voiceContext");

            String type = obj.getString("type");

            if (type.equals("1")) {
                Intent intent = new Intent();

                intent.setAction(ACTION_NAME);

                intent.putExtra("voiceContext", voiceContext);
                //发送广播通知界面更新
                mActivity.sendBroadcast(intent);
                //判断语音合成类是否存在
                if (mActivity.mSynthesizerPlayer == null) {
                    mActivity.mSynthesizerPlayer = SpeechSynthesizer.createSynthesizer(mActivity, null);

                    BaseFragmentActivity.getVoice(voiceContext);
                } else {
                    //将服务器返回的推送信息利用语音合成读取出来
                    BaseFragmentActivity.getVoice(voiceContext);
                }
            }else {
                mActivity.toast("网络错误~请重试");
            }
        } catch (Exception e) {
            LogUtil.d("解析出现异常" + e.toString());
        }

    }
}

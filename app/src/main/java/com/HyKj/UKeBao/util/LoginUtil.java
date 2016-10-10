package com.HyKj.UKeBao.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.HyKj.UKeBao.MyApplication;
import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.data.NetWorkService;
import com.HyKj.UKeBao.data.RetrofitHelp;
import com.HyKj.UKeBao.model.bean.BaseInfo;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * 登录&注册帮助类
 * 
 * @author Administrator
 * 
 */
public class LoginUtil {
	public MyCount mc;
	public Context mContext;

	public LoginUtil(Context context) {
		super();
		this.mContext = context;
	}

	/**
	 * @param sendSecurityCode
	 *            改变的背景
	 * @param phoneNumberInput
	 *            电话号码输入
	 */
	public void getSecurityCode(final Button sendSecurityCode,
								EditText phoneNumberInput) {
		String phone = phoneNumberInput.getText().toString().trim();
		if (TextUtils.isEmpty(phone)) {
			toast(mContext, "请输入电话号码!");
			return;
		}
		if (!(phone.length() == 11)) {
			toast(mContext, "手机号长度不正确");
			return;
		}
		// 修改按钮背景为灰色且不可用
		sendSecurityCode.setBackgroundResource(R.drawable.bg_send_security_code_clicked);
		sendSecurityCode.setEnabled(false);
		sendSecurityCode.setTextColor(mContext.getResources().getColor(R.color.text_color_black));
		mc = new MyCount(60000, 1000, sendSecurityCode);
		mc.start();
		NetWorkService mNetWorkService=RetrofitHelp.createService(NetWorkService.class, MyApplication.token);
		Observable<BaseInfo> observable=mNetWorkService.getVerificationCode(Long.parseLong(phone));
		observable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<BaseInfo>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {
						toast(mContext, "发送失败！请检查网络");
						mc.cancel();
						sendSecurityCode.setBackgroundResource(R.drawable.bg_send_security_code);
						sendSecurityCode.setEnabled(true);
						sendSecurityCode.setText("重新获取");
						sendSecurityCode.setTextColor(mContext.getResources().getColor(R.color.white));
					}

					@Override
					public void onNext(BaseInfo baseInfo) {
						if (baseInfo.success) {
							toast(mContext, "验证码已发送!");
						}
					}
				});
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public Button button;

		public MyCount(long millisInFuture, long countDownInterval,
				Button button) {
			super(millisInFuture, countDownInterval);
			this.button = button;

		}

		@Override
		public void onFinish() {
			button.setBackgroundResource(R.drawable.bg_send_security_code);
			button.setEnabled(true);
			button.setText("重新获取");
			button.setTextColor(mContext.getResources().getColor(R.color.white));
		}

		@Override
		public void onTick(long millisUntilFinished) {
			button.setText("重新获取(" + millisUntilFinished / 1000 + ")");
		}
	}

	public void toast(Context context, String info) {
		Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	}

}

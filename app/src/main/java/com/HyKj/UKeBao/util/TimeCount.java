package com.HyKj.UKeBao.util;
/*
 * 用户设定多少秒后可取消缓冲菊花
 */

import android.os.CountDownTimer;

public class TimeCount extends CountDownTimer {

	public TimeCount(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		
	}
	

	@Override
	public void onTick(long millisUntilFinished) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish() {
		BufferCircleDialog.setCancelableListener(true);	
	}

}

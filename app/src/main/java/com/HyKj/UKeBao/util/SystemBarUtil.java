package com.HyKj.UKeBao.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.HyKj.UKeBao.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 沉浸式状态栏工具类，注意：系统4.4以上有效
 * 
 * @author Administrator
 * 
 */
public class SystemBarUtil {

	private static SystemBarTintManager tintManager;
	
	public static void initSystemBar(Activity activity) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			setTranslucentStatus(activity, true);

		}

		tintManager = new SystemBarTintManager(activity);

		tintManager.setStatusBarTintEnabled(true);

		// 使用颜色资源

		tintManager.setStatusBarTintResource(R.color.status_color);

	}
	public static void initSystemBarByColor(Activity activity, int resColor) {
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			
			setTranslucentStatus(activity, true);
			
		}
		
		tintManager = new SystemBarTintManager(activity);
		
		tintManager.setStatusBarTintEnabled(true);
		
		// 使用颜色资源
		tintManager.setStatusBarTintResource(resColor);
		
	}
	
	public static void changeColor(int resColor) {
		if(tintManager!=null){
			tintManager.setStatusBarTintResource(resColor);
		}
	}

	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {

		Window win = activity.getWindow();

		WindowManager.LayoutParams winParams = win.getAttributes();

		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

		if (on) {

			winParams.flags |= bits;

		} else {

			winParams.flags &= ~bits;

		}

		win.setAttributes(winParams);

	}

}

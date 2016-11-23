package com.HyKj.UKeBao.util;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout.LayoutParams;

public class SetSizeUtils {
	/**
	 * 设置 图形按照屏幕的宽度设置大小，高和宽都根据屏幕宽度设置
	 * @param context
	 * @param v 控件
	 * @param x 屏幕宽度的几分之几
	 * @param y 屏幕宽度的几分之几
	 */
	public static void setSizeWidth(Context context,View view,int x,int y){
		 WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	        Display display = wm.getDefaultDisplay();
	        int h1 = display.getHeight();
	        int w1 = display.getWidth();
	     LayoutParams params=   (LayoutParams) view.getLayoutParams();
	     params.width=h1/x;
	     params.height=h1/y;
	     view.setLayoutParams(params);
	}
	/***
	 *  设置 图形按照屏幕的宽度设置大小，高和宽都根据屏幕高度设置
	 * @param context
	 * @param v 控件
	 * @param x 屏幕高度的几分之几
	 * @param y屏幕高度的几分之几
	 */
	public static void setSizeHeight(Context context,View view,int x,int y){
		 WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
	        Display display = wm.getDefaultDisplay();
	        int h1 = display.getHeight();
	        int w1 = display.getWidth();
	     LayoutParams params=   (LayoutParams) view.getLayoutParams();
	     params.width=w1/x;
	     params.height=w1/y;
	     view.setLayoutParams(params);
	}
	/***
	 * 
	 * @param context
	 * @param view
	 * @param y 占屏幕高度的几分之几
	 * @param bottomMargen 与底部的距离
	 */
	public static void setSizeOnlyHeight(Context context,View view,int y,int bottomMargen){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		int h1 = display.getHeight();
		int w1 = display.getWidth();
		LayoutParams params=   (LayoutParams) view.getLayoutParams();
		params.height=h1/y;
		params.bottomMargin=bottomMargen;
		view.setLayoutParams(params);
	}
}

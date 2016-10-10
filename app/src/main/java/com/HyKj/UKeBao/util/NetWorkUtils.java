package com.HyKj.UKeBao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/***
 * 判断是否有网络
 * @author Administrator
 *
 */
public class NetWorkUtils {
	public static boolean networkStatusOK(Context mContext) {
		boolean netStatus = false;
		try {
			ConnectivityManager connectManager = (ConnectivityManager)mContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectManager
					.getActiveNetworkInfo();
			if (activeNetworkInfo != null) {
				if (activeNetworkInfo.isAvailable()
						&& activeNetworkInfo.isConnected()) {
					netStatus = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return netStatus;
	}
}

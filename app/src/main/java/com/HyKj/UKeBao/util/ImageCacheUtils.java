package com.HyKj.UKeBao.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageCacheUtils {

	public static Bitmap decodeBitmap(String pathString) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 通过这个bitmap获取图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(pathString, options);
		if (bitmap == null) {
			System.out.println("bitmap为空");
		}
		float realWidth = options.outWidth;
		float realHeight = options.outHeight;
		System.out.println("真实图片高度：" + realHeight + "宽度:" + realWidth);
		// 计算缩放比
		int scale = (int) ((realHeight > realWidth ? realHeight : realWidth) / 300);
		if (scale <= 0) {
			scale = 1;
		}
		options.inSampleSize = scale;
		options.inJustDecodeBounds = false;
		// 注意这次要把options.inJustDecodeBounds 设为 false,这次图片是要读取出来的。
		bitmap = BitmapFactory.decodeFile(pathString, options);
//		int w = bitmap.getWidth();
//		int h = bitmap.getHeight();
//		System.out.println("缩略图高度：" + h + "宽度:" + w);
		return bitmap;
	}

}

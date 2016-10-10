package com.HyKj.UKeBao.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import com.HyKj.UKeBao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public abstract class BaseImageAdapter<E> extends BaseAdapter {
	protected DisplayImageOptions options;
	protected Context context;
	protected List<E> dataList;
	protected LayoutInflater inflater;

	public BaseImageAdapter(Context context, List<E> list) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.dataList = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_good)
				.showImageForEmptyUri(R.drawable.default_good)
				.showImageOnFail(R.drawable.default_good).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(388)).build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null == dataList) {
			throw new RuntimeException("");
		} else {

			return dataList.size();
		}
	}

	@Override
	public Object getItem(int position) {
		if (null == dataList) {
			throw new RuntimeException("");
		} else {
			return dataList.get(position);
		}

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}

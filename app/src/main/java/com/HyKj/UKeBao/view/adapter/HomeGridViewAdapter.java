package com.HyKj.UKeBao.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;


public class HomeGridViewAdapter extends BaseAdapter {
	private Context cx;
	/**
	 * 功能模块的标题
	 */
	private String[] textStr;
	/**
	 * 功能模块的icon
	 */
	private int[] imgRes;
	private int newCount;

	public HomeGridViewAdapter(Context cx, String[] textStr, int[] imgRes, int newCount) {
		this.cx=cx;

		this.textStr=textStr;

		this.imgRes=imgRes;

		this.newCount=newCount;
	}

	@Override
	public int getCount() {
		return textStr.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView= View.inflate(cx, R.layout.item_gridview_home, null);

		ImageView iv=(ImageView) convertView.findViewById(R.id.iv_gridview_icon);

		TextView tv=(TextView) convertView.findViewById(R.id.tv_gridview_title);

		TextView tv_newsCount=(TextView) convertView.findViewById(R.id.tv_newsCount);

		iv.setImageResource(imgRes[position]);

		tv.setText(textStr[position]);

		if(newCount!=0&&position==2){
			tv_newsCount.setVisibility(View.VISIBLE);

			tv_newsCount.setText(newCount+"");
		}
		return convertView;
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}



}

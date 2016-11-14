package com.HyKj.UKeBao.view.adapter;

import android.app.Activity;
import android.content.Context;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.activity.login.LoginActivity;

import java.util.ArrayList;

public class ViewPagerAdapter extends PagerAdapter {
	private Context context;
	private ArrayList<View> views = new ArrayList<View>();
	private SharedPreferences sharedPreferences;
	private Editor editor;
	private int [] res;

	public ViewPagerAdapter(Context context, ArrayList<View> views) {
		this.views = views;
		this.context = context;
	}
	public ViewPagerAdapter(Context context, int [] res) {
		this.res = res;
		this.context = context;
	}

	@Override
	public int getCount() {
//		if (views != null) {
//			return views.size();
//		} else {
//			return 0;
//		}
		return res.length;

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0 == arg1);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
//		((ViewPager) container).removeView(views.get(addGoods_position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		sharedPreferences = context.getSharedPreferences("user_login",
				context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		View convertView = View.inflate(context, R.layout.item_viewpager_guide,
				null);
		RelativeLayout rl_guide = (RelativeLayout) convertView
				.findViewById(R.id.rl_guide);
		ImageView iv = (ImageView) convertView.findViewById(R.id.iv_guideItem);
		if (position == res.length- 1) {
			iv.setVisibility(View.VISIBLE);
			rl_guide.setBackgroundResource(res[position]);
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					context.startActivity(LoginActivity.getStartIntent(context));
					Activity activity = (Activity) context;
					activity.finish();
					editor.putBoolean("isFirst", false);
					editor.commit();
				}
			});
		}else{
			iv.setVisibility(View.GONE);
			rl_guide.setBackgroundResource(res[position]);
		}
		((ViewPager) container).addView(convertView);
		
		return convertView;
	}

}

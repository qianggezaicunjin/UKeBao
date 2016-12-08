package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.MemberCardInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;
import java.util.Map;


public class CardDetailAdapter extends MyBaseAdapter<MemberCardInfo> {

	public CardDetailAdapter(Context mContext,
							 List<MemberCardInfo> dataList) {
		super(mContext, dataList);
		
	}

	ViewHolder mHolder;
	private DisplayImageOptions optionse;
	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_cq_listview, null);

			mHolder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);

			mHolder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
			
			mHolder.tv_statusName=(TextView)convertView.findViewById(R.id.tv_statusName);

			mHolder.wxHeadimage=(ImageView)convertView.findViewById(R.id.wxHeadimage);

			convertView.setTag(mHolder);
			
		}else{
			mHolder = (ViewHolder) convertView.getTag();
		}
		
		String name=dataList.get(position).getMenber().getName();

		if(TextUtils.isEmpty(name)){
			mHolder.tv_name.setText("用户昵称");
		}else{
			mHolder.tv_name.setText(name);
		}
		String path=dataList.get(position).getMenber().getWxHeadimage();

		optionse = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.default_coupons)
		.showImageForEmptyUri(R.drawable.default_coupons)
		.showImageOnFail(R.drawable.default_coupons).cacheInMemory(true)
		.cacheOnDisk(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(388)).build();
		ImageLoader.getInstance().displayImage(path, mHolder.wxHeadimage,optionse);

		String statusName=dataList.get(position).getStatusName();

		if(statusName.equals("未使用")){
			mHolder.tv_statusName.setTextColor(Color.parseColor("#1FAEE6"));
		}else{
			mHolder.tv_statusName.setTextColor(Color.parseColor("#E52E04"));
		}
		mHolder.tv_statusName.setText(statusName);

		String withTime=dataList.get(position).getWithTime();

		mHolder.tv_date.setText(withTime);
		
		return convertView;
	}
	public static class ViewHolder{
		public TextView tv_name;

		public TextView tv_date;
		
		public TextView tv_statusName;
		
		public ImageView wxHeadimage;
				
	}

}

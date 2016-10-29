package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketListInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class RedPacketManageAdapter extends MyBaseAdapter<RedPacketListInfo> {
	private List<RedPacketListInfo> list=new ArrayList<RedPacketListInfo>();
	private ViewHodler mHolder;
	private DisplayImageOptions optionse;
	public RedPacketManageAdapter(Context context, List<RedPacketListInfo> list) {
		super(context, list);
		this.list=list;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_listview_redpacketmanaget_activity, null);
			mHolder = new ViewHodler();
			mHolder.userIcon = (ImageView) convertView
					.findViewById(R.id.image_item_redPacketManagerActivity);
			mHolder.userName = (TextView) convertView
					.findViewById(R.id.name_item_redPacketManagerActivity);
			mHolder.payRecordDate = (TextView) convertView
					.findViewById(R.id.data_item_redPacketManagerActivity);
			mHolder.recordAmount = (TextView) convertView
					.findViewById(R.id.score_item_redPacketManagerActivity);
			mHolder.orderState = (TextView) convertView
					.findViewById(R.id.scoreCount_item_redPacketManagerActivity);
			convertView.setTag(mHolder);

		}else{
			mHolder = (ViewHodler) convertView.getTag();
		}
			optionse = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.default_red)
			.showImageForEmptyUri(R.drawable.default_red)
			.showImageOnFail(R.drawable.default_red).cacheInMemory(true)
			.cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.displayer(new FadeInBitmapDisplayer(388)).build();
		
			mHolder.userName.setText(list.get(position).getContext()+"");
			mHolder.payRecordDate.setText(list.get(position).getTime()+"");
			ImageLoader.getInstance().displayImage(list.get(position).getImage()+"",mHolder.userIcon,optionse);
			mHolder.recordAmount.setText(list.get(position).getIntegralQuota()+"积分");
			mHolder.orderState.setText(list.get(position).getSurplusCount()+"/"+list.get(position).getCount()+"");
		
		return convertView;
	}
	public static class ViewHodler {
		public ImageView userIcon;
		public TextView userName, payRecordDate, recordAmount, orderState;

	}

}

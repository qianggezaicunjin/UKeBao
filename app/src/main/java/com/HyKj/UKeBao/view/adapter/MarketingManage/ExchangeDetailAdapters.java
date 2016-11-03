package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.ProductLists;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

public class ExchangeDetailAdapters extends MyBaseAdapter<ProductLists> {
	private List<ProductLists> rowList=new ArrayList<ProductLists>();

	private Context mcontext;

	private ViewHodler mHolder;

	private  Double infactPriceAll;

	private Double x=0.00;

	private  DisplayImageOptions options;

	public ExchangeDetailAdapters(Context context, List<ProductLists> list, Double infacPrice) {
		super(context, list);

		this.rowList=list;

		this.mcontext=context;

		this.infactPriceAll=infacPrice;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rowList.size();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(convertView==null){
			mHolder = new ViewHodler();
			convertView = mInflater.inflate(
					R.layout.item_listview_exchangedetail_activity, null);
			mHolder.imgage = (ImageView) convertView
					.findViewById(R.id.image_listView_exchangDetail_Activity);
			mHolder.orderName = (TextView) convertView
					.findViewById(R.id.goodsName_listView_exchangDetail_Activity);
			mHolder.orderPrice = (TextView) convertView
					.findViewById(R.id.goodsPrice_listView_exchangDetail_Activity);
			mHolder.goodsCount = (TextView) convertView
					.findViewById(R.id.goodsCount_listView_exchangDetail_Activity);
			mHolder.xixian = (View) convertView
					.findViewById(R.id.item_xixian);
			mHolder.xixian_small = (View) convertView
					.findViewById(R.id.item_xixian_small);
			mHolder.xixian_top = (View) convertView
					.findViewById(R.id.xixian_top);
			convertView.setTag(mHolder);

		} else {
			mHolder = (ViewHodler) convertView.getTag();
		}
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_good)
				.showImageForEmptyUri(R.drawable.default_good)
				.showImageOnFail(R.drawable.default_good).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.displayer(new FadeInBitmapDisplayer(388)).build();


		// 设置数据
		//immHolder.userIcon.setImageDrawable(recordInfo.);
		ImageLoader.getInstance().displayImage(rowList.get(position).goodsImgs.get(0), mHolder.imgage,options);
		mHolder.orderName.setText(rowList.get(position).getName()+"");
		if(position==0){
			mHolder.xixian_top.setVisibility(View.VISIBLE);
		}else{
			mHolder.xixian_top.setVisibility(View.GONE);
		}
		mHolder.orderPrice.setText("结算价  "+rowList.get(position).getRealPrice()+"元");
		if(position+1==rowList.size()){
			mHolder.xixian.setVisibility(View.VISIBLE);
			mHolder.xixian_small.setVisibility(View.GONE);
			mHolder.xixian.setVisibility(View.VISIBLE);
		}else{
			mHolder.xixian.setVisibility(View.GONE);
			mHolder.xixian_small.setVisibility(View.VISIBLE);
		}
		mHolder.goodsCount.setText("X"+rowList.get(position).getCount());

		return convertView;
	}
		
	public static class ViewHodler {
		public ImageView imgage;
		public LinearLayout framLayout;
		public TextView orderName, orderPrice,goodsCount,infactPrice;
		public View xixian,xixian_small,xixian_top;
	}
}

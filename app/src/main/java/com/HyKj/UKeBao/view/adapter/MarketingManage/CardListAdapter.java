package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.CardListInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CardListAdapter extends MyBaseAdapter<CardListInfo> {
	private List<CardListInfo>dataList=new ArrayList<CardListInfo>();

	private Context context;
	

	public CardListAdapter(Context context, List<CardListInfo> list) {
		super(context, list);
		this.dataList=list;
		this.context=context;
	}
	
	ViewHolder mHolder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			mHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_card, null);
			//已领取
			mHolder.tv_have=(TextView)convertView.findViewById(R.id.tv_detail);
			//已使用
			mHolder.tv_use=(TextView)convertView.findViewById(R.id.tv_use_detail);
			mHolder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);
			//使用规则
			mHolder.tv_detail=(TextView)convertView.findViewById(R.id.tv_rule_use);
			convertView.setTag(mHolder);
			
		}else{
			mHolder = (ViewHolder) convertView.getTag();
		}
		//可领取
		int getLimit=dataList.get(position).getLimit;
		//已被会员领取
		int menberGetCount=dataList.get(position).getMenberGetCount();
		//消费满多少可以使用
		int deduction=dataList.get(position).getDeduction();
		//卡券金钱
		float price=dataList.get(position).getPrice();

		mHolder.tv_price.setText(String.valueOf(price));
		//已被会员使用
		int menberUseCount=dataList.get(position).getMenberUseCount();
		
		mHolder.tv_detail.setText("消费订单满"+deduction+"元可以使用");

		mHolder.tv_have.setText(menberGetCount+"/"+getLimit);

		mHolder.tv_use.setText(menberUseCount+"/"+menberGetCount);

		return convertView;
	}
	public static class ViewHolder{
		public TextView tv_detail;
		//已领取
		public TextView tv_have;
		//已使用
		public TextView tv_use;
		//金钱
		public TextView tv_price;	
		
	}

}

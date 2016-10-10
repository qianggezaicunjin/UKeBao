package com.HyKj.UKeBao.view.adapter.businessManage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.bean.CashRecordInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;

import java.util.List;


public class CashRecordAdapter extends MyBaseAdapter<CashRecordInfo> {


	public CashRecordAdapter(Context mContext, List<CashRecordInfo> dataList) {
		super(mContext, dataList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_listview_record_base, null);
			holder.reason=(TextView) convertView.findViewById(R.id.tv_change_reason_recode);
			holder.account=(TextView) convertView.findViewById(R.id.tv_account_recode);
			holder.date=(TextView) convertView.findViewById(R.id.tv_date_recode);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		CashRecordInfo recodeInfo=dataList.get(position);
		//交易原因说明
		holder.reason.setText(recodeInfo.info);
		//单笔交易金额
		if(recodeInfo.type){
//			true:收入，false:支出
			holder.account.setText("+"+recodeInfo.quota);
			holder.account.setTextColor(mContext.getResources().getColor(R.color.text_color_red));
		}else{
			holder.account.setText("-"+recodeInfo.quota);
			holder.account.setTextColor(mContext.getResources().getColor(R.color.blue));
		}
		holder.date.setText(recodeInfo.time);
		return convertView;
	}
	public static class ViewHolder{
		public TextView reason,account,date;
	}

}

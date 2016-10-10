package com.HyKj.UKeBao.view.adapter.longin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;

import java.util.List;

public class ChooseProvinceAdapter extends MyBaseAdapter<String> {
	

	public ChooseProvinceAdapter(Context mContext, List<String> list) {
		super(mContext, list);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder;
		if(convertView==null){
			mHolder =new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_listview_choose_province, null);
			mHolder.tv=(TextView) convertView.findViewById(R.id.tv_chooseProvince_item);
			convertView.setTag(mHolder);
		}else{
			mHolder=(ViewHolder) convertView.getTag();
		}
		mHolder.tv.setText(dataList.get(position));
		return convertView;
	}
	public static class ViewHolder{
		public TextView tv;
		
	}

}

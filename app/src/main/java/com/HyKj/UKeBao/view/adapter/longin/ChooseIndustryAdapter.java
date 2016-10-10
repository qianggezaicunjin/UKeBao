package com.HyKj.UKeBao.view.adapter.longin;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.login.baen.CategoryInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
public class ChooseIndustryAdapter extends MyBaseAdapter<CategoryInfo> {

    public ChooseIndustryAdapter(Context mContext, List<CategoryInfo> list) {
        super(mContext,list);
        dataList = list;
        this.mContext=mContext;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_listview_choose_industrytype, null);
            mHolder.tv = (TextView) convertView.findViewById(R.id.tv_choose_item);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        mHolder.tv.setText(dataList.get(position).name);

        return convertView;
    }

    public static class ViewHolder {
        public TextView tv;

    }
}

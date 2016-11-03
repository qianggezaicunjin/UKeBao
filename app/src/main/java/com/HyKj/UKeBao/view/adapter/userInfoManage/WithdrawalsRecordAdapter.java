package com.HyKj.UKeBao.view.adapter.userInfoManage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.userInfoManage.bean.WithdrawalsRecord;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsRecordAdapter extends MyBaseAdapter{
    public ViewHoider viewHoider;

    public List<WithdrawalsRecord> mList=new ArrayList<>();

    public WithdrawalsRecordAdapter(Context mContext, List<WithdrawalsRecord> dataList) {
        super(mContext, dataList);

        mList=dataList;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            viewHoider=new ViewHoider();

            view=mInflater.inflate(R.layout.item_listview_withdrawals_record,null);

            viewHoider.tv_money= (TextView) view.findViewById(R.id.tv_withdrawals_record_money);

            viewHoider.tv_status= (TextView) view.findViewById(R.id.tv_withdrawals_record_status);

            viewHoider.tv_date= (TextView) view.findViewById(R.id.tv_withdrawals_record_date);

            view.setTag(viewHoider);
        }else {
            viewHoider= (ViewHoider) view.getTag();
        }
        if(mList.get(position).getCounterFee()!=0){
            viewHoider.tv_money.setText(mList.get(position).getQuota());
        }else {
            viewHoider.tv_money.setText(String.valueOf(mList.get(position).quota)+"元");
        }
            viewHoider.tv_status.setText((mList.get(position).getStatusType()));

            viewHoider.tv_date.setText(mList.get(position).getTime());

        return view;
    }
    public static class ViewHoider{
        public TextView tv_money;//金额

        public TextView tv_status;//状态

        public TextView tv_date;//时间
    }
}

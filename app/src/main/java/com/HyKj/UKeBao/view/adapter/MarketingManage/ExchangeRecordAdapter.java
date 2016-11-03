package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.Rows;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public class ExchangeRecordAdapter extends MyBaseAdapter<Rows> {
    // 父类定义好的变量
    protected Context mContext;
    protected List<Rows> dataList;
    protected DisplayImageOptions options;

    public ExchangeRecordAdapter(Context context, List<Rows> list) {
        super(context, list);
        // TODO Auto-generated constructor stub
        this.dataList = list;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Rows recordInfo = dataList.get(position);
        ViewHodler mHolder;
        if (convertView == null) {
            mHolder = new ViewHodler();
            convertView = mInflater.inflate(R.layout.item_listview_exchangerecotdactivity, null);
            mHolder.userIcon = (ImageView) convertView
                    .findViewById(R.id.iamgeTitle_listView_exchangeRecordActivity);
            mHolder.nickName = (TextView) convertView
                    .findViewById(R.id.userName_listView_exchangeRecordActivity);
            mHolder.recordDate = (TextView) convertView
                    .findViewById(R.id.orderTime_listView_exchangeRecordActivity);
            mHolder.exchangeAmount = (TextView) convertView
                    .findViewById(R.id.realPrice_listView_exchangeRecordActivity);
            mHolder.orderNumber = (TextView) convertView
                    .findViewById(R.id.orderName_listView_exchangeRecordActivity);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHodler) convertView.getTag();
        }
        // 设置数据
        //immHolder.userIcon.setImageDrawable(recordInfo.);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_good)
                .showImageForEmptyUri(R.drawable.default_good)
                .showImageOnFail(R.drawable.default_good).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();

        ImageLoader.getInstance().displayImage(recordInfo.getWxHeadimage(), mHolder.userIcon, options);
        String urlString = recordInfo.getWxHeadimage();
        mHolder.nickName.setText(recordInfo.getMenberName() + "");
        mHolder.orderNumber.setText("订单号  " + recordInfo.getNo() + "");
        mHolder.recordDate.setText(recordInfo.getEndDate() + "");
        mHolder.exchangeAmount.setText(recordInfo.getAllRealPrice() + "元");

        return convertView;
    }

    public static class ViewHodler {
        public ImageView userIcon;
        public TextView nickName, recordDate, exchangeAmount, orderNumber;

    }

}

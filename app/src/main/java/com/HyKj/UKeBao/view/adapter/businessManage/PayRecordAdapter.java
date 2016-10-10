package com.HyKj.UKeBao.view.adapter.businessManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.businessManage.bean.OrderRecord;
import com.HyKj.UKeBao.util.LogUtil;
import com.HyKj.UKeBao.view.adapter.BaseImageAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.List;

public class PayRecordAdapter extends BaseImageAdapter<OrderRecord> {
    // 父类定义好的变量
    protected Context mContext;
    protected List<OrderRecord> dataList;
    private double rechage;

    public PayRecordAdapter(Context context, List<OrderRecord> list, double rechages) {
        super(context, list);
        this.mContext = context;
        this.dataList = list;
        this.rechage = rechages;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHodler mHolder;
        if (convertView == null) {
            mHolder = new ViewHodler();
            convertView = inflater.inflate(
                    R.layout.item_listview_payrecord_activity, null);
            mHolder.userIcon = (ImageView) convertView
                    .findViewById(R.id.iamgeTitle_listView_payRecordActivity);
            mHolder.userName = (TextView) convertView
                    .findViewById(R.id.userName_listView_payRecordActivity);
            mHolder.payRecordDate = (TextView) convertView
                    .findViewById(R.id.orderCreatTime_listView_payRecordActivity);
            mHolder.recordAmount = (TextView) convertView
                    .findViewById(R.id.typePrice_listView_payRecordActivity);
            mHolder.orderState = (TextView) convertView
                    .findViewById(R.id.typeOrder_listView_payRecordActivity);
            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHodler) convertView.getTag();
        }
        // 设置数据
        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
        OrderRecord orderRecord = dataList.get(position);
        if (orderRecord.getStatus().equals("1")) {
            double price = Double.valueOf(orderRecord.getRealPrice());

            mHolder.recordAmount.setText("+" + df.format(price));

            mHolder.recordAmount.setTextColor(mContext.getResources().getColor(R.color.blue));

            mHolder.orderState.setText(df.format(Double.valueOf(orderRecord.getIntegral()) / rechage) + "元");

        }
        if (dataList.get(position).getStatus().equals("5")) {
            double price = Double.valueOf(dataList.get(position).getRealPrice());

            mHolder.recordAmount.setText("-" + df.format(price));

            mHolder.recordAmount.setTextColor(mContext.getResources().getColor(R.color.text_color_red));

            mHolder.orderState.setText("订单退款");
        }
        //设置默认图片
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_1)
                .showImageForEmptyUri(R.drawable.default_1)
                .showImageOnFail(R.drawable.default_1).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(388)).build();

        ImageLoader.getInstance().displayImage(dataList.get(position).getWxHeadimage(), mHolder.userIcon, options);

        mHolder.userName.setText(dataList.get(position).getMenberName() + "");
        LogUtil.d("EndDate" + dataList.get(position).getEndDate());
        if ((dataList.get(position).getEndDate()) == null) {
            mHolder.payRecordDate.setVisibility(View.INVISIBLE);
        } else {
            mHolder.payRecordDate.setVisibility(View.VISIBLE);

            mHolder.payRecordDate.setText(dataList.get(position).getEndDate() + "");
        }
        return convertView;
    }

    public static class ViewHodler {
        public ImageView userIcon;

        public TextView userName, payRecordDate, recordAmount, orderState;
    }

}

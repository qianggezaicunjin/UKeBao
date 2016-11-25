package com.HyKj.UKeBao.view.adapter.MarketingManage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.model.marketingManage.bean.RedPacketDetailInfo;
import com.HyKj.UKeBao.view.adapter.MyBaseAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;


public class RedPacketDetailActivityAdapter extends MyBaseAdapter<RedPacketDetailInfo> {
    private List<RedPacketDetailInfo> list = new ArrayList<RedPacketDetailInfo>();
    private ViewHodler mHolder;
    private Context mContext;
    private DisplayImageOptions optionse;
    public RedPacketDetailActivityAdapter(Context context,
                                          List<RedPacketDetailInfo> list) {
        super(context, list);
        this.list = list;
        this.mContext = context;
        // TODO Auto-generated constructor stub
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_listview_redpacketdetail_activity, null);
            mHolder = new ViewHodler();
            mHolder.userIcon = (ImageView) convertView
                    .findViewById(R.id.image_item_redPacketDetailActivity);
            mHolder.userName = (TextView) convertView
                    .findViewById(R.id.name_item_redPacketDetailActivity);
            mHolder.payRecordDate = (TextView) convertView
                    .findViewById(R.id.data_item_redPacketDetailActivity);
            mHolder.recordAmount = (TextView) convertView
                    .findViewById(R.id.score_item_redPacketDetailActivity);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHodler) convertView.getTag();
        }
        mHolder.userName.setText(list.get(position).getMenberName() + "");
        mHolder.payRecordDate.setText(list.get(position).getDate() + "");
        optionse = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_coupons)
                .showImageForEmptyUri(R.drawable.default_coupons)
                .showImageOnFail(R.drawable.default_coupons).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .displayer(new RoundedBitmapDisplayer(10)).build();

        ImageLoader.getInstance().displayImage(list.get(position).getWxHeadImage() + "", mHolder.userIcon, optionse);
        mHolder.recordAmount.setText(list.get(position).getIntegral() + "积分");

        return convertView;
    }

    public static class ViewHodler {
        public ImageView userIcon;
        public TextView userName, payRecordDate, recordAmount, orderState;

    }

    public void refshData(List<RedPacketDetailInfo> lis) {
        list.clear();
        list.addAll(lis);
        notifyDataSetChanged();
    }

}

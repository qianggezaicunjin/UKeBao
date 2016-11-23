package com.HyKj.UKeBao.view.adapter.businessManage.businessSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class GoodsImageRecycleViewAdapter extends RecyclerView.Adapter<GoodsImageRecycleViewAdapter.ViewHolder> {
    private List<String> mList = new ArrayList<>();

    private List<String> goodName_list = new ArrayList<>();

    public List<Integer> goodId_list = new ArrayList<Integer>();

    public List<Integer> delete_list = new ArrayList<Integer>();

    private Context mContext;

    private MyItemClickListener mlistener;

    private int init_flag;//初始化商品名称标记

    private boolean finish_flag = true;//完成初始化商品名称标记

    public ViewHolder viewHolder;

    public GoodsImageRecycleViewAdapter(List<String> data, List<String> goodName_list, List<Integer> goodId_list, Context context) {
        mList = data;

        this.goodName_list = goodName_list;

        this.goodId_list = goodId_list;

        init_flag = goodName_list.size();

        LogUtil.d("1111111111111111111init_flag" + init_flag + finish_flag);

        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_goods_image_gridview, null);

        ViewHolder viewHolder = new ViewHolder(view, mlistener);

        this.viewHolder = viewHolder;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mList.get(position).equals("end") && position != 5) {
            holder.goodsName.setVisibility(View.INVISIBLE);

            holder.goodsName.setText("I am add~");

            holder.addPhoto.setImageResource(R.drawable.add_businessimage);

            holder.addPhoto.setPadding(45, 45, 45, 45);

            holder.deletePhoto.setVisibility(View.INVISIBLE);

        } else if (!mList.get(position).equals("end")) {
            holder.goodsName.setVisibility(View.VISIBLE);

            //初始化商品名称
            holder.goodsName.setText(goodName_list.get(position));

            LogUtil.d("111111111111111position" + position + holder.goodsName.getText().toString() + "init_flag:" + init_flag + "finish_flag:" + finish_flag);

            if (mList.get(position).substring(0, 4).equals("http")) {
                Picasso.with(mContext)
                        .load(mList.get(position))
                        .resize(60,60)
                        .config(Bitmap.Config.RGB_565)
                        .into(holder.addPhoto);
            } else {
                Picasso.with(mContext)
                        .load(new File(mList.get(position)))
                        .resize(60,60)
                        .config(Bitmap.Config.RGB_565)
                        .into(holder.addPhoto);
            }
            holder.deletePhoto.setVisibility(View.VISIBLE);
        }

    }

    public interface MyItemClickListener {
        public void onItemClick(View v, int postion);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 设置item监听
     */
    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mlistener = listener;
    }

    //增加数据
    public void addData(int position, String path) {
        mList.add(position, path);

        notifyItemInserted(position);
    }

    //删除数据
    public List<String> removeData(int position) {
        if (mList.size() == 5 && !mList.get(mList.size() - 1).equals("end")) {
            mList.set(4, "end");

            try {
                delete_list.add(goodId_list.get(position));

                goodId_list.remove(position);
            } catch (Exception e) {
                LogUtil.d("GoodsImageRecycleViewAdapter删除了本地图片");
            }

        } else {
            try {
                delete_list.add(goodId_list.get(position));

                goodId_list.remove(position);

                mList.remove(position);

                goodName_list.remove(position);
            } catch (Exception e) {
                LogUtil.d("BusinessSettings_adapter" + e.toString());

                mList.remove(position);

                goodName_list.remove(position);
            }
        }
        notifyItemRemoved(position);

        return mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView addPhoto;

        public ImageView deletePhoto;

        public MyItemClickListener mlistener;

        public TextView goodsName;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);

            mlistener = listener;

            addPhoto = (ImageView) itemView.findViewById(R.id.addphoto);

            deletePhoto = (ImageView) itemView.findViewById(R.id.goods_delephoto);

            goodsName = (TextView) itemView.findViewById(R.id.et_goodsName);

            deletePhoto.setOnClickListener(this);

            addPhoto.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mlistener != null) {
                mlistener.onItemClick(view, getLayoutPosition());
            }
        }
    }
}

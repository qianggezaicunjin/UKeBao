package com.HyKj.UKeBao.view.adapter.businessManage.businessSettings;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.HyKj.UKeBao.R;
import com.HyKj.UKeBao.util.LogUtil;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/11.
 */
public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
    private List<String> mList = new ArrayList<>();

    private Context mContext;

    private MyItemClickListener mlistener;

    public MyRecycleViewAdapter(List<String> data, Context context) {
        mList = data;

        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_add_photo_gridview, null);

        return new ViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mList.get(position).equals("end") && position != 5) {
            holder.addPhoto.setImageResource(R.drawable.add_businessimage);

            holder.addPhoto.setPadding(45, 45, 45, 45);

            holder.deletePhoto.setVisibility(View.INVISIBLE);

        } else if (!mList.get(position).equals("end")) {
            if (mList.get(position).substring(0, 4).equals("http")) {
                Picasso.with(mContext)
                        .load(mList.get(position))
                        .config(Bitmap.Config.RGB_565)
                        .into(holder.addPhoto);
            } else {
                Picasso.with(mContext)
                        .load(new File(mList.get(position)))
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
        if (mList.size() == 5&&!mList.get(mList.size()-1).equals("end")) {
            mList.set(4, "end");
        } else {
            mList.remove(position);
        }

        notifyItemRemoved(position);

        return mList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView addPhoto;

        public ImageView deletePhoto;

        public MyItemClickListener mlistener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);

            mlistener = listener;

            addPhoto = (ImageView) itemView.findViewById(R.id.addphoto);

            deletePhoto = (ImageView) itemView.findViewById(R.id.delephoto);

            deletePhoto.setOnClickListener(this);

            addPhoto.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            LogUtil.d("点击了delete按钮,LayoutPosition:" + getLayoutPosition() + "----position:" + getPosition());
            if (mlistener != null) {
                mlistener.onItemClick(view, getLayoutPosition());
            }
        }
    }
}

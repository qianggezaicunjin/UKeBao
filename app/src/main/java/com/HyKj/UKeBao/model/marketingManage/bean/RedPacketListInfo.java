package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 红包列表信息
 * Created by Administrator on 2016/10/29.
 */
public class RedPacketListInfo {
    public int total;//列表总数

    public String context;//红包内容

    public int count;//红包总数

    public int id;//编号

    public double integralQuota;//红包积分

    public int surplusCount;//剩余红包数

    public double surplusQuota;//剩余积分

    public String time;//时间

    public String image;//图片

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getIntegralQuota() {
        return integralQuota;
    }

    public void setIntegralQuota(double integralQuota) {
        this.integralQuota = integralQuota;
    }

    public int getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(int surplusCount) {
        this.surplusCount = surplusCount;
    }

    public double getSurplusQuota() {
        return surplusQuota;
    }

    public void setSurplusQuota(double surplusQuota) {
        this.surplusQuota = surplusQuota;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "RedPacketListInfo{" +
                "context='" + context + '\'' +
                ", total=" + total +
                ", count=" + count +
                ", id=" + id +
                ", integralQuota=" + integralQuota +
                ", surplusCount=" + surplusCount +
                ", surplusQuota=" + surplusQuota +
                ", time='" + time + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}

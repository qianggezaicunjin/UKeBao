package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 单个红包详情信息
 * Created by Administrator on 2016/10/18.
 */
public class RedPacketDetail {

    public String context;

    public String distance;

    public String surplusCount;

    public String count;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getSurplusCount() {
        return surplusCount;
    }

    public void setSurplusCount(String surplusCount) {
        this.surplusCount = surplusCount;
    }

    @Override
    public String toString() {
        return "RedPacketDetail{" +
                "context='" + context + '\'' +
                ", distance='" + distance + '\'' +
                ", surplusCount='" + surplusCount + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}

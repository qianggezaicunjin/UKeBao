package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 单个红包详情信息
 * Created by Administrator on 2016/10/18.
 */
public class RedPacketDetail {

    public String context;//广告语

    public String distance;//距离

    public String surplusCount;//剩余数量

    public String count;//数量

    public String surplusQuota;//剩余积分

    public double integralQuota;//红包总积分

    public String image;//图片

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
        return surplusCount+"/"+count;
    }

    public void setSurplusCount(String surplusCount) {
        this.surplusCount = surplusCount;
    }

    public String getImage() {
        return image+"@178h_320w_1e_1c";
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

    public String getSurplusQuota() {
        return surplusQuota+"/"+integralQuota+"积分";
    }

    public void setSurplusQuota(String surplusQuota) {
        this.surplusQuota = surplusQuota;
    }

    @Override
    public String toString() {
        return "RedPacketDetail{" +
                "context='" + context + '\'' +
                ", distance='" + distance + '\'' +
                ", surplusCount='" + surplusCount + '\'' +
                ", count='" + count + '\'' +
                ", surplusQuota='" + surplusQuota + '\'' +
                ", integralQuota=" + integralQuota +
                ", image='" + image + '\'' +
                '}';
    }
}

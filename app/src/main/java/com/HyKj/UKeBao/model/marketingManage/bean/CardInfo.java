package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 发送卡劵后的返回信息
 * Created by Administrator on 2016/10/26.
 */
public class CardInfo {
    public String details;

    public String id;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CardInfo{" +
                "details='" + details + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}

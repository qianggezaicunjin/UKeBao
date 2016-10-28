package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 会员领取卡劵信息
 * Created by Administrator on 2016/10/27.
 */
public class MemberCardInfo {
    public String statusName;//使用状态

    public String withTime;//领取卡劵时间

    public String name;//会员名称

    public String wxHeadimage;//会员头像地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getWithTime() {
        return withTime;
    }

    public void setWithTime(String withTime) {
        this.withTime = withTime;
    }

    public String getWxHeadimage() {
        return wxHeadimage;
    }

    public void setWxHeadimage(String wxHeadimage) {
        this.wxHeadimage = wxHeadimage;
    }

    @Override
    public String toString() {
        return "MemberCardInfo{" +
                "name='" + name + '\'' +
                ", statusName='" + statusName + '\'' +
                ", withTime='" + withTime + '\'' +
                ", wxHeadimage='" + wxHeadimage + '\'' +
                '}';
    }
}

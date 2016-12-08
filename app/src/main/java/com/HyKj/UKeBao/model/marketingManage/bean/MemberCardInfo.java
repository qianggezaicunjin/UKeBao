package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 会员领取卡劵信息
 * Created by Administrator on 2016/10/27.
 */
public class MemberCardInfo {
    public String statusName;//使用状态

    public String withTime;//领取卡劵时间

    public Menber menber;

    public Menber getMenber() {
        return menber;
    }

    public void setMenber(Menber menber) {
        this.menber = menber;
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

    @Override
    public String toString() {
        return "MemberCardInfo{" +
                "menber=" + menber +
                ", statusName='" + statusName + '\'' +
                ", withTime='" + withTime + '\'' +
                '}';
    }
}

package com.HyKj.UKeBao.model.userInfoManage.bean;

/**
 * 兑换记录
 * Created by Administrator on 2016/11/3.
 */
public class WithdrawalsRecord {
    public String time;//交易时间

    public double quota;//提现金额(扣除手续费之后的金额)

    public double counterFee;//手续费

    public String statusType;//审核状态(0:待审核，1:已通过，2：不通过..）

    public double getCounterFee() {
        return counterFee;
    }

    public void setCounterFee(double counterFee) {
        this.counterFee = counterFee;
    }

    public String getQuota() {
        return quota+"(手续费"+counterFee+"元)";
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WithdrawalsRecord{" +
                "counterFee=" + counterFee +
                ", time='" + time + '\'' +
                ", quota=" + quota +
                ", statusType='" + statusType + '\'' +
                '}';
    }
}

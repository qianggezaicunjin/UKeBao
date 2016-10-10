package com.HyKj.UKeBao.model.businessManage.bean;

/**
 * 财务管理信息
 * Created by Administrator on 2016/9/27.
 */
public class FinancialInfo {
    public String refundmount;//退款金额

    public String refundCount;//退款笔数

    public String OrderAmount;//订单金额

    public String OrderCount;//订单笔数

    public String turnover;//成交金额

    public String bargainCount;//成交笔数

    public String cash;//现金账户

    public String discountPaid;//折后实收

    public String integral;//积分账户

    public String discountQuota;//折扣实结

    public String freeServiceQuota;//免服务费金额

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getRefundmount() {
        return refundmount;
    }

    public void setRefundmount(String refundmount) {
        this.refundmount = refundmount;
    }

    public String getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(String refundCount) {
        this.refundCount = refundCount;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getOrderCount() {
        return OrderCount;
    }

    public void setOrderCount(String orderCount) {
        OrderCount = orderCount;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String turnover) {
        this.turnover = turnover;
    }

    public String getBargainCount() {
        return bargainCount;
    }

    public void setBargainCount(String bargainCount) {
        this.bargainCount = bargainCount;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getDiscountPaid() {
        return discountPaid;
    }

    public void setDiscountPaid(String discountPaid) {
        this.discountPaid = discountPaid;
    }

    public String getDiscountQuota() {
        return discountQuota;
    }

    public void setDiscountQuota(String discountQuota) {
        this.discountQuota = discountQuota;
    }

    public String getFreeServiceQuota() {
        return freeServiceQuota;
    }

    public void setFreeServiceQuota(String freeServiceQuota) {
        this.freeServiceQuota = freeServiceQuota;
    }

    @Override
    public String toString() {
        return "FinancialInfo{" +
                "refundmount='" + refundmount + '\'' +
                ", refundCount='" + refundCount + '\'' +
                ", OrderAmount='" + OrderAmount + '\'' +
                ", OrderCount='" + OrderCount + '\'' +
                ", turnover='" + turnover + '\'' +
                ", bargainCount='" + bargainCount + '\'' +
                ", cash='" + cash + '\'' +
                ", discountPaid='" + discountPaid + '\'' +
                ", integral='" + integral + '\'' +
                ", discountQuota='" + discountQuota + '\'' +
                ", freeServiceQuota='" + freeServiceQuota + '\'' +
                '}';
    }
}

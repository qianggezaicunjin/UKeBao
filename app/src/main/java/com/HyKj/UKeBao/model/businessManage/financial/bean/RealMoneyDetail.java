package com.HyKj.UKeBao.model.businessManage.financial.bean;

/**
 * 实收详情
 *
 * Created by Administrator on 2016/11/10.
 */
public class RealMoneyDetail {

    public int allCount;//合计订单笔数

    public double allPrice;//订单金额

    public double allRealPrice;//实收净额

    public 	double couponAllRealPrice;//卡券实收净额

    public double couponCash;//减卡券金额

    public double couponCount;//卡券优惠订单笔数

    public double couponFreeServiceCash;//免服务费金额

    public double couponPrice;//卡券优惠订单金额

    public double couponRealPrice;//卡券实收金额

    public double couponServiceCost;//服务费

    public double discount;//折扣

    public double discountAllPrice;//折扣订单金额

    public double discountAllRealPrice;//折扣实收净额

    public int discountCount;//	折扣订单数

    public double discountFreeServiceCash;//不参与折扣金额

    public double discountPrice ;//折扣金额

    public double discountRealPrice;//折后实收

    public int superDiscountCount;//超值兑换订单数量

    public double superDiscountPrice;//超值兑换订单金额

    public double superDiscountRealPrice;//超值兑换结算金额

    public String getAllCount() {
        return allCount+"笔";
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public String getAllPrice() {
        return allPrice+"元";
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public String getAllRealPrice() {
        return allRealPrice+"元";
    }

    public void setAllRealPrice(double allRealPrice) {
        this.allRealPrice = allRealPrice;
    }

    public String getCouponAllRealPrice() {
        return couponAllRealPrice+"元";
    }

    public void setCouponAllRealPrice(double couponAllRealPrice) {
        this.couponAllRealPrice = couponAllRealPrice;
    }

    public String getCouponCash() {
        return "减卡劵"+couponCash+"元";
    }

    public void setCouponCash(double couponCash) {
        this.couponCash = couponCash;
    }

    public String getCouponCount() {
        return couponCount+"笔";
    }

    public void setCouponCount(double couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponFreeServiceCash() {
        return couponFreeServiceCash+"元";
    }

    public void setCouponFreeServiceCash(double couponFreeServiceCash) {
        this.couponFreeServiceCash = couponFreeServiceCash;
    }

    public String getCouponPrice() {
        return couponPrice+"元";
    }

    public void setCouponPrice(double couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getCouponRealPrice() {
        return couponRealPrice+"元";
    }

    public void setCouponRealPrice(double couponRealPrice) {
        this.couponRealPrice = couponRealPrice;
    }

    public String getCouponServiceCost() {
        return couponServiceCost+"元";
    }

    public void setCouponServiceCost(double couponServiceCost) {
        this.couponServiceCost = couponServiceCost;
    }

    public String getDiscount() {
        return discount+"折";
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDiscountAllPrice() {
        return discountAllPrice+"元";
    }

    public void setDiscountAllPrice(double discountAllPrice) {
        this.discountAllPrice = discountAllPrice;
    }

    public String getDiscountAllRealPrice() {
        return discountAllRealPrice+"元";
    }

    public void setDiscountAllRealPrice(double discountAllRealPrice) {
        this.discountAllRealPrice = discountAllRealPrice;
    }

    public String getDiscountCount() {
        return discountCount+"笔";
    }

    public void setDiscountCount(int discountCount) {
        this.discountCount = discountCount;
    }

    public String getDiscountFreeServiceCash() {
        return discountFreeServiceCash+"元";
    }

    public void setDiscountFreeServiceCash(double discountFreeServiceCash) {
        this.discountFreeServiceCash = discountFreeServiceCash;
    }

    public String getDiscountPrice() {
        return discountPrice+"元";
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getDiscountRealPrice() {
        return discountRealPrice+"元";
    }

    public void setDiscountRealPrice(double discountRealPrice) {
        this.discountRealPrice = discountRealPrice;
    }

    public String getSuperDiscountCount() {
        return superDiscountCount+"笔";
    }

    public void setSuperDiscountCount(int superDiscountCount) {
        this.superDiscountCount = superDiscountCount;
    }

    public String getSuperDiscountPrice() {
        return superDiscountPrice+"元";
    }

    public void setSuperDiscountPrice(double superDiscountPrice) {
        this.superDiscountPrice = superDiscountPrice;
    }

    public String getSuperDiscountRealPrice() {
        return superDiscountRealPrice+"元";
    }

    public void setSuperDiscountRealPrice(double superDiscountRealPrice) {
        this.superDiscountRealPrice = superDiscountRealPrice;
    }

    @Override
    public String toString() {
        return "RealMoneyDetail{" +
                "allCount=" + allCount +
                ", allPrice=" + allPrice +
                ", allRealPrice=" + allRealPrice +
                ", couponAllRealPrice=" + couponAllRealPrice +
                ", couponCash=" + couponCash +
                ", couponCount=" + couponCount +
                ", couponFreeServiceCash=" + couponFreeServiceCash +
                ", couponPrice=" + couponPrice +
                ", couponRealPrice=" + couponRealPrice +
                ", couponServiceCost=" + couponServiceCost +
                ", discount=" + discount +
                ", discountAllPrice=" + discountAllPrice +
                ", discountAllRealPrice=" + discountAllRealPrice +
                ", discountCount=" + discountCount +
                ", discountFreeServiceCash=" + discountFreeServiceCash +
                ", discountPrice=" + discountPrice +
                ", discountRealPrice=" + discountRealPrice +
                ", superDiscountCount=" + superDiscountCount +
                ", superDiscountPrice=" + superDiscountPrice +
                ", superDiscountRealPrice=" + superDiscountRealPrice +
                '}';
    }
}

package com.HyKj.UKeBao.model.businessManage.bean;

import java.io.Serializable;

/**
 * 订单记录
 */
public class OrderRecord implements Serializable {
    /**
     * 现金支付额度
     */
    private String cashQuota;
    /**
     * 是否可退款 是:true 否:false
     */
    private boolean canCancel;
    /**
     * 是否查看过 是:true 否:false
     */
    private boolean isSee;
    /**
     * 支付时间
     */
    private String paymentDate;
    /**
     * 折扣
     */
    private String discount;
    /**
     * 主键
     */
    private String id;
    /**
     * 头像
     */
    private String wxHeadimage;
    /**
     * 市场价格
     */
    private String price;
    /**
     * 支付类型标识
     */
    private String payType;
    /**
     * 实际价格
     */
    private String realPrice;
    /**
     * 赠送积分
     */
    private String sendIntegral;
    /**
     * 订单积分值
     */
    private String integral;
    /**
     * 下单时间
     */
    private String createDate;
    /**
     * 会员卡号或者手机号
     */
    private String menberCardNoPhone;
    /**
     * 验证码
     */
    private String checkNo;
    /**
     * 会员名
     */
    private String menberName;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 订单状态对应的中文
     */
    private String statusName;
    /**
     * 订单号
     */
    private String no;
    /**
     * 订单结束时间
     */
    private String endDate;
    /**
     * 不享优惠，默认值为0，说明该笔订单没有不享受优惠金额
     */
    private double freeServiceQuota;
    /**
     * 卡券id 没有卡券时该值为0 且下面两个字段不会返回key
     */
    private int menberCouponId;
    /**
     * 卡劵优惠 卡券金额（减少的额度）
     */
    private double couponQuota;
    /**
     * 卡劵优惠 卡券满deduction金额可用（需满足的额度）
     */
    private double deduction;
    /**
     * 服务费
     * */
    private double serviceCostPrice;

    @Override
    public String toString() {
        return "OrderRecord{" +
                "canCancel=" + canCancel +
                ", cashQuota='" + cashQuota + '\'' +
                ", isSee=" + isSee +
                ", paymentDate='" + paymentDate + '\'' +
                ", discount='" + discount + '\'' +
                ", id='" + id + '\'' +
                ", wxHeadimage='" + wxHeadimage + '\'' +
                ", price='" + price + '\'' +
                ", payType='" + payType + '\'' +
                ", realPrice='" + realPrice + '\'' +
                ", sendIntegral='" + sendIntegral + '\'' +
                ", integral='" + integral + '\'' +
                ", createDate='" + createDate + '\'' +
                ", menberCardNoPhone='" + menberCardNoPhone + '\'' +
                ", checkNo='" + checkNo + '\'' +
                ", menberName='" + menberName + '\'' +
                ", status='" + status + '\'' +
                ", orderType='" + orderType + '\'' +
                ", statusName='" + statusName + '\'' +
                ", no='" + no + '\'' +
                ", endDate='" + endDate + '\'' +
                ", freeServiceQuota=" + freeServiceQuota +
                ", menberCouponId=" + menberCouponId +
                ", couponQuota=" + couponQuota +
                ", deduction=" + deduction +
                ", serviceCostPrice=" + serviceCostPrice +
                '}';
    }

    public String getServiceCostPrice() {
        return serviceCostPrice+"元";
    }

    public void setServiceCostPrice(double serviceCostPrice) {
        this.serviceCostPrice = serviceCostPrice;
    }

    public double getFreeServiceQuota() {
        return freeServiceQuota;
    }

    public void setFreeServiceQuota(double freeServiceQuota) {
        this.freeServiceQuota = freeServiceQuota;
    }

    public int getMenberCouponId() {
        return menberCouponId;
    }

    public void setMenberCouponId(int menberCouponId) {
        this.menberCouponId = menberCouponId;
    }

    public double getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(double couponQuota) {
        this.couponQuota = couponQuota;
    }

    public double getDeduction() {
        return deduction;
    }

    public void setDeduction(double deduction) {
        this.deduction = deduction;
    }

    public String getCashQuota() {
        return cashQuota+"元"+"(卡劵抵扣-"+couponQuota+"元)";
    }

    public void setCashQuota(String cashQuota) {
        this.cashQuota = cashQuota;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    public boolean isSee() {
        return isSee;
    }

    public void setSee(boolean isSee) {
        this.isSee = isSee;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getCheckNo() {
        return checkNo;
    }

    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    public String getMenberName() {
        return menberName;
    }

    public void setMenberName(String menberName) {
        this.menberName = menberName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxHeadimage() {
        return wxHeadimage;
    }

    public void setWxHeadimage(String wxHeadimage) {
        this.wxHeadimage = wxHeadimage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getSendIntegral() {
        return sendIntegral;
    }

    public void setSendIntegral(String sendIntegral) {
        this.sendIntegral = sendIntegral;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getMenberCardNoPhone() {
        return menberCardNoPhone;
    }

    public void setMenberCardNoPhone(String menberCardNoPhone) {
        this.menberCardNoPhone = menberCardNoPhone;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

}

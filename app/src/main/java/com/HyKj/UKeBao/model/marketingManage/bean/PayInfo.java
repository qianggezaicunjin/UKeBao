package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 支付信息
 * Created by Administrator on 2016/10/24.
 */
public class PayInfo extends Object{
    public String context;//返回内容

    public String integralQuota;//总积分

    public String image;//广告图片

    public String count;//总个数

    public String businessStoreShowmanshipId;//揽客信息id

    public PayResult payResult;//第三方支付签名后数据对象（微信或支付宝）

    public String getBusinessStoreShowmanshipId() {
        return businessStoreShowmanshipId;
    }

    public void setBusinessStoreShowmanshipId(String businessStoreShowmanshipId) {
        this.businessStoreShowmanshipId = businessStoreShowmanshipId;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntegralQuota() {
        return integralQuota;
    }

    public void setIntegralQuota(String integralQuota) {
        this.integralQuota = integralQuota;
    }

    public PayResult getPayResult() {
        return payResult;
    }

    public void setPayResult(PayResult payResult) {
        this.payResult = payResult;
    }

    @Override
    public String toString() {
        return "PayInfo{" +
                "businessStoreShowmanshipId='" + businessStoreShowmanshipId + '\'' +
                ", context='" + context + '\'' +
                ", integralQuota='" + integralQuota + '\'' +
                ", image='" + image + '\'' +
                ", count='" + count + '\'' +
                ", payResult=" + payResult +
                '}';
    }
}

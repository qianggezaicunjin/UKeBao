package com.HyKj.UKeBao.model.marketingManage.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class ExchangeInfo {
    public double allIntegral;//积分结算总额

    public double allPrice;//结算总金额

    public int orderType;//订单类型（1:积分对换）

    public List<ProductLists> productList;//产品信息

    public String no;//订单号

    public String getNo() {
        return "订单号为:"+no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public double getAllIntegral() {
        return allIntegral;
    }

    public void setAllIntegral(double allIntegral) {
        this.allIntegral = allIntegral;
    }

    public String getAllPrice() {
        return "￥" +allPrice+ "元";
    }

    public void setAllPrice(double allPrice) {
        this.allPrice = allPrice;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public List<ProductLists> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductLists> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ExchangeInfo{" +
                "allIntegral=" + allIntegral +
                ", allPrice=" + allPrice +
                ", orderType=" + orderType +
                ", productList=" + productList +
                ", no='" + no + '\'' +
                '}';
    }
}

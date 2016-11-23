package com.HyKj.UKeBao.model.marketingManage.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 兑换信息
 */
public class ProductLists implements Serializable {
    public int count;

    public String no;//订单号

    public int extractType;

    public List<String> goodsImgs;//商品图片地址数组

    public String[] goodsThumbs;

    private String name;//商品名称

    public int id;

    public String info;

    public int integral;

    public int limitDiscountBuy;//限制购买数

    public int productAmount;//单品购买数量

    public int marketPrice;

    public String realPrice;//单商品结算价

    public List<String> getGoodsImgs() {
        return goodsImgs;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getExtractType() {
        return extractType;
    }

    public void setExtractType(int extractType) {
        this.extractType = extractType;
    }


    public String[] getGoodsThumbs() {
        return goodsThumbs;
    }

    public void setGoodsThumbs(String[] goodsThumbs) {
        this.goodsThumbs = goodsThumbs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getLimitDiscountBuy() {
        return limitDiscountBuy;
    }

    public void setLimitDiscountBuy(int limitDiscountBuy) {
        this.limitDiscountBuy = limitDiscountBuy;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
    }

    public void setGoodsImgs(List<String> goodsImgs) {
        this.goodsImgs = goodsImgs;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProductLists{" +
                "count=" + count +
                ", no='" + no + '\'' +
                ", extractType=" + extractType +
                ", goodsImgs=" + goodsImgs +
                ", goodsThumbs=" + Arrays.toString(goodsThumbs) +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", info='" + info + '\'' +
                ", integral=" + integral +
                ", limitDiscountBuy=" + limitDiscountBuy +
                ", productAmount=" + productAmount +
                ", marketPrice=" + marketPrice +
                ", realPrice='" + realPrice + '\'' +
                '}';
    }

}

package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 单个卡劵详情信息
 * Created by Administrator on 2016/10/18.
 */
public class CardDetail {
    public String deduction;// 消费满多少

    public String price;// 减多少

    public String menberGetCount;// 已领取

    public String menberUseCount;// 已使用

    public String inventory;// 库存

    public String distance;//距离

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getInventory() {
        return inventory;
    }

    public void setInventory(String inventory) {
        this.inventory = inventory;
    }

    public String getMenberGetCount() {
        return menberGetCount;
    }

    public void setMenberGetCount(String menberGetCount) {
        this.menberGetCount = menberGetCount;
    }

    public String getMenberUseCount() {
        return menberUseCount;
    }

    public void setMenberUseCount(String menberUseCount) {
        this.menberUseCount = menberUseCount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CardDetail{" +
                "deduction='" + deduction + '\'' +
                ", price='" + price + '\'' +
                ", menberGetCount='" + menberGetCount + '\'' +
                ", menberUseCount='" + menberUseCount + '\'' +
                ", inventory='" + inventory + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}

package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 *  卡劵列表信息
 * Created by Administrator on 2016/10/29.
 */
public class CardListInfo {
    public int  inventory;//库存

    public int deduction;//消费满多少可以使用

    public float price;//抵现金额

    public String startTime;//有效开始时间

    public String endTime;//有效结束时间

    public int getLimit;//会员可领取数量

    public int menberGetCount;//已被会员领取数

    public int menberUseCount;//已被会员使用数

    public String details;//详情

    public int total;

    public int id;

    public int getDeduction() {
        return deduction;
    }

    public void setDeduction(int deduction) {
        this.deduction = deduction;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getGetLimit() {
        return getLimit;
    }

    public void setGetLimit(int getLimit) {
        this.getLimit = getLimit;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getMenberGetCount() {
        return menberGetCount;
    }

    public void setMenberGetCount(int menberGetCount) {
        this.menberGetCount = menberGetCount;
    }

    public int getMenberUseCount() {
        return menberUseCount;
    }

    public void setMenberUseCount(int menberUseCount) {
        this.menberUseCount = menberUseCount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CardListInfo{" +
                "deduction=" + deduction +
                ", inventory=" + inventory +
                ", price=" + price +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", getLimit=" + getLimit +
                ", menberGetCount=" + menberGetCount +
                ", menberUseCount=" + menberUseCount +
                ", details='" + details + '\'' +
                ", total=" + total +
                ", id=" + id +
                '}';
    }
}

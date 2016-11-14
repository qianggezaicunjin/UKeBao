package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * 获取升级vip信息
 * Created by Administrator on 2016/11/15.
 */
public class VipPayInfo {
    public String money;

    public String integral;

    public String cash;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "VipPayInfo{" +
                "cash='" + cash + '\'' +
                ", money='" + money + '\'' +
                ", integral='" + integral + '\'' +
                '}';
    }
}

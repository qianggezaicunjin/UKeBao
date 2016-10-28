package com.HyKj.UKeBao.model.marketingManage.bean;

public class RedPacketDetailInfo {
    private String menberName;//会员名

    private String integralQuota;//总积分

    private String integral;//领取积分

    private String wxHeadImage;//用户头像

    private String date;//领取日期

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getIntegralQuota() {
        return integralQuota;
    }

    public void setIntegralQuota(String integralQuota) {
        this.integralQuota = integralQuota;
    }

    public String getMenberName() {
        return menberName;
    }

    public void setMenberName(String menberName) {
        this.menberName = menberName;
    }

    public String getWxHeadImage() {
        return wxHeadImage;
    }

    public void setWxHeadImage(String wxHeadImage) {
        this.wxHeadImage = wxHeadImage;
    }

    @Override
    public String toString() {
        return "RedPacketDetailInfo{" +
                "date='" + date + '\'' +
                ", menberName='" + menberName + '\'' +
                ", integralQuota='" + integralQuota + '\'' +
                ", integral='" + integral + '\'' +
                ", wxHeadImage='" + wxHeadImage + '\'' +
                '}';
    }
}

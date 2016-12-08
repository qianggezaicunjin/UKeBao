package com.HyKj.UKeBao.model.marketingManage.bean;

/**
 * Created by Administrator on 2016/12/7.
 */
public  class Menber {
    public String name;//会员名称

    public String wxHeadimage;//会员头像地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxHeadimage() {
        return wxHeadimage;
    }

    public void setWxHeadimage(String wxHeadimage) {
        this.wxHeadimage = wxHeadimage;
    }

    @Override
    public String toString() {
        return "Menber{" +
                "name='" + name + '\'' +
                ", wxHeadimage='" + wxHeadimage + '\'' +
                '}';
    }
}

package com.HyKj.UKeBao.model.businessManage.businessSettings.bean;

/**
 * 商品信息
 * Created by Administrator on 2016/11/13.
 */
public class GoodsInfo {
    public Integer id;

    public String name;//商品名称

    public String src;//图片地址

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "GoodsInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}

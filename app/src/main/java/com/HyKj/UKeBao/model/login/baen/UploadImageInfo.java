package com.HyKj.UKeBao.model.login.baen;

/**
 * Created by Administrator on 2016/9/3.
 */
public class UploadImageInfo {

    public String imagePrefix;//服务器域名

    public String imageSrc;//图片路径

    public String getImagePrefix() {
        return imagePrefix;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Override
    public String toString() {
        return "UploadImageInfo{" +
                "imagePrefix='" + imagePrefix + '\'' +
                ", imageSrc='" + imageSrc + '\'' +
                '}';
    }
}

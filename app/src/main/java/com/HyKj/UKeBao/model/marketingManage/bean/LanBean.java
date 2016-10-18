package com.HyKj.UKeBao.model.marketingManage.bean;

public class LanBean {
	String context;
	double curryentLatitude;
	double currryentLongtitude;
	double distance;
	int count;
	int id;
	String nameTiltle;
	int ids;
	String surplusCount;//剩余红包数
	String imageUrl;
	public int getIds() {
		return ids;
	}
	public void setIds(int ids) {
		this.ids = ids;
	}
	public String getSurplusCount() {
		return surplusCount;
	}
	public void setSurplusCount(String surplusCount) {
		this.surplusCount = surplusCount;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getIntegralQuota() {
		return integralQuota;
	}
	public void setIntegralQuota(String integralQuota) {
		this.integralQuota = integralQuota;
	}
	public String getSurplusQuota() {
		return surplusQuota;
	}
	public void setSurplusQuota(String surplusQuota) {
		this.surplusQuota = surplusQuota;
	}
	String integralQuota;//积分总数
	String surplusQuota;//剩余积分

	@Override
	public String toString() {
		return "LanBean [context=" + context + ", curryentLatitude="
				+ curryentLatitude + ", currryentLongtitude="
				+ currryentLongtitude + ", distance=" + distance + ", count="
				+ count + ", id=" + id + ", nameTiltle=" + nameTiltle + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameTiltle() {
		return nameTiltle;
	}
	public void setNameTiltle(String nameTiltle) {
		this.nameTiltle = nameTiltle;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public double getCurryentLatitude() {
		return curryentLatitude;
	}
	public void setCurryentLatitude(double curryentLatitude) {
		this.curryentLatitude = curryentLatitude;
	}
	public double getCurrryentLongtitude() {
		return currryentLongtitude;
	}
	public void setCurrryentLongtitude(double currryentLongtitude) {
		this.currryentLongtitude = currryentLongtitude;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}

package com.HyKj.UKeBao.model.marketingManage.bean;

public class CardAndRedPacketInfo {

	//		type	int	0:卡卷 1：红包
//	context	string	广告内容
//	count	int	总数量
//	distance	double	距离
//	id	int	主键
//	integralQuota	double	总积分
//	latitude	double	纬度
//	longitude	double	经度
//	payType	int	支付类型
//	status	int	状态
//	surplusCount	int	剩余数量
//	surplusQuota	double	剩余积分
//	time	string	添加时间
//	:-----	:-----	-----
//	createTime	string	添加时间
//	details	string	卡卷说明
//	endTime	string	结束时间
//	inventory	int	库存
//	startTime	string	开始时间
	private String context;
	private String count;
	private String distance;
	private String id;
	private String integralQuota;
	private String latitude;
	private String longitude;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	private String payType;
	private String status;
	private String  image;
	private String surplusCount;
	private String surplusQuota;
	private String time;
	private String type;
	private String createTime;
	private String details;
	private String endTime;
	private String inventory;
	private String startTime;
	private String usecode;

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIntegralQuota() {
		return integralQuota;
	}
	public void setIntegralQuota(String integralQuota) {
		this.integralQuota = integralQuota;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSurplusCount() {
		return surplusCount;
	}
	public void setSurplusCount(String surplusCount) {
		this.surplusCount = surplusCount;
	}
	public String getSurplusQuota() {
		return surplusQuota;
	}
	public void setSurplusQuota(String surplusQuota) {
		this.surplusQuota = surplusQuota;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getUsecode() {
		return usecode;
	}
	public void setUsecode(String usecode) {
		this.usecode = usecode;
	}

	@Override
	public String toString() {
		return "CardAndRedPacketInfo [context=" + context + ", count=" + count
				+ ", distance=" + distance + ", id=" + id + ", integralQuota="
				+ integralQuota + ", latitude=" + latitude + ", longitude="
				+ longitude + ", payType=" + payType + ", status=" + status
				+ ", image=" + image + ", surplusCount=" + surplusCount
				+ ", surplusQuota=" + surplusQuota + ", time=" + time
				+ ", type=" + type + ", createTime=" + createTime
				+ ", details=" + details + ", endTime=" + endTime
				+ ", inventory=" + inventory + ", startTime=" + startTime
				+ ", usecode=" + usecode + "]";
	}
}

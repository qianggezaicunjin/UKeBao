package com.HyKj.UKeBao.model.login.baen;


import com.HyKj.UKeBao.model.businessManage.businessSettings.bean.GoodsInfo;

import java.io.Serializable;
import java.util.List;

public class BusinessInfo implements Serializable {
	private static final long serialVersionUID = -2587644784526787538L;
	public boolean alreadyCollection;
	public BankMessage bankMessage;
	public String bankName;
	public String bankNo;
	public String bankUserName;
	public String businessArea;
	public double businessDiscount;
	public List<String> businessStoreImages;
	public double cash;
	public double changeIncome;
	public double freezeCash;
	public int id;
	public double integralScale;
	public String isDouble;
	public int isExamine;
	public String isExchange;
	public boolean isHavePay;
	public String isIntegral;
	public String isValue;
	public boolean isVip;
	public int limitCashOutQuota;
	public double menberCostCash;
	public double menberCostIntegral;
	public int messageCount;
	public double messageMark;
	public String projectDesc;
	public String ptype;
	public List<String> pictures;
	public String reward;
	public double serviceCost;
	public boolean status;
	public String storeDesc;
	public String stype;
	public String vip;
	public String businessName;// 店铺名称
	public String name;// 联系人姓名
	public String tel;// 联系人电话
	public String businessRegistrationNo;// 工商注册号
	public int category;
	public String province;// 省
	public String city;// 市
	public String area;// 区
	public String address;// 详细地址
	public double longitude;// 经度
	public double latitude;// 纬度
	public List<String> identityPicture;// 营业执照,身份证照,身份证背面照
	public String industryType;// 具体类型
	public String parentName;// 大类型
	public int discount;// 折扣
	public double integral;// 赠送积分
	public List<GoodsInfo> piList;//商品信息

	public List<GoodsInfo> getPiList() {
		return piList;
	}

	public void setPiList(List<GoodsInfo> piList) {
		this.piList = piList;
	}

	public List<String> getPictures() {
		return pictures;
	}
	public void setPictures(List<String> pictures) {
		this.pictures = pictures;
	}
	public BusinessInfo() {
		super();
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean isAlreadyCollection() {
		return alreadyCollection;
	}
	public void setAlreadyCollection(boolean alreadyCollection) {
		this.alreadyCollection = alreadyCollection;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public BankMessage getBankMessage() {
		return bankMessage;
	}
	public void setBankMessage(BankMessage bankMessage) {
		this.bankMessage = bankMessage;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankNo() {
		return bankNo;
	}
	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
	public String getBankUserName() {
		return bankUserName;
	}
	public void setBankUserName(String bankUserName) {
		this.bankUserName = bankUserName;
	}
	public String getBusinessArea() {
		return businessArea;
	}
	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public List<String> getBusinessStoreImages() {
		return businessStoreImages;
	}
	public void setBusinessStoreImages(List<String> businessStoreImages) {
		this.businessStoreImages = businessStoreImages;
	}
	//冻结金额+可提金额
	public String getCash() {
		return (cash+freezeCash)+"元";
	}
	public String getBusinessRegistrationNo() {
		return businessRegistrationNo;
	}
	public void setBusinessRegistrationNo(String businessRegistrationNo) {
		this.businessRegistrationNo = businessRegistrationNo;
	}
	public List<String> getIdentityPicture() {
		return identityPicture;
	}
	public void setIdentityPicture(List<String> identityPicture) {
		this.identityPicture = identityPicture;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public double getChangeIncome() {
		return changeIncome;
	}
	public void setChangeIncome(double changeIncome) {
		this.changeIncome = changeIncome;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public double getFreezeCash() {
		return freezeCash;
	}
	public void setFreezeCash(double freezeCash) {
		this.freezeCash = freezeCash;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIntegral() {
		return integral+"分";
	}
	public void setIntegral(double integral) {
		this.integral = integral;
	}
	public double getIntegralScale() {
		return integralScale;
	}
	public void setIntegralScale(double integralScale) {
		this.integralScale = integralScale;
	}
	public String getIsDouble() {
		return isDouble;
	}
	public void setIsDouble(String isDouble) {
		this.isDouble = isDouble;
	}
	public int getIsExamine() {
		return isExamine;
	}
	public void setIsExamine(int isExamine) {
		this.isExamine = isExamine;
	}
	public String getIsExchange() {
		return isExchange;
	}
	public void setIsExchange(String isExchange) {
		this.isExchange = isExchange;
	}
	public boolean isHavePay() {
		return isHavePay;
	}
	public void setHavePay(boolean isHavePay) {
		this.isHavePay = isHavePay;
	}
	public String getIsIntegral() {
		return isIntegral;
	}
	public void setIsIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}
	public String getIsValue() {
		return isValue;
	}
	public void setIsValue(String isValue) {
		this.isValue = isValue;
	}
	public boolean isVip() {
		return isVip;
	}
	public void setVip(boolean isVip) {
		this.isVip = isVip;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getLimitCashOutQuota() {
		return limitCashOutQuota;
	}
	public void setLimitCashOutQuota(int limitCashOutQuota) {
		this.limitCashOutQuota = limitCashOutQuota;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getMenberCostCash() {
		return menberCostCash;
	}
	public void setMenberCostCash(double menberCostCash) {
		this.menberCostCash = menberCostCash;
	}
	public double getMenberCostIntegral() {
		return menberCostIntegral;
	}
	public void setMenberCostIntegral(double menberCostIntegral) {
		this.menberCostIntegral = menberCostIntegral;
	}
	public int getMessageCount() {
		return messageCount;
	}
	public void setMessageCount(int messageCount) {
		this.messageCount = messageCount;
	}
	public double getMessageMark() {
		return messageMark;
	}
	public void setMessageMark(double messageMark) {
		this.messageMark = messageMark;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPtype() {
		return ptype+"-";
	}
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public double getServiceCost() {
		return serviceCost;
	}
	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getStoreDesc() {
		return storeDesc;
	}
	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}
	public String getStype() {
		return stype;
	}
	public void setStype(String stype) {
		this.stype = stype;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getBusinessDiscount() {
		return businessDiscount+"折";
	}
	public void setBusinessDiscount(double businessDiscount) {
		this.businessDiscount = businessDiscount;
	}
	@Override
	public String toString() {
		return "BusinessInfo [alreadyCollection=" + alreadyCollection
				+ ", bankMessage=" + bankMessage + ", bankName=" + bankName
				+ ", bankNo=" + bankNo + ", bankUserName=" + bankUserName
				+ ", businessArea=" + businessArea + ", businessDiscount="
				+ businessDiscount + ", businessStoreImages="
				+ businessStoreImages + ", cash=" + cash + ", changeIncome="
				+ changeIncome + ", freezeCash=" + freezeCash + ", id=" + id
				+ ", integralScale=" + integralScale + ", isDouble=" + isDouble
				+ ", isExamine=" + isExamine + ", isExchange=" + isExchange
				+ ", isHavePay=" + isHavePay + ", isIntegral=" + isIntegral
				+ ", isValue=" + isValue + ", isVip=" + isVip
				+ ", limitCashOutQuota=" + limitCashOutQuota
				+ ", menberCostCash=" + menberCostCash
				+ ", menberCostIntegral=" + menberCostIntegral
				+ ", messageCount=" + messageCount + ", messageMark="
				+ messageMark + ", projectDesc=" + projectDesc + ", ptype="
				+ ptype + ", pictures=" + pictures + ", reward=" + reward
				+ ", serviceCost=" + serviceCost + ", status=" + status
				+ ", storeDesc=" + storeDesc + ", stype=" + stype + ", vip="
				+ vip + ", businessName=" + businessName + ", name=" + name
				+ ", tel=" + tel + ", businessRegistrationNo="
				+ businessRegistrationNo + ", category=" + category
				+ ", province=" + province + ", city=" + city + ", area="
				+ area + ", address=" + address + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", identityPicture="
				+ identityPicture + ", industryType=" + industryType
				+ ", parentName=" + parentName + ", discount=" + discount
				+ ", integral=" + integral + "]";
	}

}

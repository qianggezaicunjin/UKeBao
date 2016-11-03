package com.HyKj.UKeBao.model.marketingManage.bean;

import java.io.Serializable;
import java.util.List;

public class Rows implements Serializable{
	public static final long serialVersionUID = 4374170588767414044L;
	public Double allIntegral;
	public String businessName;
	public int checkNo;
	public String createDate;
	public String endDate;
	public int id;
	public String no;
	public String menberName;
	public String wxHeadimage;
	public int status;
	public List<ProductLists> productLists;
	public Rows() {
		
	}

	public Double getAllIntegral() {
		return allIntegral;
	}
	public void setAllIntegral(Double allIntegral) {
		this.allIntegral = allIntegral;
	}
	
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public int getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(int checkNo) {
		this.checkNo = checkNo;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getMenberName() {
		return menberName;
	}
	public void setMenberName(String menberName) {
		this.menberName = menberName;
	}
	public String getWxHeadimage() {
		return wxHeadimage;
	}
	public void setWxHeadimage(String wxHeadimage) {
		this.wxHeadimage = wxHeadimage;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<ProductLists> getProductLists() {
		return productLists;
	}
	public void setProductLists(List<ProductLists> productLists) {
		this.productLists = productLists;
	}
	private String allRealPrice;
	public String getAllRealPrice() {
		return allRealPrice;
	}
	public void setAllRealPrice(String allRealPrice) {
		this.allRealPrice = allRealPrice;
	}
	@Override
	public String toString() {
		return "Rows [allIntegral=" + allIntegral + ", allRealPrice="
				+ allRealPrice + ", businessName=" + businessName
				+ ", checkNo=" + checkNo + ", createDate=" + createDate
				+ ", endDate=" + endDate + ", id=" + id + ", no=" + no
				+ ", menberName=" + menberName + ", wxHeadimage=" + wxHeadimage
				+ ", status=" + status + ", productLists=" + productLists + "]";
	}
	
}

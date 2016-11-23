package com.HyKj.UKeBao.model.businessManage.bean;

public class RedBackInfo {
	/**
	 * 广告内容
	 * */
	private String context;//
	/**
	 * 总数量
	 * */
	private String count;//总数量
	/**
	 * 剩余积分
	 * */
	private String surplusQuota;//剩余积分
	@Override
	public String toString() {
		return "CardBackInfo [context=" + context + ", count=" + count
				+ ", surplusQuota=" + surplusQuota + ", surplusCount="
				+ surplusCount + ", integralQuota=" + integralQuota + "]";
	}
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
	public String getSurplusQuota() {
		return surplusQuota;
	}
	public void setSurplusQuota(String surplusQuota) {
		this.surplusQuota = surplusQuota;
	}
	public String getSurplusCount() {
		return surplusCount;
	}
	public void setSurplusCount(String surplusCount) {
		this.surplusCount = surplusCount;
	}
	public String getIntegralQuota() {
		return integralQuota;
	}
	public void setIntegralQuota(String integralQuota) {
		this.integralQuota = integralQuota;
	}
	/**
	 * 剩余数量
	 * */
	private String surplusCount;//剩余数量
	/**
	 *总积分
	 * */
	private String integralQuota;//总积分
	
}

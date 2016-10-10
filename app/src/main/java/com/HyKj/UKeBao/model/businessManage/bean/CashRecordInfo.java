package com.HyKj.UKeBao.model.businessManage.bean;
/**
 * 商家现金流水记录
 * @author Administrator
 *
 */
public class CashRecordInfo {
	public double cash;//商家当前现金余额
	public String time;//交易时间
	public boolean type;//交易类型（true:收入，false:支出）
	public String info;//交易原因说明
	public double quota;//单笔交易金额
	public double id;//交易订单id
	
	public CashRecordInfo() {
		super();
	}

	public CashRecordInfo(double cash, String time, boolean type, String info,
						  double quota, double id) {
		super();
		this.cash = cash;
		this.time = time;
		this.type = type;
		this.info = info;
		this.quota = quota;
		this.id = id;
	}

	public double getCash() {
		return cash;
	}

	public void setCash(double cash) {
		this.cash = cash;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public double getquota() {
		return quota;
	}

	public void setquota(double quota) {
		this.quota = quota;
	}

	public double getId() {
		return id;
	}

	public void setId(double id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CashRecordInfo [cash=" + cash + ", time=" + time + ", type="
				+ type + ", info=" + info + ", quota=" + quota + ", id=" + id
				+ "]";
	}
	
}

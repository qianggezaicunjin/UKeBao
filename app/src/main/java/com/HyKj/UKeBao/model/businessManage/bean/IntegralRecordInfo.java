package com.HyKj.UKeBao.model.businessManage.bean;
/**
 * 商家积分流水记录
 * @author Administrator
 *
 */
public class IntegralRecordInfo {
	public double integral ;//商家当前积分余额
	public String time;//交易时间
	public boolean type;//交易类型（true:收入，false:支出）
	public String  info;//交易原因说明
	public double  quota;//单笔交易金额
	public int id;//交易订单id
	public double costCash;//会员消费金额
	public String menberNo;//受赠会员卡号
	
	
	public IntegralRecordInfo() {
		super();
	}


	public IntegralRecordInfo(double integral, String time, boolean type,
							  String info, double quota, int id, double costCash, String menberNo) {
		super();
		this.integral = integral;
		this.time = time;
		this.type = type;
		this.info = info;
		this.quota = quota;
		this.id = id;
		this.costCash = costCash;
		this.menberNo = menberNo;
	}


	public double getIntegral() {
		return integral;
	}


	public void setIntegral(double integral) {
		this.integral = integral;
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


	public double getQuota() {
		return quota;
	}


	public void setQuota(double quota) {
		this.quota = quota;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getCostCash() {
		return costCash;
	}


	public void setCostCash(double costCash) {
		this.costCash = costCash;
	}


	public String getMenberNo() {
		return menberNo;
	}


	public void setMenberNo(String menberNo) {
		this.menberNo = menberNo;
	}


	@Override
	public String toString() {
		return "IntegralRecordInfo [integral=" + integral + ", time=" + time
				+ ", type=" + type + ", info=" + info + ", quota=" + quota
				+ ", id=" + id + ", costCash=" + costCash + ", menberNo="
				+ menberNo + "]";
	}
	
}

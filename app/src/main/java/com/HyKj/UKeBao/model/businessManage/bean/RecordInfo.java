package com.HyKj.UKeBao.model.businessManage.bean;

/**
 * 数据记录实体类
 * 
 * @author Administrator
 * 
 */
public class RecordInfo {
	/**
	 * 变动原因,数据类型,1：充值积分 2：买单成功 3：赠送会员
	 */
	public int recodeType;
	/**
	 * 变动原因,对应的名字
	 */
	public String recodeTypeName;
	/**
	 * 数量
	 */
	public String recodeAccount;
	/**
	 * 日期
	 */
	public String recodeDate;
	/**
	 * 会员（名字，手机号/卡号）
	 */
	public String memberName;

	public RecordInfo(int recodeType, String recodeTypeName,
					  String recodeAccount, String recodeDate, String memberName) {
		super();
		this.recodeType = recodeType;
		this.recodeTypeName = recodeTypeName;
		this.recodeAccount = recodeAccount;
		this.recodeDate = recodeDate;
		this.memberName = memberName;
	}

	public int getRecodeType() {
		return recodeType;
	}

	public void setRecodeType(int recodeType) {
		this.recodeType = recodeType;
	}

	public String getRecodeTypeName() {
		return recodeTypeName;
	}

	public void setRecodeTypeName(String recodeTypeName) {
		this.recodeTypeName = recodeTypeName;
	}

	public String getRecodeAccount() {
		return recodeAccount;
	}

	public void setRecodeAccount(String recodeAccount) {
		this.recodeAccount = recodeAccount;
	}

	public String getRecodeDate() {
		return recodeDate;
	}

	public void setRecodeDate(String recodeDate) {
		this.recodeDate = recodeDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@Override
	public String toString() {
		return "RecodeInfo [recodeType=" + recodeType + ", recodeTypeName="
				+ recodeTypeName + ", recodeAccount=" + recodeAccount
				+ ", recodeDate=" + recodeDate + ", memberName=" + memberName
				+ "]";
	}

}

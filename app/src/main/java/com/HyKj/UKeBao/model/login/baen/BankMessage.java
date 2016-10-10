package com.HyKj.UKeBao.model.login.baen;

import java.io.Serializable;

public class BankMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5856536929263967743L;
	public String fBankName;
	public String fBankNo;
	public String fName;
	public int id;
	
	public BankMessage() {
		super();
	}
	public String getfBankName() {
		return fBankName;
	}
	public void setfBankName(String fBankName) {
		this.fBankName = fBankName;
	}
	public String getfBankNo() {
		return fBankNo;
	}
	public void setfBankNo(String fBankNo) {
		this.fBankNo = fBankNo;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}

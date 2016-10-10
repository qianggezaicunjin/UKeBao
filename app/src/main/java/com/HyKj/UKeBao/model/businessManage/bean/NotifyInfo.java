package com.HyKj.UKeBao.model.businessManage.bean;
/**
 * 公告数据
 * @author Administrator
 *
 */
public class NotifyInfo {
	public String addTime;
	public String context;
	public String id;
	public String receiveIdentity;
	public String status;
	public String title;
	
	
	public NotifyInfo() {
		super();
	}

	public NotifyInfo(String addTime, String context, String id,
					  String receiveIdentity, String status, String title) {
		super();
		this.addTime = addTime;
		this.context = context;
		this.id = id;
		this.receiveIdentity = receiveIdentity;
		this.status = status;
		this.title = title;
	}
	
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReceiveIdentity() {
		return receiveIdentity;
	}
	public void setReceiveIdentity(String receiveIdentity) {
		this.receiveIdentity = receiveIdentity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "NotifyInfo [addTime=" + addTime + ", context=" + context
				+ ", id=" + id + ", receiveIdentity=" + receiveIdentity
				+ ", status=" + status + ", title=" + title + "]";
	}
	
}

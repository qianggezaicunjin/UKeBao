package com.HyKj.UKeBao.model.login.baen;


import java.util.ArrayList;
import java.util.List;

/**
 * 商品类型
 * @author Administrator
 *
 */
public class ProductCategory {
	public String msg;
	public String status;
	public String success;
	public List<CategoryInfo> rows=new ArrayList<CategoryInfo>();

	public ProductCategory() {
		super();
	}
	public ProductCategory(String msg, String status, String success,
						   List<CategoryInfo> rows) {
		super();
		this.msg = msg;
		this.status = status;
		this.success = success;
		this.rows = rows;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<CategoryInfo> getCategory() {
		return rows;
	}
	public void setCategory(List<CategoryInfo> categoryInfo) {
		this.rows = categoryInfo;
	}
	@Override
	public String toString() {
		return "ProductCategory [msg=" + msg + ", status=" + status
				+ ", success=" + success + ", category=" + rows + "]";
	}




}

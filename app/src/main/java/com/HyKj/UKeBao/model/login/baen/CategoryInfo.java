package com.HyKj.UKeBao.model.login.baen;

public class CategoryInfo {
	public int discount;
	public int id;
	public int integealBack;
	public int integral;
	public String name;
	public int parent;
	public String parentName;
	public int serviceCost;
	
	public CategoryInfo() {
		super();
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIntegealBack() {
		return integealBack;
	}

	public void setIntegealBack(int integealBack) {
		this.integealBack = integealBack;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(int serviceCost) {
		this.serviceCost = serviceCost;
	}

	@Override
	public String toString() {
		return "CategoryInfo [discount=" + discount + ", id=" + id
				+ ", integealBack=" + integealBack + ", integral=" + integral
				+ ", name=" + name + ", parent=" + parent + ", parentName="
				+ parentName + ", serviceCost=" + serviceCost + "]";
	}
	
	
	
	
}

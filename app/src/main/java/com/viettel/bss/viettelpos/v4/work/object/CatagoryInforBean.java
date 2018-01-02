package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

class CatagoryInforBean implements Serializable, Comparable<CatagoryInforBean> {
	private String dataType;
	private Integer displayOrder;
	private String inforCode;
	private String inforName;
	private Integer isLeaves;
	private Integer isRequire;
	private Integer isTitle;
	private String parentCode;
	private Integer status;
	private Integer type;
	private String value;

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getInforCode() {
		return inforCode;
	}

	public void setInforCode(String inforCode) {
		this.inforCode = inforCode;
	}

	public String getInforName() {
		return inforName;
	}

	public void setInforName(String inforName) {
		this.inforName = inforName;
	}

	public Integer getIsLeaves() {
		return isLeaves;
	}

	public void setIsLeaves(Integer isLeaves) {
		this.isLeaves = isLeaves;
	}

	public Integer getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(Integer isRequire) {
		this.isRequire = isRequire;
	}

	public Integer getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(Integer isTitle) {
		this.isTitle = isTitle;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"CatagoryInforBean\":{\"dataType\":\"%s\", \"displayOrder\":\"%s\", \"inforCode\":\"%s\", \"inforName\":\"%s\", \"isLeaves\":\"%s\", \"isRequire\":\"%s\", \"isTitle\":\"%s\", \"parentCode\":\"%s\", \"status\":\"%s\", \"type\":\"%s\", \"value\":\"%s\"}}",
				dataType, displayOrder, inforCode, inforName, isLeaves, isRequire, isTitle, parentCode, status, type, value);
	}

	@Override
	public int compareTo(CatagoryInforBean another) {
		// TODO Auto-generated method stub
		return 0;
	}

}

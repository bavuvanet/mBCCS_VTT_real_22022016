package com.viettel.bss.viettelpos.v4.charge.object;

import java.util.List;

public class ContractFormMngt {
	private String code;
	private String name;
	private Boolean isCheck = false;
	private List<ContractFormMngt> lstChild;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Boolean isCheck) {
		this.isCheck = isCheck;
	}

	public List<ContractFormMngt> getLstChild() {
		return lstChild;
	}

	public void setLstChild(List<ContractFormMngt> lstChild) {
		this.lstChild = lstChild;
	}

}

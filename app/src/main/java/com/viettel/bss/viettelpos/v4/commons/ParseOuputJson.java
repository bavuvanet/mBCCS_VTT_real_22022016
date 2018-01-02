package com.viettel.bss.viettelpos.v4.commons;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
import com.viettel.bss.viettelpos.v4.charge.object.ContractFormMngtBean;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;

public class ParseOuputJson {
	
	@SerializedName("token")
	public String token;
	@SerializedName("errorCode")
	public String errorCode;
	@SerializedName("description")
	public String description;
	@SerializedName("lstCorporationCategoryBO")
	public ArrayList<CorporationCategoryBO> lstCorporationCategoryBO;
	@SerializedName("lstContractFormMngtBeanPayServs")
	public ArrayList<ContractFormMngtBean> lstContractFormMngtBeanPayServs = new ArrayList<ContractFormMngtBean>();
	
	public ArrayList<ContractFormMngtBean> getLstContractFormMngtBeanPayServs() {
		return lstContractFormMngtBeanPayServs;
	}

	public void setLstContractFormMngtBeanPayServs(ArrayList<ContractFormMngtBean> lstContractFormMngtBeanPayServs) {
		this.lstContractFormMngtBeanPayServs = lstContractFormMngtBeanPayServs;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<CorporationCategoryBO> getLstCorporationCategoryBO() {
		return lstCorporationCategoryBO;
	}

	public void setLstCorporationCategoryBO(ArrayList<CorporationCategoryBO> lstCorporationCategoryBO) {
		this.lstCorporationCategoryBO = lstCorporationCategoryBO;
	}
	
	
	
	
}

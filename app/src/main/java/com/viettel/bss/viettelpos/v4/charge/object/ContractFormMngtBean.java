package com.viettel.bss.viettelpos.v4.charge.object;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ContractFormMngtBean implements Serializable{
	@SerializedName("code")
	private String code;
	@SerializedName("name")
	private String name;
	
	@SerializedName("lstContractFormMngtBeanPayServ")
	private ArrayList<ContractFormMngtBean> lstContractFormMngtBeanPayServ = new ArrayList<ContractFormMngtBean>();
	
	private boolean check = false;
	
	
	

	public ArrayList<ContractFormMngtBean> getLstContractFormMngtBeanPayServ() {
		return lstContractFormMngtBeanPayServ;
	}

	public void setLstContractFormMngtBeanPayServ(ArrayList<ContractFormMngtBean> lstContractFormMngtBeanPayServ) {
		this.lstContractFormMngtBeanPayServ = lstContractFormMngtBeanPayServ;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	/**
	 * Gets the value of the code property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the value of the code property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setCode(String value) {
		this.code = value;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setName(String value) {
		this.name = value;
	}

	public ContractFormMngtBean(String code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public ContractFormMngtBean() {
		super();
	}

}

package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.work.object.Action;

@Root(name = "return", strict = false)
public class SaleOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;

	@ElementList(name = "lstProductSpecCharDTOs", entry = "lstProductSpecCharDTOs", required = false, inline = true)
	private ArrayList<ProductSpecCharObj> lstProductSpecCharDTOs;

	@Element(name = "custCollectId", required = false)
	private String custCollectId;
	@ElementList(name = "lstRecordProfile", entry = "lstRecordProfile", required = false, inline = true)
	private List<FormBean> lstRecordProfile;
	@ElementList(name = "lstAction", entry = "lstAction", required = false, inline = true)
	private List<Action> lstAction;
	public List<Action> getLstAction() {
		return lstAction;
	}

	public void setLstAction(List<Action> lstAction) {
		this.lstAction = lstAction;
	}

	public List<FormBean> getLstRecordProfile() {
		return lstRecordProfile;
	}

	public void setLstRecordProfile(List<FormBean> lstRecordProfile) {
		this.lstRecordProfile = lstRecordProfile;
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

	public ArrayList<ProductSpecCharObj> getLstProductSpecCharDTOs() {
		return lstProductSpecCharDTOs;
	}

	public void setLstProductSpecCharDTOs(
			ArrayList<ProductSpecCharObj> lstProductSpecCharDTOs) {
		this.lstProductSpecCharDTOs = lstProductSpecCharDTOs;
	}

	public String getCustCollectId() {
		return custCollectId;
	}

	public void setCustCollectId(String custCollectId) {
		this.custCollectId = custCollectId;
	}

}

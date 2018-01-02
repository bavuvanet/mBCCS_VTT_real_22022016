package com.viettel.bss.viettelpos.v4.sale.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class InventoryOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;

	@ElementList(name = "lstProductOfferingDTO", entry = "lstProductOfferingDTO", required = false, inline = true)
	private ArrayList<ProductOfferingDTO> lstProductOfferingDTO;

	public ArrayList<ProductOfferingDTO> getLstProductOfferingDTO() {
		return lstProductOfferingDTO;
	}

	public void setLstProductOfferingDTO(ArrayList<ProductOfferingDTO> lstProductOfferingDTO) {
		this.lstProductOfferingDTO = lstProductOfferingDTO;
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

}

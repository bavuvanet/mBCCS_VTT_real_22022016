package com.viettel.bss.viettelpos.v4.report.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class BonusOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstAttributeObjectBO", entry = "lstAttributeObjectBO", required = false, inline = true)
	private List<AttributeObjectBOArray> lstAttributeObjectBO;

	public List<AttributeObjectBOArray> getLstAttributeObjectBO() {
		return lstAttributeObjectBO;
	}

	public void setLstAttributeObjectBO(
			List<AttributeObjectBOArray> lstAttributeObjectBO) {
		this.lstAttributeObjectBO = lstAttributeObjectBO;
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

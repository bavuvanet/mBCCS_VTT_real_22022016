package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return",strict=false)
public class CommonParseOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstSubRelPros", entry = "lstSubRelPros", required = false, inline = true)
	private List<SubRelPro> lstSubRelPros ;
	public List<SubRelPro> getLstSubRelPros() {
		return lstSubRelPros;
	}
	public void setLstSubRelPros(List<SubRelPro> lstSubRelPros) {
		this.lstSubRelPros = lstSubRelPros;
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

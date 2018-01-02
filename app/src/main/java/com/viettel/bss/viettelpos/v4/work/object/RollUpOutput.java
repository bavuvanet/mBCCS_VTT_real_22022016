package com.viettel.bss.viettelpos.v4.work.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class RollUpOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstSaleProgram", entry = "lstSaleProgram", required = false, inline = true)
	private List<SaleProgramDTO> lstSaleProgram;
	@ElementList(name = "lstRegisterRollUpBo", entry = "lstRegisterRollUpBo", required = false, inline = true)
	private List<RegisterRollUpBO> lstRegisterRollUpBo;
	@ElementList(name = "lstRollUpBo", entry = "lstRollUpBo", required = false, inline = true)
	private List<RollUpBO> lstRollUpBo;
	
	
	
	public List<RollUpBO> getLstRollUpBo() {
		return lstRollUpBo;
	}

	public void setLstRollUpBo(List<RollUpBO> lstRollUpBo) {
		this.lstRollUpBo = lstRollUpBo;
	}

	public List<RegisterRollUpBO> getLstRegisterRollUpBo() {
		return lstRegisterRollUpBo;
	}

	public void setLstRegisterRollUpBo(List<RegisterRollUpBO> lstRegisterRollUpBo) {
		this.lstRegisterRollUpBo = lstRegisterRollUpBo;
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

	public List<SaleProgramDTO> getLstSaleProgram() {
		return lstSaleProgram;
	}

	public void setLstSaleProgram(List<SaleProgramDTO> lstSaleProgram) {
		this.lstSaleProgram = lstSaleProgram;
	}
}

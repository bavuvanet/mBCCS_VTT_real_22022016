package com.viettel.bss.viettelpos.v4.charge.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class PaymentOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstVerifyBean", entry = "lstVerifyBean", required = false, inline = true)
	private List<ChargeContractItem> lstVerifyBean;
	@Element(name = "msgDebitBean", required = false)
	private DebitBean	msgDebitBean;

	public DebitBean getMsgDebitBean() {
		return msgDebitBean;
	}

	public void setMsgDebitBean(DebitBean msgDebitBean) {
		this.msgDebitBean = msgDebitBean;
	}

	public List<ChargeContractItem> getLstVerifyBean() {
		return lstVerifyBean;
	}

	public void setLstVerifyBean(List<ChargeContractItem> lstVerifyBean) {
		this.lstVerifyBean = lstVerifyBean;
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

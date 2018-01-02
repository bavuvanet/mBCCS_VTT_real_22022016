package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "AccountBookBankplusDTO", strict = false)
public class AccountBookBankplusDTO {
	@Element(name = "openingBalance", required = false)
	private String openingBalance;
	@Element(name = "closingBalance", required = false)
	private String closingBalance;
	@Element(name = "requestTypeName", required = false)
	private String requestTypeName;
	@Element(name = "amountRequest", required = false)
	private String amountRequest;
	@Element(name = "createDate", required = false)
	private String createDate;
	@Element(name = "requestId", required = false)
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getAmountRequest() {
		return amountRequest;
	}

	public void setAmountRequest(String amountRequest) {
		this.amountRequest = amountRequest;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getRequestTypeName() {
		return requestTypeName;
	}

	public void setRequestTypeName(String requestTypeName) {
		this.requestTypeName = requestTypeName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}

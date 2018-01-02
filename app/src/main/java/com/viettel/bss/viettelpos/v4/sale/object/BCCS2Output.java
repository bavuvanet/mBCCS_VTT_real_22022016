package com.viettel.bss.viettelpos.v4.sale.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "return", strict = false)
public class BCCS2Output {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "historyInforCardNumberDTO", required = false)
	private HistoryInforCardNumberDTO historyInforCardNumberDTO;
	@Element(name = "content", required = false)
	private DataCell dataCell;
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
	public HistoryInforCardNumberDTO getHistoryInforCardNumberDTO() {
		return historyInforCardNumberDTO;
	}
	public void setHistoryInforCardNumberDTO(
			HistoryInforCardNumberDTO historyInforCardNumberDTO) {
		this.historyInforCardNumberDTO = historyInforCardNumberDTO;
	}

	public DataCell getDataCell() {
		return dataCell;
	}

	public void setDataCell(DataCell dataCell) {
		this.dataCell = dataCell;
	}
}

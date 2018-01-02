package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;
import java.util.Date;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "return",strict=false)
public class StockTransAction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Element(name = "actionCode", required = false)
	private String expCode;

	private Date exportDate;
	
	@Element(name = "stockTransId", required = false)
	private Long stockTransId;
	
	@Element(name = "createDatetime",required=false)
	private String createDatetime;

	public String getExpCode() {
		return expCode;
	}

	public void setExpCode(String expCode) {
		this.expCode = expCode;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public Long getStockTransId() {
		return stockTransId;
	}

	public void setStockTransId(Long stockTransId) {
		this.stockTransId = stockTransId;
	}

	public String getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(String createDatetime) {
		this.createDatetime = createDatetime;
	}

}

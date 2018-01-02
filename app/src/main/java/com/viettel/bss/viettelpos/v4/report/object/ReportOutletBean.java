package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReportOutletBean implements Serializable {

    private String stockTypeName;
    private String stockTypeId;
    private String exportOtherQuantity;
    private String exportCusQuantity;
    private String unsoldGood;
    private String importQuantity;
	public String getStockTypeName() {
		return stockTypeName;
	}
	public void setStockTypeName(String stockTypeName) {
		this.stockTypeName = stockTypeName;
	}
	public String getStockTypeId() {
		return stockTypeId;
	}
	public void setStockTypeId(String stockTypeId) {
		this.stockTypeId = stockTypeId;
	}
	public String getExportOtherQuantity() {
		return exportOtherQuantity;
	}
	public void setExportOtherQuantity(String exportOtherQuantity) {
		this.exportOtherQuantity = exportOtherQuantity;
	}
	public String getExportCusQuantity() {
		return exportCusQuantity;
	}
	public void setExportCusQuantity(String exportCusQuantity) {
		this.exportCusQuantity = exportCusQuantity;
	}
	public String getUnsoldGood() {
		return unsoldGood;
	}
	public void setUnsoldGood(String unsoldGood) {
		this.unsoldGood = unsoldGood;
	}
	public String getImportQuantity() {
		return importQuantity;
	}
	public void setImportQuantity(String importQuantity) {
		this.importQuantity = importQuantity;
	}
	
    
}

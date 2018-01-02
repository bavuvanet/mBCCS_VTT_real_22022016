package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class SupplyBaseBean {
    private String price;
    private String programMonth;
    private String label;
    
	public SupplyBaseBean() {
		super();
	}
	public SupplyBaseBean(String price, String programMonth) {
		super();
		this.price = price;
		this.programMonth = programMonth;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getProgramMonth() {
		return programMonth;
	}
	public void setProgramMonth(String programMonth) {
		this.programMonth = programMonth;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		if (label != null) {
			return label;
		}
		return programMonth;
	}
    
}

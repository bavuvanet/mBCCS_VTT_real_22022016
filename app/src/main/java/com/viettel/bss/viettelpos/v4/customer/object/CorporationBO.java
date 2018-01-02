package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "CorporationBO",strict=false)
public class CorporationBO implements Serializable{
	@Element(name = "corporationId", required = false)
	private String corporationId;
	@Element(name = "corporationName", required = false)
	private String corporationName;
	@Element(name = "corpTaxCode", required = false)
	private String corpTaxCode;
	@Element(name = "corpBussinessLiensce", required = false)
	private String corpBussinessLiensce;
	@ElementList(name = "lstCategoryInfor", entry = "lstCategoryInfor", required = false, inline = true)
	private List<CorporationCategoryBO> lstCategoryInfor;
	
	@Element(name = "address", required = false)
	private String address;
	
	@Element(name = "establishDate", required = false)
	private String establishDate;

	@Element(name = "status", required = false)
	private String status;
	
	@Element(name = "statusStr", required = false)
	private String statusStr;
	
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusStr() {
		return statusStr;
	}
	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEstablishDate() {
		return establishDate;
	}
	public void setEstablishDate(String establishDate) {
		this.establishDate = establishDate;
	}
	public String getCorporationId() {
		return corporationId;
	}
	public void setCorporationId(String corporationId) {
		this.corporationId = corporationId;
	}
	public String getCorporationName() {
		return corporationName;
	}
	public void setCorporationName(String corporationName) {
		this.corporationName = corporationName;
	}
	public String getCorpTaxCode() {
		return corpTaxCode;
	}
	public void setCorpTaxCode(String corpTaxCode) {
		this.corpTaxCode = corpTaxCode;
	}
	public String getCorpBussinessLiensce() {
		return corpBussinessLiensce;
	}
	public void setCorpBussinessLiensce(String corpBussinessLiensce) {
		this.corpBussinessLiensce = corpBussinessLiensce;
	}
	public List<CorporationCategoryBO> getLstCategoryInfor() {
		return lstCategoryInfor;
	}
	public void setLstCategoryInfor(List<CorporationCategoryBO> lstCategoryInfor) {
		this.lstCategoryInfor = lstCategoryInfor;
	}
	
}

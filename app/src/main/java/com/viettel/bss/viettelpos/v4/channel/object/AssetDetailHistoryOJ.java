package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(strict = false, name = "lstManageAssetBean")
public class AssetDetailHistoryOJ implements Serializable {
	
	@Element(name = "assetCode")
	private String code;
	@Element
	private String name;
	@Element
	private String value;
	@Element
	private String staffId;
	@Element
	private String note;
	@Element
	private String updateUserId;
	@Element(name = "quantity")
	private Long qty = 0L;     
	/*
	private Long shopId;     
    private Long assetId; 
    private String assetCode;   
    */
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
 
	
	
	

	
}

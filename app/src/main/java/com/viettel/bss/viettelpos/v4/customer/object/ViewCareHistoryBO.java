package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "return", strict = false)
public class ViewCareHistoryBO {
	@Element(name = "levelId", required = false)
	private Long levelId;
	@Element(name = "levelName", required = false)
	private String levelName;
	@Element(name = "levelValue", required = false)
	private Long levelValue;
	@Element(name = "reasonId", required = false)
	private Long reasonId;
	@Element(name = "reasonName", required = false)
	private String reasonName;
	@ElementList(name = "lstCusCareSubResult", entry = "lstCusCareSubResult", required = false, inline = true)
	private List<CustomerCareSubResultBO> lstCusCareSubResult = new ArrayList<>();
	
	public Long getLevelId() {
		return levelId;
	}
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Long getReasonId() {
		return reasonId;
	}
	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}
	public String getReasonName() {
		return reasonName;
	}
	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	public Long getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(Long levelValue) {
		this.levelValue = levelValue;
	}
	public List<CustomerCareSubResultBO> getLstCusCareSubResult() {
		return lstCusCareSubResult;
	}
	public void setLstCusCareSubResult(
			List<CustomerCareSubResultBO> lstCusCareSubResult) {
		this.lstCusCareSubResult = lstCusCareSubResult;
	}   
    
    
}

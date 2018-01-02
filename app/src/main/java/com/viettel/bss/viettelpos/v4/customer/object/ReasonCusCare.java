package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ReasonCusCare", strict = false)
public class ReasonCusCare {
	@ElementList(name = "lstDataReasonMapping", entry = "lstDataReasonMapping", required = false, inline = true)
	private List<DataReasonMapping> lstDataReasonMapping;
	@ElementList(name = "lstReasonCusCareProperty", entry = "lstReasonCusCareProperty", required = false, inline = true)
	private ArrayList<ReasonCusCareProperty> lstReasonCusCareProperty;
	@Element (name = "code", required = false)
	private String code;
	@Element (name = "levelReason", required = false)
	private Long levelReason;
	@Element (name = "nextLevel", required = false)
	private Integer nextLevel;
	@Element (name = "reasonId", required = false)
	private Long reasonId;
	@Element (name = "reasonName", required = false)
	private String reasonName;
	@Element (name = "reasonValue", required = false)
	private Long reasonValue;
	@Element (name = "reasonCode", required = false)
	private String reasonCode;
	@Element (name = "optionType", required = false)
	private Integer optionType;
	@Element (name = "isUpdate", required = false)
	private Integer isUpdate;
	@Element (name = "priority", required = false)
	private Integer priority;
	private boolean checked;
	private boolean isMultiChoice = false;
	
	public List<DataReasonMapping> getLstDataReasonMapping() {
		return lstDataReasonMapping;
	}
	public void setLstDataReasonMapping(List<DataReasonMapping> lstDataReasonMapping) {
		this.lstDataReasonMapping = lstDataReasonMapping;
	}
	public ArrayList<ReasonCusCareProperty> getLstReasonCusCareProperty() {
		return lstReasonCusCareProperty;
	}
	public void setLstReasonCusCareProperty(
			ArrayList<ReasonCusCareProperty> lstReasonCusCareProperty) {
		this.lstReasonCusCareProperty = lstReasonCusCareProperty;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getLevelReason() {
		return levelReason;
	}
	public void setLevelReason(Long levelReason) {
		this.levelReason = levelReason;
	}
	public Integer getNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(Integer nextLevel) {
		this.nextLevel = nextLevel;
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
	public Long getReasonValue() {
		return reasonValue;
	}
	public void setReasonValue(Long reasonValue) {
		this.reasonValue = reasonValue;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean isNextLevel(){
		return (nextLevel!= null && nextLevel >0);
	}
	public boolean isMultiChoice() {
		return isMultiChoice;
	}
	public void setMultiChoice(boolean isMultiChoice) {
		this.isMultiChoice = isMultiChoice;
	}
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public Integer getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Integer isUpdate) {
		this.isUpdate = isUpdate;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	
	
}

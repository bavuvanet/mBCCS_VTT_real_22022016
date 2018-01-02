package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ReasonCusCareResult", strict = false)
public class ReasonCusCareResult {
	@Element (name = "levelName", required = false)
	private String levelName;
	@Element (name = "levelValue", required = false)
	private Long levelValue;
	@Element (name = "optionType", required = false)
	private Integer optionType;
	@ElementList(name = "lstReasonCusCare", entry = "lstReasonCusCare", required = false, inline = true)
    private ArrayList<ReasonCusCare> lstReasonCusCare = new ArrayList<>();
    
	public Long getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(Long levelValue) {
		this.levelValue = levelValue;
	}
	public ArrayList<ReasonCusCare> getLstReasonCusCare() {
		return lstReasonCusCare;
	}
	public void setLstReasonCusCare(ArrayList<ReasonCusCare> lstReasonCusCare) {
		this.lstReasonCusCare = lstReasonCusCare;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getOptionType() {
		return optionType;
	}
	public void setOptionType(Integer optionType) {
		this.optionType = optionType;
	}
	public boolean isMultiChoice(){
    	return optionType!= null && optionType ==1;
    }
    
    
}

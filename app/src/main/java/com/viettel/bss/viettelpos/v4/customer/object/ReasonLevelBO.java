package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "ReasonLevelBO", strict = false)
public class ReasonLevelBO {
	@Element (name = "levelName", required = false)
	private String levelName;
	@Element (name = "levelValue", required = false)
	private Long levelValue;
	@Element (name = "optionType", required = false)
	private Integer optionType;
	@ElementList(name = "lstReasonDetailBO", entry = "lstReasonDetailBO", required = false, inline = true)
    private ArrayList<ReasonDetailBO> lstReasonDetailBO = new ArrayList<>();
    
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public Long getLevelValue() {
		return levelValue;
	}
	public void setLevelValue(Long levelValue) {
		this.levelValue = levelValue;
	}
	public ArrayList<ReasonDetailBO> getLstReasonDetailBO() {
		return lstReasonDetailBO;
	}
	public void setLstReasonDetailBO(ArrayList<ReasonDetailBO> lstReasonDetailBO) {
		this.lstReasonDetailBO = lstReasonDetailBO;
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

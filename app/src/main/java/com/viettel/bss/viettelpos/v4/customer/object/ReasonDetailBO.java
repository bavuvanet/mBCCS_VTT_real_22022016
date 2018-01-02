package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "reasonDetailBO",strict =false)
class ReasonDetailBO {
	@Element(name = "reasonName", required = false)
	private String reasonName;
	@Element(name = "levelReason", required = false)
	private Long levelReason;
	@Element(name = "nextLevel", required = false)
	private Long nextLevel;
	@Element(name = "reasonId", required = false)
	private Long reasonId;
	@Element(name = "reasonCode", required = false)
	private Long reasonCode;
	
	private boolean checked;

	
	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Long getLevelReason() {
		return levelReason;
	}

	public void setLevelReason(Long levelReason) {
		this.levelReason = levelReason;
	}


	public Long getNextLevel() {
		return nextLevel;
	}

	public void setNextLevel(Long nextLevel) {
		this.nextLevel = nextLevel;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}
	public boolean isNextLevel(){
		return (nextLevel!= null && nextLevel.intValue()>0);
	}

	public Long getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Long reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	
}

package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "DataReasonMapping", strict = false)
public class DataReasonMapping {
	@Element (name = "lastLevelValue", required = false)
	private Integer lastLevelValue;
	@Element (name = "lastReasonId", required = false)
	private Long lastReasonId;
	@Element (name = "nextLevel", required = false)
	private Integer nextLevel;
	
	public Integer getLastLevelValue() {
		return lastLevelValue;
	}
	public void setLastLevelValue(Integer lastLevelValue) {
		this.lastLevelValue = lastLevelValue;
	}
	public Long getLastReasonId() {
		return lastReasonId;
	}
	public void setLastReasonId(Long lastReasonId) {
		this.lastReasonId = lastReasonId;
	}
	public Integer getNextLevel() {
		return nextLevel;
	}
	public void setNextLevel(Integer nextLevel) {
		this.nextLevel = nextLevel;
	}
	
	
}

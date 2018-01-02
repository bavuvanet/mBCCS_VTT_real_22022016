package com.viettel.bss.viettelpos.v4.work.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "Action",strict=false)
public class Action {
	@Element(name = "actionCode", required = false)
	private String actionCode;
	@Element(name = "actionAuditId", required = false)
	private String actionAuditId;
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getActionAuditId() {
		return actionAuditId;
	}
	public void setActionAuditId(String actionAuditId) {
		this.actionAuditId = actionAuditId;
	}

	
	
}

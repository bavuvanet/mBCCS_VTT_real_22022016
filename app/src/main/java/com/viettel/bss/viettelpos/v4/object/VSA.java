package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bss.viettelpos.v4.commons.AttributeObject;

public class VSA extends AttributeObject {
	private String parentId;
	private String objectId;
	private String objectName;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getParentId() {
		return parentId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}

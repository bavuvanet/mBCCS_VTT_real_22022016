package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class ObjectDetailGroup implements Serializable {
	private String name ;
	private String code;
	private String id;
	private String haveOinfo;
	private String parentID;
	private String valueType;
	private String haveChild;
	private String value;
	private String isKey;
	private CollectedObjectInfo mCollectedObjectInfo;
	
	public ObjectDetailGroup(String name, String code, String id,
			String haveOinfo, String parentID, String valueType,
			String haveChild, String isKey) {
		super();
		this.name = name;
		this.code = code;
		this.id = id;
		this.haveOinfo = haveOinfo;
		this.parentID = parentID;
		this.valueType = valueType;
		this.haveChild = haveChild;
		this.isKey = isKey;
	}
	
	public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public CollectedObjectInfo getmCollectedObjectInfo() {
		return mCollectedObjectInfo;
	}

	public void setmCollectedObjectInfo(CollectedObjectInfo mCollectedObjectInfo) {
		this.mCollectedObjectInfo = mCollectedObjectInfo;
	}

	public String getHaveChild() {
		return haveChild;
	}
	public void setHaveChild(String haveChild) {
		this.haveChild = haveChild;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHaveOinfo() {
		return haveOinfo;
	}
	public void setHaveOinfo(String haveOinfo) {
		this.haveOinfo = haveOinfo;
	}
	public String getParentID() {
		return parentID;
	}
	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
}

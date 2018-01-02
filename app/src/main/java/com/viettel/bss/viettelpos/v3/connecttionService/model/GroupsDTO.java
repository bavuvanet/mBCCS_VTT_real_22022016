package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
@Root(name = "GroupsDTO", strict = false)
public class GroupsDTO implements Serializable{
	@Element(name = "groupId", required = false)
	private String groupId;
	@Element(name = "groupType", required = false)
	private String groupType;
	@Element(name = "name", required = false)
	private String name;
	
	@Element(name = "code", required = false)
	private String code;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	
	
	
	
}

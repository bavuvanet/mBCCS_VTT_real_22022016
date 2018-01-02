/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;

/**
 * 
 * 
 * @author HuyPQ15
 */
public class FormBean {
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "id", required = false)
	private String id;
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "require", required = false)
	private Long require;
	@Element(name = "typeId", required = false)
	private Long typeId;
	@Element(name = "status", required = false)
	private String status;
	private String groupParentId;



	public String getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}

	public Long getRequire() {
		return require;
	}

	public void setRequire(Long require) {
		this.require = require;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {

		return code + " - " + name;
	}
}

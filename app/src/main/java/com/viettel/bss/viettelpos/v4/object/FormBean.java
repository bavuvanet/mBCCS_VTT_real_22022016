package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bss.viettelpos.v4.work.object.Action;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "FormBean", strict = false)
public class FormBean {
	@Element(name = "id", required = false)
	private String id;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "code", required = false)
	private String code;
	private Boolean checked = false;
	private String groupParentId;

	@Element(name = "actions", required = false)
	private String actions;
	@Element(name = "require", required = false)
	private Long require;


	public FormBean() {
	}

	public FormBean(String id, String name) {
		super();
		this.name = name;
		this.id = id;
	}

	public FormBean(String name, String id, String code) {
		super();
		this.name = name;
		this.id = id;
		this.code = code;
	}

	public String getActions() {
		return actions;
	}

	public void setActions(String actions) {
		this.actions = actions;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getGroupParentId() {
		return groupParentId;
	}

	public void setGroupParentId(String groupParentId) {
		this.groupParentId = groupParentId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (code == null || code.isEmpty()) {
			return name;
		}
		return code + "-" + name;
	}

}

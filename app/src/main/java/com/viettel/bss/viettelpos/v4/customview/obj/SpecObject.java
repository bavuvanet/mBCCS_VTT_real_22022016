package com.viettel.bss.viettelpos.v4.customview.obj;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="SpecObject",strict = false)
public class SpecObject {
	@Element (name = "id", required = false)
	private int id;
	@Element (name = "code", required = false)
	private String code;
	@Element (name = "name", required = false)
	private String name;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SpecObject() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {

		return code + " " + name;
	}
}

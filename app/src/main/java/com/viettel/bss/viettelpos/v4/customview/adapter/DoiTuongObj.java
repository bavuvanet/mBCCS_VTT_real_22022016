package com.viettel.bss.viettelpos.v4.customview.adapter;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="DoiTuongObj",strict = false)
public class DoiTuongObj {
	@Element (name = "id", required = false)
	private int id;
	@Element (name = "code", required = false)
	private String code;
	@Element (name = "name", required = false)
	private String name;
	private String codeName;
	
	public DoiTuongObj(int id, String codeName, String code) {
		super();
		this.id = id;
		this.codeName = codeName;
		this.code = code;
	}
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public DoiTuongObj() {
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

		return code + name;
	}


	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	

}

package com.viettel.bss.viettelpos.v4.channel.object;

import java.io.Serializable;

public class ObjectCatOJ implements Serializable {

	private Long id;
	private String code;
	private String name;
	private Long type;
	private String value;
	private String note;
	private long qyt;
	public ObjectCatOJ(Long id, String code, String name, Long type,
			String value, long qyt) {
		super();
		this.id = id;
		this.qyt = qyt;
		this.code = code;
		this.name = name;
		this.type = type;
		this.value = value;
	
	}
	

	public long getQyt() {
		return qyt;
	}


	public void setQyt(long qyt) {
		this.qyt = qyt;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

}

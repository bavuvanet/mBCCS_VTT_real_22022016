package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "dataComboboxBean",strict=false)
public class DataComboboxBean implements Serializable, Cloneable{
	@Element(name = "code", required = false)
	  private String code;
	@Element(name = "name", required = false)
	    private String name;
	
	private boolean checked = false;
	
	
	
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
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
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
	
}

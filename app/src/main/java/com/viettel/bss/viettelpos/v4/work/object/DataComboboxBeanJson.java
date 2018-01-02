package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class DataComboboxBeanJson implements Serializable{
	@SerializedName("code")
	  public String code;
	@SerializedName("name")
	    public String name;
	
	private boolean checked = false;
	
	private boolean isCheckApprove = false;
	
	
	
	
	
	
	
	public boolean isCheckApprove() {
		return isCheckApprove;
	}
	public void setCheckApprove(boolean isCheckApprove) {
		this.isCheckApprove = isCheckApprove;
	}
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

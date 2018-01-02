package com.viettel.bss.viettelpos.v4.channel.object;

import java.util.ArrayList;

public class ObjectTypeBO {
	private String broadTypeName;
	private String broadTypeValue;
	private ArrayList<ObjectCatBO> arrObjectCatBOs = new ArrayList<>();
	public String getBroadTypeName() {
		return broadTypeName;
	}
	public void setBroadTypeName(String broadTypeName) {
		this.broadTypeName = broadTypeName;
	}
	public String getBroadTypeValue() {
		return broadTypeValue;
	}
	public void setBroadTypeValue(String broadTypeValue) {
		this.broadTypeValue = broadTypeValue;
	}
	public ArrayList<ObjectCatBO> getArrObjectCatBOs() {
		return arrObjectCatBOs;
	}
	public void setArrObjectCatBOs(ArrayList<ObjectCatBO> arrObjectCatBOs) {
		this.arrObjectCatBOs = arrObjectCatBOs;
	}
	
}

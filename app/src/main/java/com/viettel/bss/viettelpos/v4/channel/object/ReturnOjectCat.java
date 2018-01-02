package com.viettel.bss.viettelpos.v4.channel.object;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class ReturnOjectCat {
	@ElementList(inline = true)
	private ArrayList<AssetDetailHistoryOJ> arrObject;
	@Element
	private String errorCode;
	@Element
	private String token;
	public ArrayList<AssetDetailHistoryOJ> getArrObject() {
		return arrObject;
	}
	public void setArrObject(ArrayList<AssetDetailHistoryOJ> arrObject) {
		this.arrObject = arrObject;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
}

package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
@Root(name = "VasResponseBO",strict=false)
public class VasResponseBO {
	@ElementList(name = "lstVasUsed", entry = "lstVasUsed", required = false, inline = true)
	private ArrayList<SubRelProductBO> lstVasUsed;
	@ElementList(name = "lstVasChange", entry = "lstVasChange", required = false, inline = true)
	private ArrayList<SubRelProductBO> lstVasChange;
	
	public ArrayList<SubRelProductBO> getLstVasUsed() {
		return lstVasUsed;
	}
	public void setLstVasUsed(ArrayList<SubRelProductBO> lstVasUsed) {
		this.lstVasUsed = lstVasUsed;
	}
	public ArrayList<SubRelProductBO> getLstVasChange() {
		return lstVasChange;
	}
	public void setLstVasChange(ArrayList<SubRelProductBO> lstVasChange) {
		this.lstVasChange = lstVasChange;
	}
}

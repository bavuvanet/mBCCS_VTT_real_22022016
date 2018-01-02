package com.viettel.bss.viettelpos.v3.connecttionService.model;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "MapSupplyMethodSupplyProgram", strict = false)
public class MapSupplyMethodSupplyProgram implements Serializable{
	
	@ElementList(name = "lstSupplyPrograms", entry = "lstSupplyPrograms", required = false, inline = true)
	private List<SupplyProgram> lstSupplyPrograms;
	@Element(name = "supplyMethod", required = false)
	private String supplyMethod;

	public List<SupplyProgram> getLstSupplyPrograms() {
		return lstSupplyPrograms;
	}

	public void setLstSupplyPrograms(List<SupplyProgram> lstSupplyPrograms) {
		this.lstSupplyPrograms = lstSupplyPrograms;
	}

	public String getSupplyMethod() {
		return supplyMethod;
	}

	public void setSupplyMethod(String supplyMethod) {
		this.supplyMethod = supplyMethod;
	}

}

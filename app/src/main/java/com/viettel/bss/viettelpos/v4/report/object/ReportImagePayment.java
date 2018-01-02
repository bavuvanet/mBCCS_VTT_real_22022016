package com.viettel.bss.viettelpos.v4.report.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("serial")
public class ReportImagePayment implements Serializable {
	private String noDb;
	private String noGdx;
	private String noNvdb;
	private String numDb;
	private String numGdx;
	private String numNvdb;
	private String staffCode;
	private String staffId;
	private String objectType;  
	
	private HashMap<String, ArrayList<ResultEquipments>> mHasmapResultEquipment = new HashMap<>();
	
	
	
	
	
	
	public HashMap<String, ArrayList<ResultEquipments>> getmHasmapResultEquipment() {
		return mHasmapResultEquipment;
	}
	public void setmHasmapResultEquipment(HashMap<String, ArrayList<ResultEquipments>> mHasmapResultEquipment) {
		this.mHasmapResultEquipment = mHasmapResultEquipment;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getNoDb() {
		return noDb;
	}
	public void setNoDb(String noDb) {
		this.noDb = noDb;
	}
	public String getNoGdx() {
		return noGdx;
	}
	public void setNoGdx(String noGdx) {
		this.noGdx = noGdx;
	}
	public String getNoNvdb() {
		return noNvdb;
	}
	public void setNoNvdb(String noNvdb) {
		this.noNvdb = noNvdb;
	}
	public String getNumDb() {
		return numDb;
	}
	public void setNumDb(String numDb) {
		this.numDb = numDb;
	}
	public String getNumGdx() {
		return numGdx;
	}
	public void setNumGdx(String numGdx) {
		this.numGdx = numGdx;
	}
	public String getNumNvdb() {
		return numNvdb;
	}
	public void setNumNvdb(String numNvdb) {
		this.numNvdb = numNvdb;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	} 
	
}

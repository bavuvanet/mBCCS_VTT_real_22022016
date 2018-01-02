package com.viettel.bss.viettelpos.v4.syn.object;

import java.util.ArrayList;

public class SynchronizeDataObject {
	private String tableName;
	private ArrayList<String> lstData;
	private String oraRowSCN;
	private String query;
	public String getTableName() {
		if (tableName == null) {
			return "";
		}
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public ArrayList<String> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<String> lstData) {
		this.lstData = lstData;
	}

	public String getOraRowSCN() {
		if (oraRowSCN == null) {
			return "";
		}
		return oraRowSCN;
	}

	public void setOraRowSCN(String oraRowSCN) {
		this.oraRowSCN = oraRowSCN;
	}

	public void addData(String data) {
		if (lstData == null) {
			lstData = new ArrayList<>();
		}
		lstData.add(data);
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
}

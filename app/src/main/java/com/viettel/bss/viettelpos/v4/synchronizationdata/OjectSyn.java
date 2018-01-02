package com.viettel.bss.viettelpos.v4.synchronizationdata;

public class OjectSyn {
	private String table_name;
	private String max_ora_rowscn;
	public OjectSyn(String table_name, String max_ora_rowscn) {
		super();
		this.table_name = table_name;
		this.max_ora_rowscn = max_ora_rowscn;
	}
	
	public OjectSyn() {
		super();
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getMax_ora_rowscn() {
		return max_ora_rowscn;
	}

	public void setMax_ora_rowscn(String max_ora_rowscn) {
		this.max_ora_rowscn = max_ora_rowscn;
	}

}

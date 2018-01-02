package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.util.ArrayList;
class SynchoronizationObject {
	private  String table_name;
	private String max_ora_rowscn;
	private ArrayList<String> _getListDta;

	public ArrayList<String> get_getListDta() {
		return _getListDta;
	}
	public void set_getListDta(ArrayList<String> _getListDta) {
		this._getListDta = _getListDta;
	}
	public String getMax_ora_rowscn() {
		return max_ora_rowscn;
	}
	public void setMax_ora_rowscn(String max_ora_rowscn) {
		this.max_ora_rowscn = max_ora_rowscn;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

}

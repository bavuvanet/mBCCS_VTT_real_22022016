package com.viettel.bss.viettelpos.v4.synchronizationdata;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;

class SynchoronizationManager {
	private Context context;
	private GetMaxOraRowScnDal dal = null;

	public SynchoronizationManager(Context context) {
		this.context = context;
	}

	// get list object from table max_ora_rowscn
	public ArrayList<OjectSyn> OnGetListMaxOraRow() {
		ArrayList<OjectSyn> lisArrayList = new ArrayList<>();
		try{
			
			dal = new GetMaxOraRowScnDal(context);
			dal.open();
			lisArrayList = dal.getlistSyn("max_ora_rowscn", "0");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(dal!=null){
				dal.close();
			}
		}
		return lisArrayList;
	}
	
	// get list object from table max_ora_rowscn all table
	public ArrayList<OjectSyn> OnGetListMaxOraRowAllTable() {
		ArrayList<OjectSyn> lisArrayList = new ArrayList<>();
		try{
			
			dal = new GetMaxOraRowScnDal(context);
			dal.open();
			lisArrayList = dal.getAllListsyn();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(dal!=null){
				dal.close();
			}
		}
		return lisArrayList;
	}

	// run synchronizationdata
	public String RunSynChroniZationData(Context context2) {
		this.context = context2;
        ArrayList<OjectSyn> arraSynchoronizationObjects = OnGetListMaxOraRow();
		// new SynchronizationData(context, arraSynchoronizationObjects);
		return Constant.SUCCESS_CODE;
	}

	// create file xml for Synchronization
	public String createXMLSyn(String tableName, String max_Row) {
		StringBuilder stringBuilder = new StringBuilder(
				"<ws:syncChangeRecordDatabase>");
		stringBuilder.append("<syncInput>");
		stringBuilder.append("<token>").append(Session.getToken()).append("</token>");
		stringBuilder.append("<lstSyncBase>");
		stringBuilder.append("<oraRowscn>").append(max_Row).append("</oraRowscn>");
		stringBuilder.append("<table>").append(tableName).append("</table>");
		stringBuilder.append("</lstSyncBase>");
		stringBuilder.append("</syncInput>");
		stringBuilder.append("</ws:syncChangeRecordDatabase>");
		Log.d("createfilexmlSyn", stringBuilder.toString());
		return stringBuilder.toString();
	}

}

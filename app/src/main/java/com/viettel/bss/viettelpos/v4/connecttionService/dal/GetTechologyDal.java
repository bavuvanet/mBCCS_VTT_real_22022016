package com.viettel.bss.viettelpos.v4.connecttionService.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TechologyBeans;

public class GetTechologyDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public GetTechologyDal(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close() {
		try{
			dal.close();
			database.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
//	select * from ap_param where param_type like '%LIST_TECHNOLOGY%' and param_code='I' and param_value='3';
	
	public String getNameTechology(String paramCode,String paramValue){
		String nameTech = "";
        Cursor cursor = null;
        cursor = database.rawQuery("select description  from ap_param where par_name like 'LIST_TECHNOLOGY' and par_type=? and par_value = ?;", new String[]{paramCode,paramValue});
		if(cursor == null){
			return nameTech;
		}
		if(cursor.moveToFirst()){
			do {
				nameTech = cursor.getString(0);
			} while (cursor.moveToNext());
		}
		return nameTech;
		
	}
	public ArrayList<TechologyBeans> getListTechology(String serviceType) {
		Log.d("serviceType", serviceType);
		ArrayList<TechologyBeans> arrTechologyBeans = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("SELECT * FROM ap_param WHERE par_name = 'LIST_TECHNOLOGY' AND par_type = ? AND status = '1' order by description");
		
		cursor = database.rawQuery(sql.toString(), new String[]{serviceType});
		Log.d("sql", sql.toString());
		if(cursor == null){
			return arrTechologyBeans;
		}
		if(cursor.moveToFirst()){
			do {
				TechologyBeans techologyBeans = new TechologyBeans();
				String idTech = cursor.getString(0);
				Log.d("idTech", idTech);
				techologyBeans.setIdTech(idTech);
				String parName = cursor.getString(1);
				Log.d("parName", parName);
				techologyBeans.setParName(parName);
				String parType = cursor.getString(2);
				Log.d("parType", parType);
				techologyBeans.setParType(parType);
				String parValue = cursor.getString(3);
				Log.d("parValue", parValue);
				techologyBeans.setParValue(parValue);
				String description = cursor.getString(4);
				Log.d("description", description);
				techologyBeans.setDescription(description);
				String status = cursor.getString(5);
				techologyBeans.setStatus(status);
				arrTechologyBeans.add(techologyBeans);
			} while (cursor.moveToNext());
		}
		return arrTechologyBeans;
	}
//	SELECT description FROM ap_param WHERE par_name = 'LIST_TECHNOLOGY' AND par_type = 'F' and par_value = 4 AND status = '1' order by description
	public String getNameTechologyCD(String technology) {
		
		String des = "";
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("SELECT DISTINCT(description) FROM ap_param WHERE par_name = 'LIST_TECHNOLOGY' AND  par_value = ? AND status = '1' order by description");
		
		cursor = database.rawQuery(sql.toString(), new String[]{technology});
		Log.d("sql", sql.toString());
		if(cursor == null){
			return des;
		}
		if(cursor.moveToFirst()){
			do {

				des= cursor.getString(0);
				Log.d("des", des);
				
			} while (cursor.moveToNext());
		}
		return des;
	}

}

package com.viettel.bss.viettelpos.v4.connecttionService.dal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceBeans;

public class GetServiceDal {
	private SQLiteDatabase database;
	private DBOpenHelper dal;
	public static Map<String, TelecomServiceBeans> mapTelecomServiceCM = null;
	public GetServiceDal(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close()  {
		try{
			dal.close();
			database.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getDescription(String parType) throws Exception{
		String description = "";
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select name from telecom_service  WHERE service_alias = ? AND status = '1' ");
		//sql.append("select tel_service_name from telecom_service where status = '1' and code = ? ");
		 cursor = database.rawQuery(sql.toString(), new String[]{parType});
			if(cursor == null){
				return description;
			}
			if(cursor.moveToFirst()){
				do {
					String des = cursor.getString(0);
					Log.d("description", description);
					description = des;
					
				} while (cursor.moveToNext());
				
			}
		
		return description;
	}
	public ArrayList<TelecomServiceBeans> getlisTelecomServiceBeans() throws Exception{
		ArrayList<TelecomServiceBeans> lisTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
		
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select * from telecom_service where status = '1'");
	//	sql.append("select * from telecom_service where status = '1' and code is not null and code <> '' order by tel_service_name");
		 cursor = database.rawQuery(sql.toString(), null);
		if(cursor == null){
			return lisTelecomServiceBeans;
		}
		if(cursor.moveToFirst()){
			do {
				
				getdataCM(lisTelecomServiceBeans, cursor);
			//	getdataIM(lisTelecomServiceBeans, cursor);
			} while (cursor.moveToNext());
			
			
		}
		return lisTelecomServiceBeans;
	}
	public void getdataCM(ArrayList<TelecomServiceBeans> listServiceBeans,Cursor cursor){
		TelecomServiceBeans teServiceBeans = new TelecomServiceBeans();
		String telecomServiceId = cursor.getString(cursor.getColumnIndex("telecom_service_id"));
		Log.d("telecomServiceId", telecomServiceId);
		teServiceBeans.setTelecomServiceId(telecomServiceId);
		String servicetype = cursor.getString(cursor.getColumnIndex("service_alias"));
		Log.d("servicetype", servicetype);
		teServiceBeans.setServiceTypeId(servicetype);
		
		String serviceAlias = cursor.getString(cursor.getColumnIndex("service_alias"));
		teServiceBeans.setServiceAlias(serviceAlias);
		
		String tele_service_name = cursor.getString(cursor.getColumnIndex("name"));
		Log.d("tele_service_name", tele_service_name);
		teServiceBeans.setTele_service_name(tele_service_name);
		
//		String permisstion = cursor.getString(4);
//		teServiceBeans.setPermission(permisstion);
		
		
		String status = cursor.getString(cursor.getColumnIndex("status"));
		teServiceBeans.setStatus(status);
		String serviceId = cursor.getString(cursor.getColumnIndex("telecom_service_id"));
		teServiceBeans.setServiceId(serviceId);
		
//		String type = cursor.getString(7);
//		teServiceBeans.setType(type);
//		String hostService = cursor.getString(8);
//		teServiceBeans.setHostService(hostService);
//		String tsId = cursor.getString(9);
//		teServiceBeans.setTsId(tsId);
		listServiceBeans.add(teServiceBeans);
	}
	public void getdataIM(ArrayList<TelecomServiceBeans> listServiceBeans,Cursor cursor){
		TelecomServiceBeans teServiceBeans = new TelecomServiceBeans();
		String servicetype = cursor.getString(6);
		Log.d("servicetype", servicetype);
		teServiceBeans.setServiceAlias(servicetype);
		
		
		String tele_service_name = cursor.getString(1);
		Log.d("tele_service_name", tele_service_name);
		teServiceBeans.setTele_service_name(tele_service_name);
		listServiceBeans.add(teServiceBeans);
		
	}
	
	// TODO Get service from CM Mobile,homephone 
	//select * from telecom_service where status = '1' and telecom_service_id in (1,2)
	public ArrayList<TelecomServiceBeans> getlisServiceMobile() throws Exception{
		ArrayList<TelecomServiceBeans> lisTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
		
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select * from telecom_service where status = '1' and telecom_service_id in (1,2)");
		 cursor = database.rawQuery(sql.toString(), null);
		if(cursor == null){
			return lisTelecomServiceBeans;
		}
		if(cursor.moveToFirst()){
			do {
				getdataCM(lisTelecomServiceBeans, cursor);
			} while (cursor.moveToNext());
			
			
		}
		return lisTelecomServiceBeans;
	}
	
	public ArrayList<TelecomServiceBeans> getlisServiceMobileSM() throws Exception{
		ArrayList<TelecomServiceBeans> lisTelecomServiceBeans = new ArrayList<TelecomServiceBeans>();
		
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select * from telecom_service where status = '1' and telecom_service_id  = 1");
		 cursor = database.rawQuery(sql.toString(), null);
		if(cursor == null){
			return lisTelecomServiceBeans;
		}
		if(cursor.moveToFirst()){
			do {
				getdataCM(lisTelecomServiceBeans, cursor);
			} while (cursor.moveToNext());
			
			
		}
		return lisTelecomServiceBeans;
	}
	public String getTelecomserviceId(String parType) throws Exception{
		String telecomserviceId = "";
		StringBuilder sql = new StringBuilder();
		Cursor cursor = null;
		sql.append("select telecom_service_id from telecom_service  WHERE service_alias = ? AND status = '1' ");
		//sql.append("select tel_service_name from telecom_service where status = '1' and code = ? ");
		 cursor = database.rawQuery(sql.toString(), new String[]{parType});
			if(cursor == null){
				return telecomserviceId;
			}
			if(cursor.moveToFirst()){
				do {
					String des = cursor.getString(0);
					Log.d("telecomserviceid", des);
					telecomserviceId = des;
					
				} while (cursor.moveToNext());
				
			}
		
		return telecomserviceId;
	}
	public TelecomServiceBeans getTelecomServiceCMById(String telecomServiceId)throws Exception {
		if (mapTelecomServiceCM != null) {
			return mapTelecomServiceCM.get(telecomServiceId);
		}
		mapTelecomServiceCM = new HashMap<String, TelecomServiceBeans>();
		try {
			open();

			String sql = "select telecom_service_id, name from telecom_service where status = 1";
			Cursor cursor = null;
			cursor = database.rawQuery(sql, new String[] {});
			if (cursor == null) {
				mapTelecomServiceCM = null;
				return null;

			}
			if (cursor.moveToFirst()) {
				do {
					TelecomServiceBeans item = new TelecomServiceBeans();
					item.setTelecomServiceId(cursor.getString(0));
					item.setTele_service_name(cursor.getString(1));
					mapTelecomServiceCM.put(item.getTelecomServiceId(), item);
				} while (cursor.moveToNext());

			}

		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
			throw e;
		} finally {
			close();
		}
		return mapTelecomServiceCM.get(telecomServiceId);
	}
}

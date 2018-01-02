package com.viettel.bss.viettelpos.v4.connecttionService.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ExamineJoinBeans;

public class GetExamineJoinDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public GetExamineJoinDal(Context context) {
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
	
	public ArrayList<ExamineJoinBeans> getListExamine(String serviceType) {
		ArrayList<ExamineJoinBeans> lisExamineJoinBeans = new ArrayList<>();
        Cursor cursor = null;
        cursor = database.rawQuery("SELECT * FROM ap_param WHERE par_name = 'LINE_TYPE_CD' AND DESCRIPTION = ? ORDER BY id DESC", new String[]{serviceType});
		if(cursor == null){
			return lisExamineJoinBeans;
		}
		if(cursor.moveToFirst()){
			do {
				ExamineJoinBeans exaJoinBeans = new ExamineJoinBeans();
				String parName = cursor.getString(1);
				Log.d("parName", parName);
				exaJoinBeans.setParName(parName);
				
				String parType = cursor.getString(2);
				Log.d("parType", parType);
				exaJoinBeans.setParType(parType);
				
				String parValue = cursor.getString(3);
				Log.d("parValue", parValue);
				exaJoinBeans.setParValue(parValue);
				
				String description = cursor.getString(4);
				Log.d("description", description);
				exaJoinBeans.setDescription(description);
				
				String status = cursor.getString(5);
				Log.d("status", status);
				exaJoinBeans.setStatus(status);
				
				lisExamineJoinBeans.add(exaJoinBeans);
				
				
			} while (cursor.moveToNext());
		}
		
		
		
		return lisExamineJoinBeans;
		
	}
}

package com.viettel.bss.viettelpos.v4.charge.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.charge.object.CycleObj;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;

public class CycleDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	
	public CycleDal(Context context){
		dal = new DBOpenHelper(context);	
	}
	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}
	public void close() {
		dal.close();
		if (database != null) {
			database.close();
		}
	}
	
	public 	ArrayList<CycleObj> getAllCycle() {
		ArrayList<CycleObj> lisCycleObjs = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select cast(bill_cycle_from as integer), curr_applied_cycle ");
		sql.append(" from curr_bill_cycle order by bill_cycle_from");
		Log.d("sqllCycle", sql.toString());
		Cursor cursor = database.rawQuery(sql.toString(), null);
		if(cursor.moveToFirst()){
			do{
				CycleObj cycleObj = new CycleObj();
				int billCycleFrom = cursor.getInt(0);
				Log.d("billCycleFrom",""+ billCycleFrom);
				cycleObj.setBillCycleFrom(""+billCycleFrom);
				String appliedCycle = cursor.getString(1);
				Log.d("appliedCycle", appliedCycle);
				cycleObj.setAppliedCyle(appliedCycle);
				
				lisCycleObjs.add(cycleObj);	
			}while(cursor.moveToNext());
		}
		return lisCycleObjs;
	}
	
}

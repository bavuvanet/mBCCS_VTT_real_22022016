package com.viettel.bss.viettelpos.v4.connecttionService.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TypePaperBeans;

public class GetTypePaperDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public GetTypePaperDal(Context context) {
		dal = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = dal.getWritableDatabase();
	}

	public void close(){
		try{
			dal.close();
			database.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public ArrayList<TypePaperBeans> getLisTypepaper() {
		
		ArrayList<TypePaperBeans> lisPaperBeans = new ArrayList<>();
        Cursor cursor = null;


        cursor = database.rawQuery("SELECT id,par_name,par_type,par_value,description,status FROM ap_param WHERE par_name = '301' and status = '1' order by description", null);
		
		if(cursor == null){
			return lisPaperBeans;
		}
		if(cursor.moveToFirst()){
			do {
				TypePaperBeans typePaperBeans = new TypePaperBeans();
				String idTypePaper = cursor.getString(0);
				Log.d("idTypePaper", idTypePaper);
				typePaperBeans.setIdTypePaper(idTypePaper);
				String parName = cursor.getString(1);
				Log.d("parName", parName);
				typePaperBeans.setParNamePaper(parName);
				String parType = cursor.getString(2);
				Log.d("parType", parType);
				typePaperBeans.setParType(parType);
				String parValue = cursor.getString(3);
				Log.d("parValue", parValue);
				typePaperBeans.setParValues(parValue);
				String description = cursor.getString(4);
				typePaperBeans.setDescription(description);
				String status = cursor.getString(5);
				Log.d("status", status);
				typePaperBeans.setStatus(status);
				lisPaperBeans.add(typePaperBeans);
			} while (cursor.moveToNext());
			
		}
		return lisPaperBeans;
	}
	
	
	

}

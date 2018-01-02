package com.viettel.bss.viettelpos.v4.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.object.LocalConfig;

public class LocalConfigDAL {

	private SQLiteDatabase database;
	private final DatabaseConfigHelper databaseConfig;

	public LocalConfigDAL(Context context) {
		databaseConfig = new DatabaseConfigHelper(context);
	}

	public void open() throws SQLException {
		database = databaseConfig.getWritableDatabase();
	}

	public void close() {
		databaseConfig.close();
		if (database != null) {
			database.close();
		}
	}

	public void deleteByName(String name) {
		database.execSQL("delete from local_config where param_name = ?",new String[]{name});
	}

	public int insertLocalConfig(LocalConfig config) {
		ContentValues values = new ContentValues();
		values.put("param_name", config.getParamName());
		values.put("param_value", config.getParamValue());
		values.put("create_time", config.getCreateDate());
		Long insertResult =  database.insert("local_config", null, values);
		if(insertResult.compareTo(-1L) == 0){
			return 0;
		}else{
			return 1;
		}
	}

//	public LocalConfig getConfigByName(String paramName) throws Exception {
//		String[] param = new String[] { paramName };
//		Cursor cursor = database
//				.rawQuery(
//						"Select param_name, param_value, create_time from local_config where param_name = ? ",
//						param);
//		if (cursor.moveToFirst()) {
//			LocalConfig result = new LocalConfig();
//			result.setParamName(cursor.getString(0));
//			result.setParamValue(cursor.getString(1));
//			result.setCreateDate(cursor.getString(2));
//			cursor.close();
//			return result;
//
//		}
//		return null;
//	}
}

package com.viettel.bss.viettelpos.v4.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.object.VSA;

public class VSADAL {

	private SQLiteDatabase database;
	private final DatabaseConfigHelper loginDatabase;
	
	public VSADAL(Context context) {
		loginDatabase = new DatabaseConfigHelper(context);
	}

	public void open() throws SQLException {
		database = loginDatabase.getWritableDatabase();
	}

	public void close() {
		loginDatabase.close();
		if (database != null) {
			database.close();
		}
	}


	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	public int insertVSAItem(VSA vsaMenu) {
		Log.e("insertMenu",vsaMenu.getObjectName());
		ContentValues values = new ContentValues();
		values.put("object_id", vsaMenu.getObjectId());
		values.put("object_name", vsaMenu.getObjectName());
		values.put("parent_id", vsaMenu.getParentId());
		values.put("status", vsaMenu.getStatus());
		Long result = database.insert("VSA", null, values);
		if(result.compareTo(-1L) ==0){
			return 0;
		}else{
			return 1;
		}
	}

	public List<String> getAllVSAName() {
		List<String> result = new ArrayList<>();
		Cursor cursor = database.rawQuery(
				"Select object_name from VSA where status = '1' ", null);
		if (cursor.moveToFirst()) {
			do {
				String item = cursor.getString(0);
				result.add(item);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return result;
	}
}

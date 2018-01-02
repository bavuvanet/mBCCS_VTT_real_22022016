package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.channel.object.ObjectCatOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.OBJ_CAT;

public class OjectCatDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;
	private final String TABLE_NAME = "obj_cat";
	private String errorMessage;
    private String TAG = "TAG";
	public OjectCatDAL(Context context) {
        mDAL = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = mDAL.getWritableDatabase();
	}

	public void close() {
		mDAL.close();
		if (database != null) {
			database.close();
		}
	}

	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	public ObjectCatOJ getObjectCatById(String typecode, String valuecode) {
		ObjectCatOJ mObject = null;
		String[] arrayCol = new String[] { OBJ_CAT.KEY_OBJ_CATE_ID,
				OBJ_CAT.KEY_OBJ_CATE_CODE, OBJ_CAT.KEY_OBJ_NAME,
				OBJ_CAT.KEY_OBJ_NOTE, OBJ_CAT.KEY_OBJ_TYPE,
				OBJ_CAT.KEY_OBJ_VALUE };
		String cond = typecode + " = ?";
		String[] arg = new String[] { valuecode };
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		mCursor = db.query(TABLE_NAME, arrayCol, cond, arg, null, null, null);
		if (mCursor.moveToFirst()) {
			Long id = mCursor.getLong(0);
			String code = mCursor.getString(1);
			String name = mCursor.getString(2);
			String note = mCursor.getString(3);
			Long type = mCursor.getLong(4);
			String value = mCursor.getString(5);

			//mObject = new ObjectCatOJ(id, code, name, type, value, note, 0);

		}
		return mObject;
	}
	
	public ArrayList<ObjectCatOJ> getListObjectCat() {
		int count = 0;
		ArrayList<ObjectCatOJ> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		String[] arrayCol = new String[] { OBJ_CAT.KEY_OBJ_CATE_ID,
				OBJ_CAT.KEY_OBJ_CATE_CODE, OBJ_CAT.KEY_OBJ_NAME,
				 OBJ_CAT.KEY_OBJ_TYPE,
				OBJ_CAT.KEY_OBJ_VALUE };
		mCursor = db.query(TABLE_NAME, arrayCol, "status = 1", null, "code", null, null);
		Log.i("TAG2", "COUNT = " + mCursor.getCount());
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				
				do{
					Long id = mCursor.getLong(0);
					String code = mCursor.getString(1);
					String name = mCursor.getString(2);
					
					Long type = mCursor.getLong(3);
					String value = mCursor.getString(4);
					ObjectCatOJ mObject = new ObjectCatOJ(id, code, name, type,
							value,  0);
					mArrayList.add(mObject);
					
					}while(mCursor.moveToNext());
			}
		}
		Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		
		return mArrayList;

	}

}

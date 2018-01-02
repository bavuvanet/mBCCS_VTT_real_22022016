package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.sale.object.TelecomServiceObject;

public class TelecomServiceDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public TelecomServiceDAL(Context context) {
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

	public ArrayList<TelecomServiceObject> getAllTelecomService() {
		ArrayList<TelecomServiceObject> result = new ArrayList<>();
		Cursor cursor = null;
		try {
			cursor = database
					.rawQuery(
							" select  telecom_service_id, name, service_alias from telecom_service where status = 1 order by telecom_service_id ",
							null);
			cursor.moveToFirst();
			do {
				TelecomServiceObject item = new TelecomServiceObject();
				item.setTelecomServiceId(cursor.getLong(0));
				item.setName(cursor.getString(1));
				if (!cursor.isNull(2)) {
					item.setCode(cursor.getString(2));
				}
				result.add(item);
			} while (cursor.moveToNext());
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return result;
	}
}

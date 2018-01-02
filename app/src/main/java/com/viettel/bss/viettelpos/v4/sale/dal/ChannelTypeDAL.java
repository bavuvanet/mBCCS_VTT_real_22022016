package com.viettel.bss.viettelpos.v4.sale.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;

import java.util.ArrayList;
import java.util.List;

public class ChannelTypeDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper channelType;

	public ChannelTypeDAL(Context context) {
		channelType = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = channelType.getWritableDatabase();
	}

	public void close() {
		database.close();
		if (database != null) {
			database.close();
		}
	}

	public List<ChannelTypeObject> getAllChannel() {
		List<ChannelTypeObject> result = new ArrayList<>();
		Cursor cursor = null;
		StringBuilder sql = new StringBuilder("");
		sql.append(" SELECT   channel_type_id, name");
		sql.append(" FROM   channel_type");
		sql.append(" WHERE   status = 1");
		sql.append(" AND channel_type_id IN");
		sql.append(" (SELECT   DISTINCT");
		sql.append(" CASE channel_type_id");
		sql.append(" WHEN 10");
		sql.append(" THEN");
		sql.append(" CASE point_of_sale");
		sql.append(" WHEN 1 THEN 80043");
		sql.append(" ELSE 10");
		sql.append(" END");
		sql.append(" ELSE");
		sql.append(" channel_type_id");
		sql.append(" END");
		sql.append(" AS channel_type_id");
		sql.append(" FROM   staff");
		sql.append(" WHERE   status = 1 and  UPPER (staff_code) <> ?)");
		sql.append(" ORDER BY   name");
		try {

			cursor = database.rawQuery(sql.toString(),
					new String[] { Session.userName.toUpperCase() });
			cursor.moveToFirst();
			do {
				ChannelTypeObject item = new ChannelTypeObject();
				item.setChannelTypeId(cursor.getLong(0));
				item.setName(cursor.getString(1));
				result.add(item);
			} while (cursor.moveToNext());
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return result;
	}

}

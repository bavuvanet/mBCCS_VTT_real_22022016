package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelTypeOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;

public class ChannelTypeDAL {

	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;
	private final String TABLE_NAME = "channel_type";
	private final Context mContext;

	public ChannelTypeDAL(Context context) {
		this.mContext = context;
		mDAL = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = mDAL.getWritableDatabase();
	}

	public void close() {
		try {
			mDAL.close();
			if (database != null) {
				database.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	public ArrayList<ChannelTypeOJ> getTasksToArray() {

		ArrayList<ChannelTypeOJ> mArrayList = new ArrayList<>();

		SQLiteDatabase db = mDAL.getReadableDatabase();
        String sql = "" + " SELECT   channel_type_id, name" +
                " FROM   channel_type" +
                " WHERE   status = 1" +
                " AND channel_type_id IN" +
                " (SELECT   DISTINCT" +
                " CASE channel_type_id" +
                " WHEN 10" +
                " THEN" +
                " CASE point_of_sale" +
                " WHEN 1 THEN 80043" +
                " ELSE 10" +
                " END" +
                " ELSE" +
                " channel_type_id" +
                " END" +
                " AS channel_type_id" +
                " FROM   staff" +
                " WHERE  status = 1 and  UPPER (staff_code) <> ?)" +
                " ORDER BY   name";

//		Cursor cursor = db
//				.rawQuery(
//						"select channel_type_id, " +
//						"name from channel_type where cast " +
//						"(channel_type_id as integer) in " +
//						"(select cast(channel_type_id as integer) " +
//						"from staff where cast (status as integer) = cast(1 as integer)) ",
//						null);
		
		Cursor cursor = db
				.rawQuery(
                        sql,
						new String[]{Session.userName});
		ChannelTypeOJ ctypeTitle = new ChannelTypeOJ(-1L, mContext
				.getResources().getString(R.string.defaultSelectByType));
		ChannelTypeOJ ctypeDefault = new ChannelTypeOJ(0L, mContext
				.getResources().getString(R.string.spinner_show));
		mArrayList.add(ctypeTitle);
		mArrayList.add(ctypeDefault);
		if (cursor != null) {
			cursor.moveToFirst();
			do {
				Long channelTypeId = cursor.getLong(0);
				String channelTypeName = cursor.getString(1);
				ChannelTypeOJ mChannelType = new ChannelTypeOJ(channelTypeId,
						channelTypeName);
				mArrayList.add(mChannelType);

			} while (cursor.moveToNext());
			cursor.close();
		}

		Log.e(tag, cursor.getCount() + "");
		return mArrayList;

	}
	
	public ArrayList<ChannelTypeOJ> getTasksToArray(boolean titleSelect) {

		ArrayList<ChannelTypeOJ> mArrayList = new ArrayList<>();

		SQLiteDatabase db = mDAL.getReadableDatabase();
        String sql = "" + " SELECT   channel_type_id, name" +
                " FROM   channel_type" +
                " WHERE   status = 1" +
                " AND channel_type_id IN" +
                " (SELECT   DISTINCT" +
                " CASE channel_type_id" +
                " WHEN 10" +
                " THEN" +
                " CASE point_of_sale" +
                " WHEN 1 THEN 80043" +
                " ELSE 10" +
                " END" +
                " ELSE" +
                " channel_type_id" +
                " END" +
                " AS channel_type_id" +
                " FROM   staff" +
                " WHERE  status = 1 and  UPPER (staff_code) <> ?)" +
                " ORDER BY   name";

//		Cursor cursor = db
//				.rawQuery(
//						"select channel_type_id, " +
//						"name from channel_type where cast " +
//						"(channel_type_id as integer) in " +
//						"(select cast(channel_type_id as integer) " +
//						"from staff where cast (status as integer) = cast(1 as integer)) ",
//						null);
		
		Cursor cursor = db
				.rawQuery(
                        sql,
						new String[]{Session.userName});
		if(titleSelect){
			ChannelTypeOJ ctypeTitle = new ChannelTypeOJ(-1L, mContext
					.getResources().getString(R.string.defaultSelectByType));
			mArrayList.add(ctypeTitle);
		}
		
		ChannelTypeOJ ctypeDefault = new ChannelTypeOJ(0L, mContext
				.getResources().getString(R.string.spinner_show));		
		mArrayList.add(ctypeDefault);
		
		if (cursor != null) {
			cursor.moveToFirst();
			do {
				Long channelTypeId = cursor.getLong(0);
				String channelTypeName = cursor.getString(1);
				ChannelTypeOJ mChannelType = new ChannelTypeOJ(channelTypeId,
						channelTypeName);
				mArrayList.add(mChannelType);

			} while (cursor.moveToNext());
			cursor.close();
		}

		Log.e(tag, cursor.getCount() + "");
		return mArrayList;

	}

	private final String tag = "channel type dal";
}

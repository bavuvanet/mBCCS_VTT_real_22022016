package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.channel.object.ChannelViewHistoryOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.VISIT;

public class ChannelViewHistoryDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;

    public ChannelViewHistoryDAL(Context context) {
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

	public ArrayList<ChannelViewHistoryOJ> getHistoryCare(int numberDay,
			int first, int end, String staff_code, String objectId) {
		int count = 0;
		ArrayList<ChannelViewHistoryOJ> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		// String selection = " user_code = ? and obj_id = ? ";
		String selection = null;
		String[] whereArgs = null;
		// whereArgs = new String[]{staff_code, objectId};
        String TABLE_NAME = "visit";
        mCursor = db.query(TABLE_NAME, new String[] { VISIT.KEY_ID, VISIT.DATE,
				VISIT.KEY_CONTENT }, selection, whereArgs, null, null, null);
		Log.i("TAG2", "COUNT = " + mCursor.getCount());
		if (mCursor != null) {
			if (mCursor.moveToPosition(first)) {
				while (!mCursor.isAfterLast()) {
					String date = mCursor.getString(1).split(" ")[0];
					String content = mCursor.getString(2);
					String title = "Lịch sử chăm sóc kênh";
					ChannelViewHistoryOJ mChannelCareContent = new ChannelViewHistoryOJ(
							date, content);
					mArrayList.add(mChannelCareContent);
					mCursor.moveToNext();
				}
			}
		}
		Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}
}

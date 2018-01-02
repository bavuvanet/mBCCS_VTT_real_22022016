package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.channel.object.AssetDetailHistoryOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;

class AssetDetailHistoryDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;
	private final String TABLE_NAME = "manage_asset_detail_history";
    private ArrayList<AssetDetailHistoryOJ> arrayList;
	
	private String errorMessage = "error";
	private final String TAG = "TAG";

	public AssetDetailHistoryDAL(Context context) {
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
	
}

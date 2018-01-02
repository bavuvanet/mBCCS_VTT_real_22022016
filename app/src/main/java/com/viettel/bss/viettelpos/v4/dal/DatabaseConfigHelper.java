package com.viettel.bss.viettelpos.v4.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.viettel.bss.viettelpos.v4.commons.Constant;

class DatabaseConfigHelper extends SQLiteOpenHelper {
	private static final String TABLE_NAME = "VSA";
	private static final String COLUMN_ID = "object_id";
	private static final String COLUMN_KEY = "parent_id";
	private static final String COLUMN_VALUE = "object_name";

	private static final String DATABASE_NAME = Constant.DATABASE_CONFIG;
	private static final int DATABASE_VERSION = 1;
	//Tao bang luu noi dung VSA
	private static final String DATABASE_CREATE = "create table " + TABLE_NAME
			+ "(" + COLUMN_ID + " text primary key, " + COLUMN_KEY + " text, "
			+ COLUMN_VALUE + " text," + "status text);";

	//Tao bang luu cac tham so cau hinh cua ung dung
	private static final String CREATE_TABLE_PARAM = "create table local_config("
			+ "param_name text, " + "param_value text, " + "create_time text);";

	public DatabaseConfigHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	private void initDataTable(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
		database.execSQL(CREATE_TABLE_PARAM);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS local_config" );
		onCreate(db);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		initDataTable(database);
	}
}

package com.viettel.bss.viettelpos.v4.charge.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.charge.object.TelecomServiceObj;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;

class ChargeDB {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;
    private final String TABLE_CURR_BILL_CYCLE = "curr_bill_cycle";// curr_bill_cycle

    // table telecom service
	private static final String KEY_ID = "telecom_service_id";
	private static final String KEY_TEL_NAME = "tel_service_name";
	private static final String KEY_DATE = "create_date";

	// table area
	public static final String KEY_AREA_CODE = "area_code";
	public static final String KEY_PARENT_CODE = "parent_code";
	public static final String KEY_CEN_CODE = "cen_code";
	public static final String KEY_PROVINCE_CODE = "province";
	public static final String KEY_DISTRICT_CODE = "district";
	public static final String KEY_PERCINCT_CODE = "precinct";
	public static final String KEY_AREA_NAME = "name";
	public static final String KEY_AREA_FULL_NAME = "full_name";

	public ChargeDB(Context context) {
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

	public ArrayList<AreaObj> getListArea() {
		ArrayList<AreaObj> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();

        String TABLE_AREA = "area";
        mCursor = db.query(TABLE_AREA, new String[] { }, null, null, null, null, null);
		if (mCursor.moveToFirst()) {
			while (!mCursor.isAfterLast()) {
				String areaCode = mCursor.getString(2);
				String parentCode = mCursor.getString(3);

				String province = mCursor.getString(5);
				String distict = mCursor.getString(6);
				String precinct = mCursor.getString(7);
				String name = mCursor.getString(10);
				String fullName = mCursor.getString(11);
				// String createDate = mCursor.getString(2).split(" ")[0];
				AreaObj areaObj = new AreaObj();
				areaObj.setAreaCode(areaCode);
				areaObj.setParentCode(parentCode);
				areaObj.setProvince(province);
				areaObj.setDistrict(distict);
				areaObj.setPrecinct(precinct);
				areaObj.setName(name);
				areaObj.setFullNamel(fullName);
				mArrayList.add(areaObj);
				mCursor.moveToNext();
			}
		}
		mCursor.close();
		db.close();
		Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}

	public ArrayList<TelecomServiceObj> getListTelecomService() {
		ArrayList<TelecomServiceObj> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();

        String TABLE_TELECOM_SERVICE = "telecom_service";
        mCursor = db.query(TABLE_TELECOM_SERVICE, new String[] { KEY_ID,
				KEY_TEL_NAME, KEY_DATE }, null, null, null, null, null);
		if (mCursor.moveToFirst()) {
			while (!mCursor.isAfterLast()) {
				int content_id = mCursor.getInt(0);
				String telName = mCursor.getString(1);
				// String createDate = mCursor.getString(2).split(" ")[0];
				TelecomServiceObj mChannelCareContent = new TelecomServiceObj();
				mChannelCareContent.setTelecomServiceId(content_id);
				mChannelCareContent.setTelServiceName(telName);
				mArrayList.add(mChannelCareContent);
				mCursor.moveToNext();
			}
		}
		mCursor.close();
		db.close();
		Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}

}

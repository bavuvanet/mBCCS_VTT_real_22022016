package com.viettel.bss.viettelpos.v4.infrastrucure.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.STAFF;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.AreaObject;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ServiceObject;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.object.SalePoint;

public class DalInfrastructure {
	private SQLiteDatabase database;
	private final DBOpenHelper channelDAL;

	public DalInfrastructure(Context context) {
		channelDAL = new DBOpenHelper(context);
	}

	public void open() throws SQLException {
		database = channelDAL.getWritableDatabase();
	}

	public void close() {
		channelDAL.close();
		if (database != null) {
			database.close();
		}
	}

	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	public ArrayList<Staff> getStaffJobManager(String TABLE_NAME, int first,
			int end) {
		int count = 0;
		ArrayList<Staff> mArrayList = null;
		Cursor mCursor = null;
		// String filter = Define.STAFF.KEY_STAFF_OWNER_ID + " = ?";
		// String[] value = new String[] { staff_id };
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, new String[] { STAFF.KEY_NAME,
					STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
					STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
					STAFF.KEY_ID, STAFF.KEY_CODE }, null, null, null, null,
					null);
		if (mCursor != null) {
			if (first == 0)
				mCursor.moveToFirst();
			else
				mCursor.moveToPosition(first);
			mArrayList = new ArrayList<>();
			while (mCursor.moveToNext() && count < end) {
				String name = mCursor.getString(0);
				String address = mCursor.getString(1);
				String iduserno = mCursor.getString(2);
				String id_issue_date = mCursor.getString(3);
				String birthday = mCursor.getString(4);
				String staffid = mCursor.getString(5);
				// Staff mTask = new Staff(
				// staffid,
				// name,
				// address,
				// "http://anhdepblog.com/graphics/love/images/anhdepblog.com_love389.jpg",
				// iduserno,
				// id_issue_date,
				// birthday,
				// "",
				// 0);
				// mArrayList.add(mTask);
				count++;
			}
		}
		return mArrayList;

	}

	public ArrayList<ServiceObject> getAllServiceSpinner(String TABLE_NAME) {
		ArrayList<ServiceObject> mArrayList = null;
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		Cursor mCursor = db.query(TABLE_NAME, null, null, null, null, null,
				null);
		if (mCursor != null) {

			mArrayList = new ArrayList<>();
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				String serviceId = mCursor.getString(0);
				String serviceName = mCursor.getString(1);
				String description = mCursor.getString(2);
				Log.d("description", "description: " + description);
				String createDate = mCursor.getString(3);
				String status = mCursor.getString(4);
				String role_name = mCursor.getString(5);
				String code = mCursor.getString(6);

				ServiceObject mTask = new ServiceObject(serviceId, code,
						serviceName, description, createDate, status, role_name);

				mArrayList.add(mTask);
				mCursor.moveToNext();
			}
		}
		mCursor.close();
		return mArrayList;

	}

	public ArrayList<SalePoint> getSalePointJobManager(String TABLE_NAME,
			String staff_id, int first, int end) {
		int count = 0;
		ArrayList<SalePoint> mArrayList = null;
		Cursor mCursor = null;
		String channel_type_id1 = "80043";
		String channel_type_id2 = "10";
		String filter = STAFF.KEY_STAFF_OWNER_ID + " = ? and ( "
				+ STAFF.KEY_CHANNEL_TYPE_ID + " = ? or "
				+ STAFF.KEY_CHANNEL_TYPE_ID + " = ? )";
		String[] value = new String[] { "0", channel_type_id1, channel_type_id2 };
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, new String[] { STAFF.KEY_NAME,
					STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
					STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
					STAFF.KEY_ID, STAFF.KEY_CODE }, filter, value, null, null, null);
		if (mCursor != null) {
			mArrayList = new ArrayList<>();
			if (mCursor.moveToPosition(first))
				while (mCursor.moveToNext() && count < end) {
					String name = mCursor.getString(0);
					String address = mCursor.getString(1);
					String iduserno = mCursor.getString(2);
					String id_issue_date = mCursor.getString(3);
					String birthday = mCursor.getString(4);
					Long staffId = mCursor.getLong(5);
					String staffCode = mCursor.getString(6);
					SalePoint mSalePoint = new SalePoint(name, staffId, staffCode, address);
					mArrayList.add(mSalePoint);
					count++;
				}
		}
		return mArrayList;

	}

	public ArrayList<AreaObject> getDistrict(String TABLE_NAME, String province) {
		ArrayList<AreaObject> mArrayList = new ArrayList<>();
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		Cursor mCursor = null;
		try {
			String where = "status = 1 and district is not null and parent_code = '"
					+ province + "'";
			mCursor = db.query(TABLE_NAME, new String[] { "district", "name" },
					where, null, null, null, null);
			if (mCursor != null && mCursor.getCount() > 0) {
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					String district = mCursor.getString(0);
					String name = mCursor.getString(1);

					AreaObject mTask = new AreaObject(name, district);
					mArrayList.add(mTask);

					mCursor.moveToNext();
				}
			}
		} finally {
			try {
				if (mCursor != null) {
					mCursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mArrayList;
	}

	public ArrayList<AreaObject> getStreet(String tableName, String district) {
		ArrayList<AreaObject> mArrayList = new ArrayList<>();
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		Cursor mCursor = null;
		try {
			String where = "status = 1 and street is not null and district = '"
					+ district + "'";
			mCursor = db.query(tableName, new String[] { "street", "name" },
					where, null, null, null, null);
			if (mCursor != null && mCursor.getCount() > 0) {
				mCursor.moveToFirst();
				while (!mCursor.isAfterLast()) {
					String street = mCursor.getString(0);
					String name = mCursor.getString(1);

					AreaObject mTask = new AreaObject(name, street);
					mArrayList.add(mTask);

					mCursor.moveToNext();
				}
			}
		} finally {
			try {
				if (mCursor != null) {
					mCursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mArrayList;
	}

	public ArrayList<AreaObject> getProvince(String tableName) {
		ArrayList<AreaObject> mArrayList = new ArrayList<>();
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		Cursor mCursor = null;
		try {
			String selection = " status = 1 and district is null and province is not null";
			mCursor = db.query(true, tableName, new String[] { "province",
					"name" }, selection, null, null, null, null, null);

			if (mCursor != null && mCursor.getCount() > 0) {
				mCursor.moveToFirst();

				while (!mCursor.isAfterLast()) {
					String province = mCursor.getString(0);
					String name = mCursor.getString(1);

					AreaObject mTask = new AreaObject(name, province);

					mArrayList.add(mTask);
					mCursor.moveToNext();
				}
			}

		} finally {
			try {
				if (mCursor != null) {
					mCursor.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return mArrayList;
	}

}

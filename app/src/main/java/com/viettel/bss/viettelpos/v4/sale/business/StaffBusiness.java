package com.viettel.bss.viettelpos.v4.sale.business;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class StaffBusiness {
	private static final String logTag = StaffBusiness.class.getName();

	public static Staff getStaffByStaffCode(Context context, String staffCode) {
		DBOpenHelper dbOpenHelper = null;
		SQLiteDatabase database = null;
		try {
			dbOpenHelper = new DBOpenHelper(context);
			database = dbOpenHelper.getReadableDatabase();
			StaffDAL dal = new StaffDAL(database);

            return dal.getStaffByStaffCode(staffCode);
		} catch (Exception e) {
			Log.e("Exception", "Exception", e);
		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}
			if (database != null) {
				database.close();
			}
		}
		return null;
	}

	/**
	 * 
	 * @param context
	 * @param channelTypeId
	 * @param searchParam
	 * @param start
	 * @param count
	 * @param x
	 * @param y
	 * @return
	 */
	public static ArrayList<Staff> getStaffByChannel(Context context,
			Long channelTypeId, Long pointOfSale, String searchParam,
			int start, int count, Double x, Double y, String[] replace) {
		DBOpenHelper dbOpenHelper = null;
		SQLiteDatabase database = null;
		try {

			dbOpenHelper = new DBOpenHelper(context);
			database = dbOpenHelper.getReadableDatabase();
			StaffDAL dal = new StaffDAL(database);
			ArrayList<Staff> lstStaff = dal.getStaffByChannel(channelTypeId,
					pointOfSale, searchParam, start, count, replace);
			dbOpenHelper.close();
			database.close();
			return lstStaff;
		} catch (Exception e) {
			Log.e(logTag, "Exeption", e);

		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}
			if (database != null) {
				database.close();
			}
		}
		return new ArrayList<>();
	}
	
	public static ArrayList<Staff> getLstStaffByStaffMngt(Context context, String staffCode) {
		DBOpenHelper dbOpenHelper = null;
		SQLiteDatabase database = null;
		try {
			dbOpenHelper = new DBOpenHelper(context);
			database = dbOpenHelper.getReadableDatabase();
			StaffDAL dal = new StaffDAL(database);
			ArrayList<Staff> lstStaff = dal.getLstStaffByStaffMngt(staffCode);
			dbOpenHelper.close();
			database.close();
			return lstStaff;
		} catch (Exception e) {
			Log.e(logTag, "Exeption", e);

		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}
			if (database != null) {
				database.close();
			}
		}
		return new ArrayList<>();
	}

	public static List<Staff> getStaffByStaffLocation(Context context, String x, String y) {
		DBOpenHelper dbOpenHelper = null;
		SQLiteDatabase database = null;
		try {
			dbOpenHelper = new DBOpenHelper(context);
			database = dbOpenHelper.getReadableDatabase();
			StaffDAL dal = new StaffDAL(database);
			List<Staff> lstStaff = dal.getStaffByStaffLocation(x,y);
			dbOpenHelper.close();
			database.close();
			return lstStaff;
		} catch (Exception e) {
			Log.e(logTag, "Exeption", e);
			
		} finally {
			if (dbOpenHelper != null) {
				dbOpenHelper.close();
			}
			if (database != null) {
				database.close();
			}
		}
		return new ArrayList<>();
	}
	
	
}

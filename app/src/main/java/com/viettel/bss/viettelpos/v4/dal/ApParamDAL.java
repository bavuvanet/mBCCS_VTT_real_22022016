package com.viettel.bss.viettelpos.v4.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.bankplus.object.TelecomSuppliers;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.ApParamObj;

import java.util.ArrayList;
import java.util.List;

public class ApParamDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	private final String logTag = ApParamDAL.class.getName();

	public ApParamDAL(Context context) {
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

	/**
	 * 
	 * @param keyMenu
	 * @return
	 * @throws Exception
	 */
	public String getListMenu(String[] keyMenu) {
		StringBuilder result = new StringBuilder("");
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  par_type FROM ap_param");
		sql.append(" WHERE par_name = 'SM_PERMISSION' and status = 1");
		sql.append(" and par_value in ( ?");
		for (int i = 1; i < keyMenu.length; i++) {
			sql.append(" ,?");
		}
		sql.append(" )");
		sql.append(" ORDER BY id DESC");
		Cursor c = database.rawQuery(sql.toString(), keyMenu);
		if (c != null && c.moveToFirst()) {
			do {
				result.append(c.getString(0)).append(";");
			} while (c.moveToNext());
		}
		result.insert(0, ";");
		return result.toString();
	}

	private final String tag = "ap param dal";

	public ArrayList<ApParamObj> getArrReasons() {
		open();
		ArrayList<ApParamObj> arrREs = new ArrayList<>();
		String sql = "SELECT par_type, par_value FROM ap_param "
				+ "WHERE par_name = 'UPDATE_TASK_REASON'";

		Cursor cursor = database.rawQuery(sql, null);
		Log.e(tag, (cursor == null) + ", " + cursor.getCount()
				+ "............cursor null");
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					ApParamObj apParamObj = new ApParamObj();
					apParamObj.setParType(cursor.getString(0));
					apParamObj.setParValue(cursor.getString(1));
					arrREs.add(apParamObj);
				} while (cursor.moveToNext());
            }
		}
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrREs;
	}

	public List<Spin> getAppParam(String par_name) {
		open();
		List<Spin> lstSpin = new ArrayList<>();
		String sql = "SELECT par_type, par_value, description FROM ap_param "
				+ "WHERE par_name = ? and status = 1 ";

		String[] selectionArgs = { par_name };
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		Log.d(tag, (cursor == null) + " cursor.getCount() " + cursor.getCount()
				+ " par_name " + par_name);
		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					Spin obj = new Spin();
					obj.setId(cursor.getString(0));
					obj.setValue(cursor.getString(1));
					obj.setName(cursor.getString(2));
					lstSpin.add(obj);
				} while (cursor.moveToNext());
            }
		}
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstSpin;
	}
	
	public List<Spin> getAppParam(String par_name, boolean order) {
		open();

		List<Spin> lstSpin = new ArrayList<>();
		String sql = "SELECT par_type, par_value FROM ap_param "
				+ "WHERE par_name = ? and status = 1 ";
		if(order){
			sql += " order by par_type";
		}

		String[] selectionArgs = { par_name };
		Cursor cursor = database.rawQuery(sql, selectionArgs);
		Log.d(tag, (cursor == null) + " cursor.getCount() " + cursor.getCount()
				+ " par_name " + par_name);

		if (cursor != null && cursor.getCount() > 0) {
			if (cursor.moveToFirst()) {
				do {
					Spin obj = new Spin();
					obj.setId(cursor.getString(0));
					obj.setValue(cursor.getString(1));					
					lstSpin.add(obj);
				} while (cursor.moveToNext());
            }
		}
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lstSpin;
	}

	/**
	 * getValue
	 * @param par_name
	 * @param par_type
	 * @return
	 */
	public String getValue(String par_name, String par_type) {
		try {
			open();
			String sql = "SELECT par_value FROM ap_param "
					+ "WHERE par_name = ? and par_type = ? and status = 1";

			String[] selectionArgs = { par_name, par_type };
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			Log.d(tag,
					(cursor == null) + " cursor.getCount() "
							+ cursor.getCount() + " par_name = " + par_name
							+ ", par_type = " + par_type);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					return cursor.getString(0);
				}
			}

			close();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Log.d("Error when getValue: ", ex.toString());
		}
		return null;
	}

	/**
	 * getValue
	 * 
	 * @param par_name
	 * @param par_type
	 * @return
	 */
	public ArrayList<TelecomSuppliers> getLstTelecomSuppliers(String parType) {
		try {
			ArrayList<TelecomSuppliers> result = new ArrayList<TelecomSuppliers>();
			open();
			String sql = "SELECT par_name,par_value,description FROM ap_param "
					+ "WHERE par_type = ? and status = 1 order by id";
			String[] selectionArgs = { parType };
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					do {
						TelecomSuppliers obj = new TelecomSuppliers();
						obj.setName(cursor.getString(0));
						obj.setCode(cursor.getString(1));
						obj.setValue(cursor.getString(2));
						result.add(obj);
					} while (cursor.moveToNext());
				}
				return result;
			}

			close();
		} catch (Exception ex) {
			Log.d("Error when getValue: ", ex.toString());
		}
		return null;
	}

	public ArrayList<ApParamBO> getLstApParamByName(String parName) {
		try {
			ArrayList<ApParamBO> result = new ArrayList<ApParamBO>();
			open();
			String sql = "SELECT par_type,par_value,description FROM ap_param "
					+ "WHERE par_name = ? and status = 1 order by id";
			String[] selectionArgs = { parName };
			Cursor cursor = database.rawQuery(sql, selectionArgs);
			if (cursor != null && cursor.getCount() > 0) {
				if (cursor.moveToFirst()) {
					do {
						ApParamBO obj = new ApParamBO();
						obj.setParType(cursor.getString(0));
						obj.setParValue(cursor.getString(1));
						obj.setDescription(cursor.getString(2));
						result.add(obj);
					} while (cursor.moveToNext());
				}
				return result;
			}

			close();
		} catch (Exception ex) {
			Log.d("Error when getValue: ", ex.toString());
		}
		return null;
	}
}

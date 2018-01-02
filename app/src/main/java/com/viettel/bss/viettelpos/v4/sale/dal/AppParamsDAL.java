package com.viettel.bss.viettelpos.v4.sale.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;

import java.util.ArrayList;

/**
 * 
 * @author huypq15
 * 
 */
public class AppParamsDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	private final String logTag = AppParamsDAL.class.getName();

	public AppParamsDAL(Context context) {
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
	 * lay danh sach goi cuoc VAS
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Spin> getListParam(String type) {
		ArrayList<Spin> result = new ArrayList<>();
		Cursor c = null;
		try {
			StringBuilder sql = new StringBuilder();
            sql.append(" SELECT   VALUE ");
			sql.append(" FROM   option_set_value_im");
			sql.append(" WHERE   status = 1");
			sql.append(" AND option_set_id = (SELECT   id");
            sql.append(" FROM   app_params");
			sql.append(" WHERE   code = ?)");

			c = database.rawQuery(sql.toString(), new String[] { type });
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					Spin item = new Spin();
					item.setId(c.getString(0));
					item.setValue(c.getString(1));
					result.add(item);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}
	public ArrayList<Spin> getListParamSale(String type) throws Exception {
		ArrayList<Spin> result = new ArrayList<Spin>();
		Cursor c = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT   	value, name");
			sql.append(" FROM   	option_set_value_sale");
            sql.append(" WHERE  	status = 1");
			sql.append(" AND 		option_set_id = (SELECT   id");
			sql.append(" FROM   	option_set_sale where code = ? and status = 1) ");
            sql.append(" ORDER BY   VALUE");

			c = database.rawQuery(sql.toString(), new String[] { type });
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					Spin item = new Spin();
					item.setId(c.getString(0));
					item.setValue(c.getString(1));
					result.add(item);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}


}

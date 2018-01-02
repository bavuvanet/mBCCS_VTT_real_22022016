package com.viettel.bss.viettelpos.v4.sale.dal;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

class StaffSaleDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public StaffSaleDAL(Context context) {
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
	 * Lay ban ghi staff dua vao staffId
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getStaffByStaffId(Long staffId) {
		String sql = " select staff_id, staff_code, name, shop_id,"
				+ " channel_type_id,price_policy,point_of_sale  "
				+ " from staff_sale where status  = 1 and staff_id  = ? ";
		Cursor c = null;
		try {
			c = database.rawQuery(sql, new String[] { staffId + "" });
			if (c.moveToFirst()) {
				Staff staff = new Staff();
				staff.setStaffId(c.getLong(0));
				staff.setStaffCode(c.getString(1));
				staff.setName(c.getString(2));
				staff.setShopId(c.getLong(3));
				staff.setChannelTypeId(c.getLong(4));
				staff.setPricePolicy(c.getString(5));
				staff.setPointOfSale(c.getLong(6));
				c.close();
				return staff;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return null;
	}

	public Staff getStaffByStaffCode(String staffCode) {
		String sql = "select staff_id, staff_code, name, shop_id,"
				+ "channel_type_id,price_policy from staff_sale where status = 1 and upper(staff_code) = ?";
		Cursor c = null;
		try {
			c = database
					.rawQuery(sql, new String[] { staffCode.toUpperCase() });
			if (c.moveToFirst()) {
				Staff staff = new Staff();
				staff.setStaffId(c.getLong(0));
				staff.setStaffCode(c.getString(1));
				staff.setName(c.getString(2));
				staff.setShopId(c.getLong(3));
				staff.setChannelTypeId(c.getLong(4));
				staff.setPricePolicy(c.getString(5));
				c.close();
				return staff;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}
		return null;
	}

}

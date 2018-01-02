package com.viettel.bss.viettelpos.v4.charge.dal;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;


public class GetStaffcodeDal {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	private final Context context;

	public GetStaffcodeDal(Context context) {
		this.context = context;
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
	
	public com.viettel.bss.viettelpos.v4.sale.object.Staff getStaff() {
		StaffDAL dal = new StaffDAL(database);
        return dal.getStaffByStaffCode(Session.userName);
	}
}

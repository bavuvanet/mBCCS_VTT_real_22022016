package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.sale.object.StockType;

class StocktTypeDAL {
	private final SQLiteDatabase database;

	// private DBOpenHelper dal;

	public StocktTypeDAL(SQLiteDatabase database) {
		this.database = database;
	}

	// public void open() throws SQLException {
	// database = dal.getWritableDatabase();
	// }
	//
	// public void close() throws Exception {
	// dal.close();
	// }

	public ArrayList<StockType> getStockType(Long ownerId, Long collId) {
		ArrayList<StockType> result = new ArrayList<>();
		String query = "select stock_type_id, table_name from Stock_Type " +
				"where status=1 and stock_Type_Id in "
				+ "( select stock_Type_Id from Stock_Model where status= 1 and stock_Model_Id in  "
				+ "(select id.stock_Model_Id from Stock_Total id where status = 1 and id.owner_Type= 2 and owner_id = ? ))";
		if (collId == null) {
			query += "  AND table_Name IS NOT NULL;";
		} else {
			query += ";";
		}
		Cursor cursor = database.rawQuery(query, new String[] { ownerId + "" });
		if (cursor.moveToFirst()) {
			do {
				StockType item = new StockType();
				item.setStockTypeId(cursor.getLong(0));
				item.setTableName(cursor.getString(1));
				result.add(item);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return result;
	}
}

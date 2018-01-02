package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransAction;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;

public class ConfirmNoteDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;

	public ConfirmNoteDAL(Context context) {
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
	 * Lay thong tin danh sach phieu xuat
	 * 
	 * @return
	 */
	public ArrayList<StockTransAction> getLstStockTrans(String toOwnerCode) {

		ArrayList<StockTransAction> result = new ArrayList<>();
		Cursor c = null;
		try {
			StaffDAL staffDAL = new StaffDAL(database);
			Staff staff = staffDAL.getStaffByStaffCode(toOwnerCode);
			if (staff == null) {
				return result;
			}
            String sql = "" + " SELECT   sta.action_code," +
                    " datetime(sta.create_datetime)," +
                    " st.stock_trans_id" +
                    " FROM   stock_trans_action sta, stock_trans st" +
                    " WHERE       1 = 1" +
                    " AND sta.stock_trans_id  = st.stock_trans_id " +
                    " AND sta.action_type  = 2" +
                    " AND st.to_owner_id  = ? " +
                    " AND st.to_owner_type  = 2" +
                    " AND st.stock_trans_type  = 1" +
                    " AND st.stock_trans_status  IN (3) order by datetime(sta.create_datetime) desc; ";
            c = database.rawQuery(sql,
					new String[] { staff.getStaffId() + "" });
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					StockTransAction item = new StockTransAction();
					item.setExpCode(c.getString(0));
					String strDate = c.getString(1);
					item.setExportDate(DateTimeUtils.convertStringToTime(
							strDate, "yyyy-MM-dd hh:mm:ss"));
					item.setStockTransId(c.getLong(2));
					result.add(item);
				} while (c.moveToNext());
			}
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return result;
	}

	/**
	 * Lay thong tin chi tiet phieu xuat
	 * 
	 * @param stockTransId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<StockTransDetail> getLstStockTransDetail(Long stockTransId) {

		ArrayList<StockTransDetail> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT   sm.stock_model_id,");
		sql.append(" sm.stock_model_code,");
		sql.append(" sm.name,");
		sql.append(" sn.action_code,");
		sql.append(" st.stock_trans_id,");
		sql.append(" st.state_id,");
		sql.append(" datetime (st.create_datetime),");
		sql.append(" (select name from  app_params ap where ap.type = 'STOCK_MODEL_UNIT' and sm.unit = ap.code) as unitName,");
		sql.append(" st.quantity_res");
		// sql.append(" ap.name");
		sql.append(" FROM   stock_trans_detail st, stock_model sm, stock_trans_action sn");
		sql.append(" WHERE   sm.stock_model_id  = st.stock_model_id ");
		// sql.append(" AND   sm.unit = ap.code");
		sql.append(" AND sn.stock_trans_id  = st.stock_trans_id ");
		sql.append(" AND sn.action_type  = 2");
		sql.append(" and st.stock_trans_id  = ?  order by datetime(st.create_datetime) desc");
		Cursor c = null;
		try {
			c = database.rawQuery(sql.toString(), new String[] { stockTransId
					+ "" });
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					StockTransDetail item = new StockTransDetail();
					item.setStockModelId(c.getLong(0));
					item.setStockModelCode(c.getString(1));
					item.setStockModelName(c.getString(2));
					item.setExpCode(c.getString(3));
					item.setStockTransId(c.getLong(4));
					item.setStateId(c.getLong(5));
					String strDate = c.getString(6);
					item.setExpDate(DateTimeUtils.convertStringToTime(strDate,
							"yyyy-MM-dd hh:mm:ss"));
					item.setUnitName(c.getString(7));
					item.setQuantity(c.getLong(8));
					result.add(item);
				} while (c.moveToNext());
			}
		} finally{
			if(c !=null){
				c.close();
			}
		}

		return result;
	}

	/**
	 * 
	 * @param stockTransId
	 * @return
	 */
	public ArrayList<Serial> getLstSerial(Long stockTransId, Long stockModelId) {
		ArrayList<Serial> result = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT   from_serial, to_serial");
		sql.append("   FROM   stock_trans_serial");
		sql.append(" WHERE   stock_trans_id  = ? "
				+ " AND stock_model_id  = ? ");
		
		Cursor c =null;
		
		try {
			c= database.rawQuery(sql.toString(), new String[] {
				stockTransId + "", stockModelId + "" });
			if (c == null) {
				return result;
			}
			if (c.moveToFirst()) {
				do {
					Serial item = new Serial();
					item.setFromSerial(c.getString(0));
					item.setToSerial(c.getString(1));
					result.add(item);
				} while (c.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(c!=null){
				c.close();
			}
		}
		
		
		return result;
	}

	/**
	 * Cap nhat tam thoi stock_trans khi update thanh cong
	 * 
	 * @param stockTransId
	 * @throws Exception
	 */
	public void updateStockTrans(Long stockTransId) {
		String sql = "update stock_trans set stock_trans_status = 0 where stock_trans_id = ? ";
		database.execSQL(sql, new String[] { stockTransId + "" });
	}
}

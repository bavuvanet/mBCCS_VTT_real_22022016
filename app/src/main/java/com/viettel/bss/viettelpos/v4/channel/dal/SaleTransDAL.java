package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.channel.object.SaleTransOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.SALE_TRANS;

public class SaleTransDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;

    public SaleTransDAL(Context context) {
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
	public Long getTotalBuyofCurentMonth(String TABLE_NAME, String month, String year, Long staffId){
		SQLiteDatabase db = mDAL.getReadableDatabase();
		Cursor mCursor = null;
		long totalOfMonth = 0;
		
		String sql = "select strftime ('%m',sale_trans_date) as valMonth, sum(amount_tax) as totalMonth from "+ TABLE_NAME+" where strftime('%Y', `sale_trans_date`) = ? AND  strftime ('%m',sale_trans_date)=? AND staff_id = ? GROUP BY valMonth";
		String[] whereArgs = new String[] {
			    year,
			    month,
			    Long.toString(staffId),
			};
		mCursor = db.rawQuery(sql, whereArgs);
		if(mCursor.moveToFirst()){
		    totalOfMonth =Long.parseLong(mCursor.getString(1));
		}
		
		return totalOfMonth;
	}
	public String lastDateBuy(String TABLE_NAME, Long staffId){
		String lastDateBuy = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		Cursor mCursor = null;
		String sql = "select sale_trans_date from " + TABLE_NAME + " where staff_id = ? order by sale_trans_date desc limit 1";
		String[] whereArgs = new String[] {
			   Long.toString(staffId),
			};
		mCursor = db.rawQuery(sql, whereArgs);
		if(mCursor.moveToFirst()){
			lastDateBuy = mCursor.getString(0);
		}
		return lastDateBuy;
	}
	public ArrayList<SaleTransOJ> getHistorySaleTrans(int numberDay,int first, int end, String staffCode, Long objectId) {
		int count = 0;
		ArrayList<SaleTransOJ> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		String selection = null;
		selection = " staff_id = ? ";
		String[] cols= new  String[]{SALE_TRANS.KEY_ID,SALE_TRANS.KEY_DATE, SALE_TRANS.KEY_AMOUT_TAX};
		String[] whereArgs = new  String[]{
			Long.toString(objectId)
		};

        String TABLE_NAME = "sale_trans";
        mCursor = db.query(TABLE_NAME, cols , selection, whereArgs, null, null, null);
		Log.i("TAG2","COUNT = " + mCursor.getCount());
		if (mCursor != null){ 
			if (mCursor.moveToPosition(first)){
				while (!mCursor.isAfterLast() ) {
					int saleTransId = mCursor.getInt(0);
					String date = mCursor.getString(1).split(" ")[0];
					long amout_tax = mCursor.getLong(2);
					SaleTransOJ mSaleTrans = new SaleTransOJ(date, amout_tax);
					
					mArrayList.add(mSaleTrans);
					mCursor.moveToNext();
				}
			}
		}
		Log.i("TAG2","mArrayList size = " + mArrayList.size());
		return mArrayList;

	}
}

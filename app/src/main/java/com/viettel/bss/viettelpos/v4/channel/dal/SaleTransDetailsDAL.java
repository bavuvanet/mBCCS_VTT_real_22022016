package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.channel.object.SaleTransDetailOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.SALE_TRANS_DETAIL;

public class SaleTransDetailsDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;

    public SaleTransDetailsDAL(Context context) {
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
	
	public ArrayList<SaleTransDetailOJ> getHistorySaleTransDetail(int numberDay,int first, int end, String staff_code, int sale_trans_id) {
		int count = 0;
		ArrayList<SaleTransDetailOJ> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		String selection = null;
		selection = " sale_trans_id = ? ";
		String[] whereArgs = null;
		whereArgs = new String[]{Integer.toString(sale_trans_id)};
		String[] colums = null;
		colums = new String[]{
					SALE_TRANS_DETAIL.KEY_ID,
					SALE_TRANS_DETAIL.KEY_STOCK_MODEL_NAME, 
					SALE_TRANS_DETAIL.KEY_QUANTITY, 
					SALE_TRANS_DETAIL.KEY_AMOUT, 
					SALE_TRANS_DETAIL.KEY_SALE_ID,
					SALE_TRANS_DETAIL.KEY_DATE
				};
        String TABLE_NAME = "sale_trans_detail";
        mCursor = db.query(TABLE_NAME, colums , selection, whereArgs, null, null, null);
		Log.i("TAG2","COUNT = " + mCursor.getCount());
		if (mCursor != null){ 
			if (mCursor.moveToPosition(first)){
				while (!mCursor.isAfterLast() ) {
					
					String stock_name = mCursor.getString(1);
					String quantity = mCursor.getString(2);
					String amount = mCursor.getString(3);
					SaleTransDetailOJ mSaleTrans = new SaleTransDetailOJ(stock_name, quantity, amount);
					
					mArrayList.add(mSaleTrans);
					mCursor.moveToNext();
				}
			}
		}
		
		Log.i("TAG2","mArrayList size = " + mArrayList.size());
		return mArrayList;

	}
}

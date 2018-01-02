package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.channel.object.ChannelContentCareOJ;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.CONTENT_CARE;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.dal.ShopDAL;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;

public class ChannelCareDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper mDAL;

    public ChannelCareDAL(Context context) {
		mDAL = new DBOpenHelper(context);
	}

	private void open() throws SQLException {
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

	public long getDurChannelCare() {
		int res = 0;
		open();
		// TODO Auto-generated method stub
		try {
			Long shopId = Session.loginUser.getShopId();

			// String sqlGetArea =
			// "select province,district from shop where shop_id = ?";
			// Cursor cursor = database.rawQuery(sqlGetArea, new String[]{shopId
			// + ""});
			String codeProv = null, codeDistr = null;
			// Log.e(tag, (cursor == null) + "....." + cursor.getCount()
			// + "..........cursor null count");
			// if (cursor != null && cursor.getCount() > 0) {
			// if (cursor.moveToFirst()) {
			// codeProv = cursor.getString(0);
			// codeDistr = cursor.getString(1);
			// Log.e(tag, codeProv + ", " + codeDistr + "..........area");
			// }
			// }
			ShopDAL shopDAL = new ShopDAL(database);
			Shop shop = shopDAL.getShopByShopId(shopId);
			String sqlDur = "SELECT   duration FROM   care_channel_time WHERE "
					+ "status = 1 AND province = ? AND district = ?";
			// sqlDur = "SELECT   duration FROM   care_channel_time ";
			String[] argSelect = new String[]{shop.getProvince(),
					shop.getDistrict()};
			// argSelect = null;

			Cursor cursor = database.rawQuery(sqlDur, argSelect);
			Log.e(tag, (cursor == null) + ", " + cursor.getCount()
					+ "..........cursor dur null");
			if (cursor != null && cursor.getCount() > 0) {
				int iDur = cursor.getColumnIndex("duration");
				if (cursor.moveToFirst()) {
					String tmp = cursor.getString(iDur);
					Log.e(tag, tmp + "........tmp");
					res = Integer.parseInt(tmp);
				}
			}

		} catch (Exception e) {
			Log.e("ChannelCareDAL", "Exception", e);
			// TODO: handle exception
		}
		try {
			close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.e(tag, res + ".....dur channel care");
		return res;
	}
	private final String tag = "cn care dal";
	public ArrayList<ChannelContentCareOJ> getListCareContent(int first, int end) {
		int count = 0;
		ArrayList<ChannelContentCareOJ> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = mDAL.getReadableDatabase();
		try {
            String TABLE_NAME = "content_care";
            mCursor = db.query(TABLE_NAME, new String[] { CONTENT_CARE.KEY_ID,
					CONTENT_CARE.KEY_CONTENT, CONTENT_CARE.KEY_DATE },
					"status = 1", null, null, null, null);
			Log.i("TAG2", "COUNT = " + mCursor.getCount());
			if (mCursor != null) {
				if (mCursor.moveToPosition(first)) {
					while (!mCursor.isAfterLast() && count < end) {
						int content_id = mCursor.getInt(0);
						String content_care = mCursor.getString(1);
						String content_date = mCursor.getString(2).split(" ")[0];
						if (content_date == null) {
							content_date = "";

						}
						ChannelContentCareOJ mChannelCareContent = new ChannelContentCareOJ(
								content_id, content_care, content_date);
						mArrayList.add(mChannelCareContent);
						count++;
						mCursor.moveToNext();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
			if (db != null) {
				db.close();
			}
		}

		Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}

}

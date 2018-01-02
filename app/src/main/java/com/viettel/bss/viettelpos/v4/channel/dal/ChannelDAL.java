package com.viettel.bss.viettelpos.v4.channel.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.location.Location;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.STAFF;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

public class ChannelDAL {
	private SQLiteDatabase database;
	private final DBOpenHelper channelDAL;

    public ChannelDAL(Context context) {
		channelDAL = new DBOpenHelper(context);
    }

	public void open() throws SQLException {
		database = channelDAL.getWritableDatabase();
	}

	public void close() {
		try {
			channelDAL.close();
			if (database != null) {
				database.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	public int queryCount(String TABLE_NAME, Long staffType, String inputSearch) {

		Cursor mCursor = null;
		String selectCon = "";
		String[] rowquery = new String[] { "count(*)" };
		String[] whereArgs = null;
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		Log.i("TAG", "staffType = " + staffType);
		if (staffType != null) {
			if (staffType == 0) {
				selectCon = null;
				if (inputSearch != null) {
					selectCon = "name like ?";
					whereArgs = new String[] { "%" + inputSearch + "%" };
				}
			} else {
				selectCon = "staffType = ?";
				if (inputSearch != null) {
					selectCon += " and name like ?";
					whereArgs = new String[] { Long.toString(staffType),
							"%" + inputSearch + "%" };
				}
			}
		}
		Log.i("TAG", "selectCon = " + selectCon);
		mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs, null,
				null, null);

		return mCursor.getCount();
	}

	public String getNumUpdateLocation(){
		String num_update_location = "";
		 StringBuilder sql = new StringBuilder();
		 sql.append("select num_update_location from staff");
		
		return num_update_location;
	}
	
	
	public void updateLocation(String TABLE_NAME, String x, String y,
			String staffCode) throws SQLException {

		SQLiteDatabase db = null;
		SQLiteStatement updatelocationXStmt = null;
//		String[] arg = new String[] { staffCode.toUpperCase() };
//		String cond = "upper(staff_code) = ?";
//		Log.e("tag", "staffCode = " + staffCode);
//		ContentValues newValues = new ContentValues();
//		newValues.put("x", x);
//		newValues.put("y", y);
//		newValues.put("num_update_location", num_update_location);
//		db.update("staff", newValues, cond, arg);
		String sql = "update staff set x = ?, y = ? , num_update_location = num_update_location + 1 where upper(staff_code) = ?";
		
		try {
			
			db = channelDAL.getWritableDatabase();
			db.beginTransactionNonExclusive();
			updatelocationXStmt = db.compileStatement(sql);
			updatelocationXStmt.bindAllArgsAsStrings(new String[] {
					x + "",
					y + "",
					staffCode.toUpperCase() + "" });
			updatelocationXStmt.executeUpdateDelete();
			updatelocationXStmt.clearBindings();
			db.setTransactionSuccessful();
			db.endTransaction();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (db != null) {
				try {
					if (db.inTransaction()) {
						db.setTransactionSuccessful();
						db.endTransaction();
					}
					db.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			if(updatelocationXStmt != null){
				updatelocationXStmt.close();
			}
		}
	}

	public String getOwnerCode1(String TABLE_NAME, String staffCode) {
		String[] rowquery = new String[] { STAFF.KEY_ID, };
		Cursor mCursor = null;
		String staffCode_ = "";
		String selectCon = " staff_code = ?";
		String[] whereArgs = new String[] { staffCode };
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs, null,
				null, null);
		if (mCursor != null) {

			if (mCursor.moveToFirst()) {
				staffCode_ = mCursor.getString(0);
			}
		}
		return staffCode_;
	}

	public Staff getStaffByStaffCode(String TABLE_NAME, String staffCode,
			Location myLocation) {
		/*
		 * String[] rowquery = new String[]{STAFF.KEY_ID, STAFF.KEY_NAME,
		 * STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO, STAFF.KEY_ID_USER_NO_DATE,
		 * STAFF.KEY_ID_USER_BIRTHDAY, STAFF.KEY_CODE, STAFF.KEY_LOCATION_X,
		 * STAFF.KEY_LOCATION_Y, STAFF.KEY_CHANNEL_TYPE_ID};
		 */

		String[] rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
				STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
				STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
				STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
				STAFF.KEY_CHANNEL_TYPE_ID, STAFF.TEL, STAFF.ISDN_AGENT, STAFF.ID_ISSUE_PLACE, 
				STAFF.PROVINCE, STAFF.DISTRICT, STAFF.PRECINCT, STAFF.STREET, STAFF.HOME, STAFF.STREETBLOCK,
				STAFF.POINT_OF_SALE_TYPE, STAFF.CONTRACT_METHOD, STAFF.BUSINESS_METHOD};
		Staff mTask = null;
		Cursor mCursor = null;
		String selectCon = " upper(staff_code) = ? ";
		String[] whereArgs = new String[] { staffCode != null ? staffCode.toUpperCase() : staffCode };
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs, null,
				null, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					Long staffid = mCursor.getLong(0);
					String name = mCursor.getString(1);
					String address = mCursor.getString(2);
					String iduserno = mCursor.getString(3);

					String id_issue_date = mCursor.getString(4);
					String birthday = mCursor.getString(5);
					String staff_code = mCursor.getString(6);
					Double x = mCursor.getDouble(7);
					Double y = mCursor.getDouble(8);
					Long channelTypeId = mCursor.getLong(9);
					String tel = mCursor.getString(10);
					String isdnAgent = mCursor.getString(11);
					String idIssuePlace = mCursor.getString(12);
					
					Log.e("TAG", "birthday=========== = " + birthday);
					Location locationStaff = new Location("location Satff");
					String linkImg = Constant.LINK_WS_SYNC + "image/"
							+ Session.getToken() + "/" + Constant.UPDATE_STAFF + "_"
							+ staffid + ".jpg";
					locationStaff.setLatitude(x);
					locationStaff.setLongitude(y);
					if (myLocation != null & x != 0 & y != 0) {
						float distance = 0;
						distance = myLocation.distanceTo(locationStaff);

						mTask = new Staff(staffid, name, address, linkImg,
								iduserno, id_issue_date, birthday, staff_code,
								distance, channelTypeId, x, y, tel, isdnAgent

						);
						mTask.setProvince(mCursor.getString(13));
						mTask.setDistrict(mCursor.getString(14));
						mTask.setPrecinct(mCursor.getString(15));
						mTask.setStreet(mCursor.getString(16));						
						mTask.setHome(mCursor.getString(17));
						mTask.setStreetBlock(mCursor.getString(18));
						mTask.setIdIssuePlace(idIssuePlace);
						mTask.setPointOfSaleType(mCursor.getString(19));
						mTask.setContractMethod(mCursor.getString(20));
						mTask.setBusinessMethod(mCursor.getString(21));
					} else {
						mTask = new Staff(staffid, name, address, linkImg,
								iduserno, id_issue_date, birthday, staff_code,
								-1, channelTypeId, x, y, tel, isdnAgent);
						mTask.setProvince(mCursor.getString(13));
						mTask.setDistrict(mCursor.getString(14));
						mTask.setPrecinct(mCursor.getString(15));
						mTask.setStreet(mCursor.getString(16));						
						mTask.setHome(mCursor.getString(17));
						mTask.setStreetBlock(mCursor.getString(18));
						mTask.setIdIssuePlace(idIssuePlace);
						mTask.setPointOfSaleType(mCursor.getString(19));
						mTask.setContractMethod(mCursor.getString(20));
						mTask.setBusinessMethod(mCursor.getString(21));
					}

				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		db.close();
		return mTask;
	}

	public ArrayList<Staff> findByListStaffCode(String TABLE_NAME,
			String listStaffCode, Location myLocation) {
		ArrayList<Staff> mArrayList = new ArrayList<>();
		String cond = "status = 1 and staff_code in ( ? ) ";
		listStaffCode = listStaffCode.substring(0, listStaffCode.length() - 1);
		Log.e("listStaffCode", " listStaffCode = " + listStaffCode);
		String[] arg = new String[] { listStaffCode };
		Cursor mCursor = null;
		SQLiteDatabase db = channelDAL.getReadableDatabase();
		String[] rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
				STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
				STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
				STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
				STAFF.KEY_CHANNEL_TYPE_ID, STAFF.TEL, STAFF.ISDN_AGENT };
		mCursor = db.query(TABLE_NAME, rowquery, cond, arg, null, null, null);

		if (mCursor != null && mCursor.getCount() > 0) {
			if (mCursor.moveToFirst()) {
				do {
					Long staffid = mCursor.getLong(0);
					String name = mCursor.getString(1);
					String address = mCursor.getString(2);
					String iduserno = mCursor.getString(3);
					String id_issue_date = mCursor.getString(4);
					String birthday = mCursor.getString(5);
					String staff_code = mCursor.getString(6);
					Double x = mCursor.getDouble(7);
					Double y = mCursor.getDouble(8);
					Long channelTypeId = mCursor.getLong(9);
					float dis = mCursor.getFloat(10);
					String tel = mCursor.getString(11);
					String isdnAgent = mCursor.getString(12);
					Location locationStaff = new Location("location Satff");

					locationStaff.setLatitude(x);
					locationStaff.setLongitude(y);
					float distance = 0;
					if (myLocation != null) {
						distance = myLocation.distanceTo(locationStaff);
					}

					String linkImg = Constant.LINK_WS_SYNC + "image/"
							+ Session.getToken() + "/" + Constant.UPDATE_STAFF + "_"
							+ staffid + ".jpg";
					Staff mTask = new Staff(staffid, name, address, linkImg,
							iduserno, id_issue_date, birthday, staff_code,
							distance, channelTypeId, x, y, tel, isdnAgent

					);
					mArrayList.add(mTask);
				} while (mCursor.moveToNext());

			}

		}

		return mArrayList;

	}

	/*
	 * Lay danh sach co phan trang theo man hinh
	 */
	public ArrayList<Staff> getListStaff(String TABLE_NAME, Long staffType,
			String inputSearch, Location myLocation, String userName,
			int first, int end, Boolean selectByLocation, Long pointOfSale) {
		ArrayList<Staff> mArrayList = new ArrayList<>();
		ArrayList<Staff> mArrayListNotLocation = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = null;
		
		Log.d("getListStaff", "TABLE_NAME = " + TABLE_NAME + ", staffType = " 
				+ staffType + ", inputSearch = " + inputSearch + ", userName = " 
				+ userName + ", first = " + first + ", end = " + end 
				+ ", selectByLocation = " + selectByLocation + ", pointOfSale = " + pointOfSale);
		try {

			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT   staff_id,");
			sql.append(" name,");
			sql.append(" address,");
			sql.append(" id_no,");
			sql.append(" id_issue_date,");
			sql.append(" birthday,");
			sql.append(" staff_code,");
			sql.append(" x,");
			sql.append(" y,");
			sql.append(" channel_type_id,");
			sql.append(" tel,");
			sql.append(" isdn_agent,");
			//Thuytv3_lay them thong tin kenh truyen_start
			sql.append(" business_method,");
			sql.append(" point_of_sale_type,");
			sql.append(" contract_method,");
			sql.append(" precinct,");
			sql.append(" district,");
			sql.append(" province,");
			sql.append(" street,");
			sql.append(" street_block,");
			sql.append(" home, ");
			sql.append(" id_issue_place ");
			//Thuytv3_lay them thong tin kenh truyen_end
			sql.append(" FROM   staff");
			sql.append(" WHERE   status = 1");
			sql.append(" and (staff_owner_id = ? or (sub_owner_id = ? and sub_owner_type = 2))");
			sql.append(" and staff_code <> ? COLLATE NOCASE");
			List<String> param = new ArrayList<>();
			param.add(Session.loginUser.getStaffId() + "");
			param.add(Session.loginUser.getStaffId() + "");
			param.add(Session.userName);
			if (staffType != null && staffType.compareTo(0L) != 0
					&& staffType.compareTo(-1L) != 0) {
				sql.append(" and channel_type_id = ? ");
				param.add(staffType + "");
			}
			if (pointOfSale != null) {
				sql.append(" and point_of_sale = ? ");
				param.add(pointOfSale + "");
			}
			if (selectByLocation) {
				sql.append(" and (x = '' or y = '' or x = '0' or y = '0' or x is null or y is null or x = 0 or y = 0)");
			}
			if (inputSearch != null && !inputSearch.trim().isEmpty()) {
				sql.append(" and (staff_code like ? COLLATE NOCASE or name   like ? COLLATE NOCASE " +
						" or name_nosign like ? COLLATE NOCASE)");

				if (inputSearch.contains("%")) {
					inputSearch.replace("%", "");
				}
				param.add("%" + inputSearch + "%");
				param.add("%" + inputSearch + "%");
				param.add("%" + inputSearch + "%");

			}
			if (myLocation != null && myLocation.getLatitude() != 0
					&& !selectByLocation) {
				sql.append(" order by (? - ifNull(x,0)) * (? - ifNull(x,0)) + (? - ifNull(y,0)) * (? - ifNull(y,0))");
				param.add(myLocation.getLatitude() + "");
				param.add(myLocation.getLatitude() + "");
				param.add(myLocation.getLongitude() + "");
				param.add(myLocation.getLongitude() + "");

			} else {
				sql.append(" order by lower(name)");
			}

			db = channelDAL.getReadableDatabase();

			String[] strParam = new String[param.size()];
			strParam = param.toArray(strParam);
			mCursor = db.rawQuery(sql.toString(), strParam);
			if (mCursor != null && mCursor.getCount() > 0) {

				if (mCursor.moveToPosition(first)) {
					int count = 0;
					do {
						Long staffid = mCursor.getLong(0);
						String name = mCursor.getString(1);
						String address = mCursor.getString(2);
						String iduserno = mCursor.getString(3);
						String id_issue_date = mCursor.getString(4);
						String birthday = mCursor.getString(5);
						String staff_code = mCursor.getString(6);
						Double x = mCursor.getDouble(7);
						Double y = mCursor.getDouble(8);
						Long channelTypeId = mCursor.getLong(9);
						String tel = mCursor.getString(mCursor.getColumnIndex(STAFF.TEL));
						String isdnAgent = mCursor.getString(11);
						//Thuytv3_lay them thong tin kenh truyen_start
						String business_method = mCursor.getString(mCursor.getColumnIndex(STAFF.BUSINESS_METHOD));
						String point_of_sale_type = mCursor.getString(mCursor.getColumnIndex(STAFF.POINT_OF_SALE_TYPE));
						String contract_method = mCursor.getString(mCursor.getColumnIndex(STAFF.CONTRACT_METHOD));
						String precinct = mCursor.getString(mCursor.getColumnIndex(STAFF.PRECINCT));
						String district = mCursor.getString(mCursor.getColumnIndex(STAFF.DISTRICT));
						String province = mCursor.getString(mCursor.getColumnIndex(STAFF.PROVINCE));
						String street = mCursor.getString(mCursor.getColumnIndex(STAFF.STREET));
						String streetBlock = mCursor.getString(mCursor.getColumnIndex(STAFF.STREETBLOCK));
						String home = mCursor.getString(mCursor.getColumnIndex(STAFF.HOME));
						String idIssuePlace = mCursor.getString(mCursor.getColumnIndex(STAFF.ID_ISSUE_PLACE));
						
						//Thuytv3_lay them thong tin kenh truyen_end
						Location locationStaff = new Location("location Satff");
						String linkImg = Constant.LINK_WS_SYNC + "image/"
								+ Session.getToken() + "/" + Constant.UPDATE_STAFF
								+ "_" + staffid + ".jpg";
						locationStaff.setLatitude(x);
						locationStaff.setLongitude(y);
						Staff mTask = null;
						if (myLocation != null && x != null && y != null
								&& x != 0 && y != 0
								&& myLocation.getLatitude() != 0) {
							float distance = 0;

							distance = myLocation.distanceTo(locationStaff);

//							mTask = new Staff(staffid, name, address, linkImg,
//									iduserno, id_issue_date, birthday,
//									staff_code, distance, channelTypeId, x, y,
//									tel, isdnAgent);
							mTask = new Staff(staffid, name, address, linkImg,
									iduserno, id_issue_date, birthday,
									staff_code, distance, channelTypeId, x, y,
									tel, isdnAgent,business_method,contract_method,
									point_of_sale_type,province,district,precinct,street,streetBlock,home, idIssuePlace);
						} else {
//							mTask = new Staff(staffid, name, address, linkImg,
//									iduserno, id_issue_date, birthday,
//									staff_code, -1, channelTypeId, x, y, tel,
//									isdnAgent);
							mTask = new Staff(staffid, name, address, linkImg,
									iduserno, id_issue_date, birthday,
									staff_code, -1, channelTypeId, x, y, tel,
									isdnAgent,business_method,contract_method,
									point_of_sale_type,province,district,precinct,street,streetBlock,home, idIssuePlace);
						}
						if (x != 0 && y != 0) {
							mArrayList.add(mTask);
						} else {
							mArrayListNotLocation.add(mTask);
						}
						count++;
					} while (mCursor.moveToNext() && count < end);

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

		ArrayList<Staff> al = new ArrayList<>();
		al.addAll(mArrayList);
		al.addAll(mArrayListNotLocation);

		return al;

	}

	public ArrayList<Staff> getListStaffNotLocation1(String TABLE_NAME,
			Long staffType, String userName, int first, int end) {
		ArrayList<Staff> mArrayList = new ArrayList<>();
		Cursor mCursor = null;
		try {

			String[] rowquery = null;
			rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
					STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
					STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
					STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
					STAFF.KEY_CHANNEL_TYPE_ID, STAFF.TEL, STAFF.ISDN_AGENT };

			String selectCon = "";
			String[] whereArgs = null;
			Log.e("userName", "userName = " + userName);
			if (userName != null && userName != "") {
				selectCon += " staff_code <> ?  COLLATE NOCASE";
				whereArgs = new String[] { userName.toUpperCase() };
			}

			SQLiteDatabase db = channelDAL.getReadableDatabase();

			selectCon += " and (x is null or x = 0) and (y is null or y = 0) and status = 1";

			mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs,
					null, null, null);

			if (mCursor.getCount() < end) {
				end = mCursor.getCount();
			}
			Log.e("TAG", "firts = " + first);
			Log.e("TAG", "end = " + end);
			Log.e("TAG", "mCursor.getCount() = " + mCursor.getCount());
			Log.e("TAG", "cond = " + selectCon);
			for (String s : whereArgs) {
				Log.e("TAG", "s :: " + s);
			}
			if (mCursor != null && mCursor.getCount() > 0) {

				if (mCursor.moveToPosition(first)) {
					int count = 0;
					do {
						Long staffid = mCursor.getLong(0);
						String name = mCursor.getString(1);
						String address = mCursor.getString(2);
						String iduserno = mCursor.getString(3);
						String id_issue_date = mCursor.getString(4);
						String birthday = mCursor.getString(5);
						String staff_code = mCursor.getString(6);
						Double x = mCursor.getDouble(7);
						Double y = mCursor.getDouble(8);
						Long channelTypeId = mCursor.getLong(9);
						String tel = mCursor.getString(10);
						String isdnAgent = mCursor.getString(11);
						Location locationStaff = new Location("location Satff");
						String linkImg = Constant.LINK_WS_SYNC + "image/"
								+ Session.getToken() + "/" + Constant.UPDATE_STAFF
								+ "_" + staffid + ".jpg";
						locationStaff.setLatitude(x);
						locationStaff.setLongitude(y);
						Staff mTask = null;
						mTask = new Staff(staffid, name, address, linkImg,
								iduserno, id_issue_date, birthday, staff_code,
								-1, channelTypeId, x, y, tel, isdnAgent);
						mArrayList.add(mTask);

						count++;
					} while (mCursor.moveToNext() && count < end);

				}

			}
		} catch (Exception ignored) {
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
		}

		return mArrayList;

	}

	/*
	 * Lay khong phan trang
	 */
	public ArrayList<Staff> getAllStaff(String TABLE_NAME, Long staffType,
			String inputSearch, Location myLocation, String userName) {
		ArrayList<Staff> mArrayList = new ArrayList<>();
		ArrayList<Staff> mArrayListNotLocation = new ArrayList<>();
		Cursor mCursor = null;
		SQLiteDatabase db = null;
		try {
			int count = 0;

			String[] rowquery = null;
			if (myLocation != null) {
				String distan = "((" + myLocation.getLatitude() + "-"
						+ STAFF.KEY_LOCATION_X + ")*("
						+ myLocation.getLatitude() + "-" + STAFF.KEY_LOCATION_X
						+ ")) + " + "((" + myLocation.getLongitude() + "-"
						+ STAFF.KEY_LOCATION_Y + ")*("
						+ myLocation.getLongitude() + "-"
						+ STAFF.KEY_LOCATION_Y + "))" + " as distance";
//				rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
//						STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
//						STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
//						STAFF.KEY_CODE, STAFF.KEY_LOCATION_X,
//						STAFF.KEY_LOCATION_Y, STAFF.KEY_CHANNEL_TYPE_ID,
//						STAFF.TEL, STAFF.ISDN_AGENT, distan, };
				//Thuytv3_lay them gia tri_start
				rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
						STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
						STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
						STAFF.KEY_CODE, STAFF.KEY_LOCATION_X,
						STAFF.KEY_LOCATION_Y, STAFF.KEY_CHANNEL_TYPE_ID,
						STAFF.TEL, STAFF.ISDN_AGENT, distan,
						STAFF.BUSINESS_METHOD,STAFF.CONTRACT_METHOD,STAFF.POINT_OF_SALE_TYPE,
						STAFF.PROVINCE,STAFF.DISTRICT,STAFF.PRECINCT,STAFF.STREET,STAFF.STREETBLOCK,STAFF.HOME };
				//Thuytv3_lay them gia tri_end
			} else {
//				rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
//						STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
//						STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
//						STAFF.KEY_CODE, STAFF.KEY_LOCATION_X,
//						STAFF.KEY_LOCATION_Y, STAFF.KEY_CHANNEL_TYPE_ID,
//						STAFF.TEL, STAFF.ISDN_AGENT };
				//Thuytv3_lay them gia tri_start
				rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
						STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
						STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
						STAFF.KEY_CODE, STAFF.KEY_LOCATION_X,
						STAFF.KEY_LOCATION_Y, STAFF.KEY_CHANNEL_TYPE_ID,
						STAFF.TEL, STAFF.ISDN_AGENT,
						STAFF.BUSINESS_METHOD,STAFF.CONTRACT_METHOD,STAFF.POINT_OF_SALE_TYPE,
						STAFF.PROVINCE,STAFF.DISTRICT,STAFF.PRECINCT,STAFF.STREET,STAFF.STREETBLOCK,STAFF.HOME };
				//Thuytv3_lay them gia tri_end
			}
			String selectCon = "";

			String[] whereArgs = null;

			Log.e("TAG", "userName: " + userName);
			if (userName != null && userName != "") {
				selectCon += " staff_code <> ?  COLLATE NOCASE";
				whereArgs = new String[] { userName.toUpperCase() };
			}

			db = channelDAL.getReadableDatabase();

			if (staffType > 0) {
				if (staffType == 0) {

					if (inputSearch != null && inputSearch != ""
							&& !inputSearch.isEmpty() && userName != null) {
						selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";

						whereArgs = new String[] { userName,
								"%" + inputSearch + "%",
								"%" + inputSearch + "%" };

					}
				} else {
					selectCon += " and channel_type_id = "
							+ Long.toString(staffType);

					if (inputSearch != null && inputSearch != "") {
						selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";
						whereArgs = new String[] { userName,
								"%" + inputSearch + "%",
								"%" + inputSearch + "%" };

					}
				}
			} else {

				if (inputSearch != null && inputSearch != ""
						&& !inputSearch.isEmpty() && userName != null) {
					selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";

					whereArgs = new String[] { userName,
							"%" + inputSearch + "%", "%" + inputSearch + "%" };

				}
			}

			Log.i("TAG", " selectCon: " + selectCon);
			Log.i("TAG", " inputSearch: " + inputSearch);
			Log.i("TAG", " staffType: " + staffType);
			selectCon += " and status = 1";
			if (myLocation != null) {
				mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs,
						null, null, "distance asc");
			} else {
				mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs,
						null, null, null);
			}
			Log.e(tag, mCursor.getCount() + ".....size list staff");
			if (mCursor != null && mCursor.getCount() > 0) {
				if (mCursor.moveToFirst()) {
					do {
						Long staffid = mCursor.getLong(0);
						String name = mCursor.getString(1);
						String address = mCursor.getString(2);
						String iduserno = mCursor.getString(3);
						String id_issue_date = mCursor.getString(4);
						String birthday = mCursor.getString(5);
						String staff_code = mCursor.getString(6);
						Double x = mCursor.getDouble(7);
						Double y = mCursor.getDouble(8);
						Long channelTypeId = mCursor.getLong(9);
						String tel = mCursor.getString(10);
						String isdnAgent = mCursor.getString(11);
						//Thuytv3_start
						String businessMethod = mCursor.getString(mCursor.getColumnIndex(STAFF.BUSINESS_METHOD));
						String contractMethod = mCursor.getString(mCursor.getColumnIndex(STAFF.CONTRACT_METHOD));
						String pointOfSaleType = mCursor.getString(mCursor.getColumnIndex(STAFF.POINT_OF_SALE_TYPE));
						String province = mCursor.getString(mCursor.getColumnIndex(STAFF.PROVINCE));
						String district = mCursor.getString(mCursor.getColumnIndex(STAFF.DISTRICT));
						String precinct = mCursor.getString(mCursor.getColumnIndex(STAFF.PRECINCT));
						String street = mCursor.getString(mCursor.getColumnIndex(STAFF.STREET));
						String streetBlock = mCursor.getString(mCursor.getColumnIndex(STAFF.STREETBLOCK));
						String home = mCursor.getString(mCursor.getColumnIndex(STAFF.HOME));
						//Thuytv3_end
						Location locationStaff = new Location("location Satff");
						String linkImg = Constant.LINK_WS_SYNC + "image/"
								+ Session.getToken() + "/" + Constant.UPDATE_STAFF
								+ "_" + staffid + ".jpg";
						locationStaff.setLatitude(x);
						locationStaff.setLongitude(y);
						Staff mTask = null;
						if (myLocation != null & x != 0 & y != 0) {
							float distance = 0;
							distance = myLocation.distanceTo(locationStaff);
//							mTask = new Staff(staffid, name, address, linkImg,
//									iduserno, id_issue_date, birthday,
//									staff_code, distance, channelTypeId, x, y,
//									tel, isdnAgent);
							mTask = new Staff(staffid, name, address, linkImg,
									iduserno, id_issue_date, birthday,
									staff_code, distance, channelTypeId, x, y,
									tel, isdnAgent,businessMethod,contractMethod,pointOfSaleType,province,district,precinct,street,streetBlock,home);
						} else {
//							mTask = new Staff(staffid, name, address, linkImg,
//									iduserno, id_issue_date, birthday,
//									staff_code, -1, channelTypeId, x, y, tel,
//									isdnAgent);
							mTask = new Staff(staffid, name, address, linkImg,
									iduserno, id_issue_date, birthday,
									staff_code, -1, channelTypeId, x, y, tel,
									isdnAgent,businessMethod,contractMethod,pointOfSaleType,province,district,precinct,street,streetBlock,home);
						}
						if (x != 0 && y != 0) {
							mArrayList.add(mTask);
						} else {
							mArrayListNotLocation.add(mTask);
						}
					} while (mCursor.moveToNext());

				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
			if (db != null) {
				db.close();
			}
		}

		ArrayList<Staff> al = new ArrayList<>();
		al.addAll(mArrayList);
		al.addAll(mArrayListNotLocation);
		Log.e(tag, al.size() + ".....size list staff");
		return al;
	}

	private final String tag = "channel dal";

	public ArrayList<Staff> getTasksToArray_(
	/* String TABLE_NAME, */Long staffType, String inputSearch,
			Location myLocation, String userName, int taskTypeId,
			boolean finished) {
		// int count = 0;
		String table = "task t, task_road tr, staff st";
		ArrayList<Staff> mArrayList = new ArrayList<>();
		ArrayList<Staff> mArrayListNotLocation = new ArrayList<>();
		Cursor mCursor = null;

		// String distan = "((" + myLocation.getLatitude() + "-"
		// + STAFF.KEY_LOCATION_X + ")*(" + myLocation.getLatitude() + "-"
		// + STAFF.KEY_LOCATION_X + ")) + " + "(("
		// + myLocation.getLongitude() + "-" + STAFF.KEY_LOCATION_Y
		// + ")*(" + myLocation.getLongitude() + "-"
		// + STAFF.KEY_LOCATION_Y + "))" + " as distance";
		String distan = "";
		if (myLocation != null) {
			distan = "((" + myLocation.getLatitude() + "-" + "st.x" + ")*("
					+ myLocation.getLatitude() + "-" + "st.x" + ")) + " + "(("
					+ myLocation.getLongitude() + "-" + "st.y" + ")*("
					+ myLocation.getLongitude() + "-" + "st.y" + "))"
					+ " as distance";
		} else {
			distan = "'' as distance";
		}
		Log.i("Tag", "distance = " + distan);

		String selectCon = "";

		String[] whereArgs = null;

		Log.e("TAG", "userName: " + userName);
		if (userName != null && userName != "") {
			selectCon += " upper(st.staff_code) <> ?  ";
			whereArgs = new String[] { userName.toUpperCase() };
		}

		// String[] rowquery = new String[]{STAFF.KEY_ID, STAFF.KEY_NAME,
		// STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
		// STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
		// STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
		// STAFF.KEY_CHANNEL_TYPE_ID, distan, STAFF.TEL, STAFF.ISDN_AGENT};

		String[] rowquery = new String[] { "st.staff_id", "st.name",
				"st.address", "st.id_no", "st.id_issue_date", "st.birthday",
				"st.staff_code", "st.x", "st.y", "st.channel_type_id", distan,
				"st.tel", "st.isdn_agent" };

		SQLiteDatabase db = channelDAL.getReadableDatabase();

		if (staffType == 0) {
			// khi chon tat ca kenh ===> phan nay ko chay den
			if (inputSearch != null && inputSearch != ""
					&& !inputSearch.isEmpty() && userName != null) {
				selectCon += " and ( upper(st.name) like ?  or upper(st.staff_code) like ? ) ";

				whereArgs = new String[] { userName.toUpperCase(),
						"%" + inputSearch.toUpperCase() + "%",
						"%" + inputSearch.toUpperCase() + "%", };

			}
		} else {
			selectCon += " and st.channel_type_id = "
					+ Long.toString(staffType);

			if (inputSearch != null && inputSearch != "") {
				selectCon += " and ( upper(st.name) like ? or upper(st.staff_code) like ? ) ";
				whereArgs = new String[] { userName.toUpperCase(),
						"%" + inputSearch.toUpperCase() + "%",
						"%" + inputSearch.toUpperCase() + "%", };

			}
		}
		selectCon += " and t.status = 1 and t.task_id = tr.task_id and t.task_type_id = 10"
				+ " and tr.object_id = st.staff_id and tr.status = 1 and tr.staff_id = "
				+ Session.loginUser.getStaffId();
		if (!finished) {
			selectCon += " and tr.progress = 1";
		}
		// String whereArgs[] = null;
		// whereArgs = new String[]{taskTypeId + "",
		// Session.loginUser.getStaffId()

		// };
		Log.i("TAG", " selectCon: " + selectCon);
		Log.i("TAG", " inputSearch: " + inputSearch);
		Log.i("TAG", " staffType: " + staffType);
		// Log.e(tag, selectCon + "=====" );//+ whereArgs[0] + "***" +
		// whereArgs[1]
		// + "***" + whereArgs[2]);
		String str = "";
		for (String text : whereArgs) {
			str += text + "****";
		}
		Log.e(tag, selectCon + "=====" + str);
		mCursor = db.query(true, table, rowquery, selectCon, whereArgs, null,
				null, "distance asc", null);
		// db.query(distinct, table, columns, selection, selectionArgs,
		// groupBy, having, orderBy, limit)
		if (mCursor != null && mCursor.getCount() > 0) {
			if (mCursor.moveToFirst()) {
				do {
					Long staffid = mCursor.getLong(0);
					String name = mCursor.getString(1);
					String address = mCursor.getString(2);
					String iduserno = mCursor.getString(3);
					String id_issue_date = mCursor.getString(4);
					String birthday = mCursor.getString(5);
					String staff_code = mCursor.getString(6);
					Double x = mCursor.getDouble(7);
					Double y = mCursor.getDouble(8);
					Long channelTypeId = mCursor.getLong(9);
					float dis = mCursor.getFloat(10);
					String tel = mCursor.getString(11);
					String isdnAgent = mCursor.getString(12);
					Location locationStaff = new Location("location Satff");

					locationStaff.setLatitude(x);
					locationStaff.setLongitude(y);
					float distance = 0;
					if (myLocation != null) {
						distance = myLocation.distanceTo(locationStaff);
					}

					String linkImg = Constant.LINK_WS_SYNC + "image/"
							+ Session.getToken() + "/" + Constant.UPDATE_STAFF + "_"
							+ staffid + Constant.extSdcard;
					Staff mTask = new Staff(staffid, name, address, linkImg,
							iduserno, id_issue_date, birthday, staff_code,
							distance, channelTypeId, x, y, tel, isdnAgent

					);

					Log.i("TAG", "X =" + x + "y = " + y);
					if (x != 0 && y != 0) {
						mArrayList.add(mTask);
					} else {
						mArrayListNotLocation.add(mTask);
					}
				} while (mCursor.moveToNext());

			}

		}

		ArrayList<Staff> al = new ArrayList<>();
		al.addAll(mArrayList);
		al.addAll(mArrayListNotLocation);

		return al;

	}

	public String convertLocationView(Double value) {
		if (value > 1000) {
			return (value / 1000) + "Km";
		} else {
			return value + "m";
		}
	}

}

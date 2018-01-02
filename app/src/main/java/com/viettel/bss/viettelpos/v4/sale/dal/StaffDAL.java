package com.viettel.bss.viettelpos.v4.sale.dal;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Define.STAFF;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class StaffDAL {
	private final SQLiteDatabase database;

	public StaffDAL(SQLiteDatabase database) {
		this.database = database;
	}

	/**
	 * Lay ban ghi staff dua vao staffId
	 * 
	 * @param staffId
	 * @return
	 */
	public Staff getStaffByStaffId(Long staffId) {
		String sql = " select staff_id, staff_code, name, shop_id, "
				+ " channel_type_id,price_policy,point_of_sale, type  "
				+ " from staff where status  = 1 and staff_id  = ? ";
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
				staff.setType(c.getLong(7));

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

	public ArrayList<Staff> getLisStaffByShopId(Long shopId, Long chanelTypeId) {
		ArrayList<Staff> mArrayList = new ArrayList<>();
		String sql = " select staff_id,staff_code, name, shop_id, channel_type_id,"
				+ " price_policy, point_of_sale"
				+ " from staff where shop_id =? AND channel_type_id = ?";
		Cursor mCursor = null;
		try {
			mCursor = database.rawQuery(sql, new String[] { shopId + "",
					chanelTypeId + "" });
			if (mCursor.moveToFirst()) {
				while (!mCursor.isAfterLast()) {
					Staff staff = new Staff();
					staff.setStaffId(mCursor.getLong(0));
					staff.setStaffCode(mCursor.getString(1));
					staff.setName(mCursor.getString(2));
					staff.setShopId(mCursor.getLong(3));
					staff.setChannelTypeId(mCursor.getLong(4));
					staff.setPricePolicy(mCursor.getString(5));
					staff.setPointOfSale(mCursor.getLong(6));
					mArrayList.add(staff);
					mCursor.moveToNext();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
		}
		return mArrayList;
	}

	public Staff getStaffByStaffCode(String staffCode) {
		String sql = " select staff_id, staff_code, name, shop_id, "
				+ " channel_type_id,price_policy, "
				+ " province, district, precinct, type,point_of_sale "
				+ " from staff where status = 1 and upper(staff_code) = ? ";
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
				staff.setProvince(c.getString(6));
				staff.setDistrict(c.getString(7));
				staff.setPrecinct(c.getString(8));
				staff.setType(c.getLong(9));
				staff.setPointOfSale(c.getLong(10));
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

	/**
	 * Lay danh sach nhan vien
	 * 
	 * @param channelTypeId
	 * @param searchParam
	 * @param start
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Staff> getStaffByChannel(Long channelTypeId,
                                              Long pointOfSale, String searchParam, int start, int count,
                                              String[] replace) {

        Double x = null;
        Double y = null;
		int counter = 0;
		ArrayList<Staff> result = new ArrayList<>();
		String sql = "select distinct staff_id, staff_code, "
				+ " name, shop_id,channel_type_id,x,y,point_of_sale, "
				+ " isdn_agent, birthday ,id_no, id_issue_place , id_issue_date , province, district , precinct ,address ,street_block,street,home from staff "
				+ " where status = 1 "
				+ " and (staff_owner_id = ? or (sub_owner_id = ? and sub_owner_type = 2))"
				+ " and staff_code <> ? COLLATE NOCASE ";
		List<String> param = new ArrayList<>();
		param.add(Session.loginUser.getStaffId() + "");
		param.add(Session.loginUser.getStaffCode());
		Cursor c = null;
		try {
			param.add(Session.userName);
			if (channelTypeId != null) {
				sql += " and channel_type_id = ? ";
				param.add(channelTypeId + "");
			}
			if (pointOfSale != null) {
				sql += " and point_of_sale = ? ";
				param.add(pointOfSale + "");
			}
			if (searchParam != null && !searchParam.trim().isEmpty()) {
				sql += " and (staff_code like ? COLLATE NOCASE or replace(name,?,?)   like ? COLLATE NOCASE"
						+ " or name_nosign like ? COLLATE NOCASE)";

				param.add("%" + searchParam + "%");
				if (searchParam.contains("%")) {
					searchParam.replace("%", "");
					param.add("%" + searchParam + "%");

				}
				param.add(replace[0]);
				param.add(replace[1]);
				param.add("%" + searchParam.toLowerCase() + "%");
				if (searchParam.contains("%")) {
					searchParam.replace("%", "");
					param.add("%" + searchParam.toLowerCase() + "%");

				}
				param.add("%" + searchParam.toLowerCase() + "%");
				if (searchParam.contains("%")) {
					searchParam.replace("%", "");
					param.add("%" + searchParam.toLowerCase() + "%");

				}

			}
			// if (x != null && y != null) {
			// // sql +=
			// " and x is not null and y is not null order by (x - ?)*(x-?) + (y- ?)*(y-?) ";
			// // param.add(x + "");
			// // param.add(x + "");
			// // param.add(y + "");
			// // param.add(y + "");
			// } else {
			sql += " order by lower(name) ";
			// }

			String[] strParam = param.toArray(new String[param.size()]);
			Log.d("sqlllllll", sql);
			c = database.rawQuery(sql, strParam);
			if (c == null) {
				return null;
			}

			if (c.moveToFirst()) {
				do {
					counter++;
					Staff staff = new Staff();
					staff.setStaffId(c.getLong(0));
					staff.setStaffCode(c.getString(1));
					staff.setName(c.getString(2));
					staff.setShopId(c.getLong(3));
					staff.setChannelTypeId(c.getLong(4));
					if (c.getString(5) != null && !c.getString(5).isEmpty()) {
						staff.setX(c.getString(5));
					}
					if (c.getString(6) != null && !c.getString(6).isEmpty()) {
						staff.setY(c.getString(6));
					}
					if (!c.isNull(7)) {
						staff.setPointOfSale(c.getLong(7));
					}
					if (!c.isNull(8)) {
						staff.setIsdnAgent(c.getString(8));
					}
					if (!c.isNull(9)) {
						staff.setBirthday(c.getString(9));
					}
					if (!c.isNull(10)) {
						staff.setId_no(c.getString(10));
					}
					// id_issue_place , id_issue_date , province, district ,
					// precinct
					if (!c.isNull(11)) {
						staff.setId_issue_place(c.getString(11));
					}
					if (!c.isNull(12)) {
						staff.setId_issue_date(c.getString(12));
					}
					if (!c.isNull(13)) {
						staff.setProvince(c.getString(13));
					}
					if (!c.isNull(14)) {
						staff.setDistrict(c.getString(14));
					}
					if (!c.isNull(15)) {
						staff.setPrecinct(c.getString(15));
					}
					if (!c.isNull(16)) {
						staff.setAddress(c.getString(16));
					}

					if (!c.isNull(17)) {
						staff.setStreet_block(c.getString(17));
					}
					if (!c.isNull(18)) {
						staff.setStreet(c.getString(18));
					}
					if (!c.isNull(19)) {
						staff.setHome(c.getString(19));
					}

					result.add(staff);

				} while (c.moveToNext() && counter < count);
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

	// Thuytv3_update staff_start
	public boolean updateStaff(
			com.viettel.bss.viettelpos.v4.login.object.Staff mSatff) {
		ContentValues values = new ContentValues();
		values.put(STAFF.KEY_NAME, mSatff.getNameStaff());
		values.put(STAFF.TEL, mSatff.getTel());
		values.put(STAFF.BUSINESS_METHOD, mSatff.getBusinessMethod());
		values.put(STAFF.CONTRACT_METHOD, mSatff.getContractMethod());
		values.put(STAFF.POINT_OF_SALE_TYPE, mSatff.getPointOfSaleType());
		values.put(STAFF.KEY_ADDRESS, mSatff.getAddressStaff());
		values.put(STAFF.PROVINCE, mSatff.getProvince());
		values.put(STAFF.PRECINCT, mSatff.getPrecinct());
		values.put(STAFF.DISTRICT, mSatff.getDistrict());
		values.put(STAFF.STREET, mSatff.getStreet());
		values.put(STAFF.STREETBLOCK, mSatff.getStreetBlock());
		values.put(STAFF.HOME, mSatff.getHome());
		values.put(STAFF.ID_ISSUE_PLACE, mSatff.getIdIssuePlace());		
		values.put(STAFF.KEY_ID_USER_NO_DATE, mSatff.getIdIssueDate());
		values.put(STAFF.KEY_ID_USER_NO, mSatff.getIdUser_no());

		int result = database.update("staff", values,
				" staff_id = " + mSatff.getStaffId(), null);
		return result > 0;
	}

	// Thuytv3_update staff_end

	/**
	 * Ham lay danh sach nhan vien quan ly + nhan vien theo nhan vien quan ly
	 * 
	 * @param staffCode
	 * @return
	 */
	public ArrayList<Staff> getLstStaffByStaffMngt(String staffCode) {
		String sql = " SELECT   STAFF_ID, " + " STAFF_CODE, " + " NAME, "
				+ " SHOP_ID, " + " CHANNEL_TYPE_ID, " + " PRICE_POLICY, "
				+ " PROVINCE, " + " DISTRICT, " + " PRECINCT, " + " TYPE "
				+ " FROM   STAFF " + " WHERE   STAFF_ID = "
				+ " (SELECT   STAFF_ID " + " FROM   STAFF "
				+ " WHERE       STATUS = 1 " + " AND UPPER (STAFF_CODE) = ?) ";

		Cursor c = null;
		ArrayList<Staff> result = new ArrayList<>();
		try {

			c = database.rawQuery(sql, new String[] { staffCode.toUpperCase() });
			if (c.moveToFirst()) {
				do {
					Staff staff = new Staff();
					staff.setStaffId(c.getLong(0));
					staff.setStaffCode(c.getString(1));
					staff.setName(c.getString(2));
					staff.setShopId(c.getLong(3));
					staff.setChannelTypeId(c.getLong(4));
					staff.setPricePolicy(c.getString(5));
					staff.setProvince(c.getString(6));
					staff.setDistrict(c.getString(7));
					staff.setPrecinct(c.getString(8));
					staff.setType(c.getLong(9));

					result.add(staff);

				} while (c.moveToNext());

			}

			sql = " SELECT   STAFF_ID, " + " STAFF_CODE, " + " NAME, "
					+ " SHOP_ID, " + " CHANNEL_TYPE_ID, " + " PRICE_POLICY, "
					+ " PROVINCE, " + " DISTRICT, " + " PRECINCT, " + " TYPE "
					+ " FROM   STAFF " + " WHERE   STAFF_OWNER_ID = "
					+ " (SELECT   STAFF_ID " + " FROM   STAFF "
					+ " WHERE       STATUS = 1 "
					+ " AND UPPER (STAFF_CODE) = ?) order by staff_code";

			c = database.rawQuery(sql, new String[] { staffCode.toUpperCase() });

			if (c.moveToFirst()) {
				do {
					Staff staff = new Staff();
					staff.setStaffId(c.getLong(0));
					staff.setStaffCode(c.getString(1));
					staff.setName(c.getString(2));
					staff.setShopId(c.getLong(3));
					staff.setChannelTypeId(c.getLong(4));
					staff.setPricePolicy(c.getString(5));
					staff.setProvince(c.getString(6));
					staff.setDistrict(c.getString(7));
					staff.setPrecinct(c.getString(8));
					staff.setType(c.getLong(9));

					result.add(staff);

				} while (c.moveToNext());
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (c != null) {
				c.close();
			}
		}

		return null;
	}

	public List<Staff> getStaffByStaffLocation(String x, String y) {
		List<Staff> result = new ArrayList<>();
		String sql = " select staff_id, staff_code, name, shop_id"
				+ " from staff where status = 1 and x = ? and y = ?";
		Cursor c = null;
		try {
			c = database.rawQuery(sql, new String[] { x, y });
			if (c.moveToFirst()) {
				do {
					Staff staff = new Staff();
					staff.setStaffId(c.getLong(0));
					staff.setStaffCode(c.getString(1));
					staff.setName(c.getString(2));
					staff.setShopId(c.getLong(3));
					
					result.add(staff);
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

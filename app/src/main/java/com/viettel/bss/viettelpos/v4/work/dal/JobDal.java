package com.viettel.bss.viettelpos.v4.work.dal;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Define.CollectedObjectInfoF;
import com.viettel.bss.viettelpos.v4.commons.Define.ObjectDetailGroupDF;
import com.viettel.bss.viettelpos.v4.commons.Define.STAFF;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.sale.dal.StaffDAL;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
import com.viettel.bss.viettelpos.v4.work.object.InfoJobNew;
import com.viettel.bss.viettelpos.v4.work.object.InfoNewManager;
import com.viettel.bss.viettelpos.v4.work.object.ItemDetailWork;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;
import com.viettel.bss.viettelpos.v4.work.object.SalePoint;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject;
import com.viettel.bss.viettelpos.v4.work.object.WorkObject;

public class JobDal {
	private SQLiteDatabase database;
	private final DBOpenHelper mDal;

    public JobDal(Context context) {
		mDal = new DBOpenHelper(context);
    }

	public void open() throws SQLException {
		database = mDal.getWritableDatabase();
	}

	public void close() {
		try {
			mDal.close();
			if (database != null) {
				database.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void truncateVSATable() {
		database.execSQL("delete from VSA");
	}

	// public void updateStatusCollectInfo(String TABLE_NAME, String id, String
	// parentId){
	// SQLiteDatabase db = mDal.getReadableDatabase();
	// Cursor mCursor = db
	// .query(TABLE_NAME,
	// rows,
	// " group_id = (select id from object_detail_group where parent_id = ? and is_key = 1) and area_code = ? ",
	// new String[] { parentId_, area_code }, null, null, null);
	//
	// }
	public ArrayList<SalePoint> getStaffJobManager(String TABLE_NAME,
			int first, int end) {
		int count = 0;
		ArrayList<SalePoint> mArrayList = null;
		Cursor mCursor = null;
		// String filter = Define.STAFF.KEY_STAFF_OWNER_ID + " = ?";
		// String[] value = new String[] { staff_id };
		SQLiteDatabase db = mDal.getReadableDatabase();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, new String[] { STAFF.KEY_NAME,
					STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
					STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
					STAFF.KEY_ID, STAFF.KEY_CODE }, null, null, null, null,
					null);
		if (mCursor != null) {
			if (first == 0)
				mCursor.moveToFirst();
			else
				mCursor.moveToPosition(first);
			mArrayList = new ArrayList<>();
			while (mCursor.moveToNext() && count < end) {
				String name = mCursor.getString(0);
				String address = mCursor.getString(1);
				String iduserno = mCursor.getString(2);
				String id_issue_date = mCursor.getString(3);
				String birthday = mCursor.getString(4);
				String staffid = mCursor.getString(5);

				count++;
			}
		}
		return mArrayList;

	}

	/*
	 * select * from collected_object_info where status = 1 group_id = (select
	 * id from object_detail_group where parent_id = ? and is_key = 1 ) and
	 * area_code =?
	 */
	public ArrayList<CollectedObjectInfo> getListCollectedInfo(
			String TABLE_NAME, String parentId_, String createUser_) {
		ArrayList<CollectedObjectInfo> result = new ArrayList<>();
		SQLiteDatabase db = mDal.getReadableDatabase();
		String[] rows = new String[] { CollectedObjectInfoF.ID,
				CollectedObjectInfoF.PARENT_ID, CollectedObjectInfoF.GROUP_ID,
				CollectedObjectInfoF.CREATE_TIME,
				CollectedObjectInfoF.UPDATE_TIME,
				CollectedObjectInfoF.CREATE_USER, CollectedObjectInfoF.VALUE,
				CollectedObjectInfoF.REPORT_CIRCLE,
				CollectedObjectInfoF.AREA_CODE, CollectedObjectInfoF.STATUS,
				CollectedObjectInfoF.YEAR_CIRCLE };
		// Cursor mCursor = db
		// .query(TABLE_NAME,
		// rows,
		// " status = 1 and group_id = (select id from object_detail_group where parent_id = ? and is_key = 1) and area_code = ? ",
		// new String[] { parentId_, area_code }, null, null, null);
		Cursor mCursor = db
				.query(TABLE_NAME,
						rows,
						" group_id = (select id from object_detail_group where parent_id = ? and is_key = 1) and upper(create_user) = ? and status = 1",
						new String[] { parentId_, createUser_.toUpperCase() },
						null, null, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id = mCursor.getString(0);
					String parentId = mCursor.getString(1);
					String groupId = mCursor.getString(2);
					String createTime = mCursor.getString(3);
					String updateTime = mCursor.getString(4);
					String createUser = mCursor.getString(5);
					String value = mCursor.getString(6);
					String reportCircle = mCursor.getString(7);
					String areaCode = mCursor.getString(8);
					String status = mCursor.getString(9);
					String yearCircle = mCursor.getString(10);
					CollectedObjectInfo cOif = new CollectedObjectInfo(id,
							parentId, groupId, createTime, updateTime,
							createUser, value, reportCircle, areaCode, status,
							yearCircle);
					result.add(cOif);
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		return result;
	}

	public ArrayList<CollectedObjectInfo> getListDetailsCollectedInfo(
			String TABLE_NAME, String parentId_, String createUser_) {
		ArrayList<CollectedObjectInfo> result = new ArrayList<>();
		SQLiteDatabase db = mDal.getReadableDatabase();
		String[] rows = new String[] { CollectedObjectInfoF.ID,
				CollectedObjectInfoF.PARENT_ID, CollectedObjectInfoF.GROUP_ID,
				CollectedObjectInfoF.CREATE_TIME,
				CollectedObjectInfoF.UPDATE_TIME,
				CollectedObjectInfoF.CREATE_USER, CollectedObjectInfoF.VALUE,
				CollectedObjectInfoF.REPORT_CIRCLE,
				CollectedObjectInfoF.AREA_CODE, CollectedObjectInfoF.STATUS,
				CollectedObjectInfoF.YEAR_CIRCLE };
		// Cursor mCursor = db
		// .query(TABLE_NAME,
		// rows,
		// " status = 1 and group_id = (select id from object_detail_group where parent_id = ? and is_key = 1) and area_code = ? ",
		// new String[] { parentId_, area_code }, null, null, null);
		Log.e("ds", "parentId_ " + parentId_ + " createUser_ = " + createUser_);
		Cursor mCursor = db
				.query(TABLE_NAME,
						rows,
						" (parent_id = ? or id = ? ) and upper(create_user) = ? and status = 1",
						new String[] { parentId_, parentId_,
								createUser_.toUpperCase() }, null, null, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String id = mCursor.getString(0);
					String parentId = mCursor.getString(1);
					String groupId = mCursor.getString(2);
					String createTime = mCursor.getString(3);
					String updateTime = mCursor.getString(4);
					String createUser = mCursor.getString(5);
					String value = mCursor.getString(6);
					String reportCircle = mCursor.getString(7);
					String areaCode = mCursor.getString(8);
					String status = mCursor.getString(9);
					String yearCircle = mCursor.getString(10);
					CollectedObjectInfo cOif = new CollectedObjectInfo(id,
							parentId, groupId, createTime, updateTime,
							createUser, value, reportCircle, areaCode, status,
							yearCircle);
					result.add(cOif);
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		return result;
	}

	/**
	 * Lay danh sach muc goc
	 * 
	 * @param TABLE_NAME
	 * @param first
	 * @param end
	 * @return
	 */
	public ArrayList<ObjectDetailGroup> getObjectGroupDetail(String TABLE_NAME,
			int first, int end) {
		int count = 0;
		ArrayList<SalePoint> mArrayList = null;
		Cursor mCursor = null;
		String[] rows = null;
		String whereClause = "method_type = 0 AND status = 1 AND (parent_id IS NULL OR parent_id = '')";

		ArrayList<ObjectDetailGroup> resultArr = new ArrayList<>();
		rows = new String[] { ObjectDetailGroupDF.ID, ObjectDetailGroupDF.CODE,
				ObjectDetailGroupDF.NAME, ObjectDetailGroupDF.ISKEY,
				ObjectDetailGroupDF.HAVE_O_INFO,
				ObjectDetailGroupDF.ITEM_ORDER, ObjectDetailGroupDF.PARENTID,
				ObjectDetailGroupDF.VALUETYPE,
				ObjectDetailGroupDF.HAVECHILDREND };
		SQLiteDatabase db = mDal.getReadableDatabase();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, rows, whereClause, null, null, null,
					null);
		if (mCursor != null) {
			mCursor.moveToFirst();
			do {
				String id = mCursor.getString(0);
				String code = mCursor.getString(1);
				String name = mCursor.getString(2);
				String isKey = mCursor.getString(3);
				String haveOinfo = mCursor.getString(4);
				String itemOrder = mCursor.getString(5);
				String parentId = mCursor.getString(6);
				String valueType = mCursor.getString(7);
				String haveChild = mCursor.getString(8);
				// CollectedObjectInfo mCollectedObjectInfo =
				// getObjectInfoCollector(
				// Define.collected_object_info, id, areaCode_);
				ObjectDetailGroup mObjectDetailGroup = new ObjectDetailGroup(
						name, code, id, haveOinfo, parentId, valueType,
						haveChild, isKey);
				// mObjectDetailGroup
				// .setmCollectedObjectInfo(mCollectedObjectInfo);
				resultArr.add(mObjectDetailGroup);
				count++;
			} while (mCursor.moveToNext());
		}
		return resultArr;

	}

	public ObjectDetailGroup getObjectGroupDetailById(String TABLE_NAME,
			String id_) {
		int count = 0;
		Cursor mCursor = null;
		String[] rows = null;
		String whereClause = "status = 1 and id = ?";
		String[] args = new String[] { id_ };
		ArrayList<ObjectDetailGroup> resultArr = new ArrayList<>();
		rows = new String[] { ObjectDetailGroupDF.ID, ObjectDetailGroupDF.CODE,
				ObjectDetailGroupDF.NAME, ObjectDetailGroupDF.ISKEY,
				ObjectDetailGroupDF.HAVE_O_INFO,
				ObjectDetailGroupDF.ITEM_ORDER, ObjectDetailGroupDF.PARENTID,
				ObjectDetailGroupDF.VALUETYPE,
				ObjectDetailGroupDF.HAVECHILDREND };
		SQLiteDatabase db = mDal.getReadableDatabase();
		resultArr.clear();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, rows, whereClause, args, null, null,
					"item_order");
		ObjectDetailGroup mObjectDetailGroup = null;
		if (mCursor.moveToFirst()) {
			String id = mCursor.getString(0);
			String code = mCursor.getString(1);
			String name = mCursor.getString(2);
			String isKey = mCursor.getString(3);
			String haveOinfo = mCursor.getString(4);
			String itemOrder = mCursor.getString(5);
			String parentId = mCursor.getString(6);
			String valueType = mCursor.getString(7);
			String haveChild = mCursor.getString(8);
			mObjectDetailGroup = new ObjectDetailGroup(name, code, id,
					haveOinfo, parentId, valueType, haveChild, isKey);
		}
		mCursor.close();
		return mObjectDetailGroup;

	}

	/**
	 * Lay truong con cua loai du lieu list
	 */

	public ObjectDetailGroup getObjectChildOfList(String TABLE_NAME,
			String parentId_) {

		int count = 0;
		Cursor mCursor = null;
		String[] rows = null;
		SQLiteDatabase db = null;
		ObjectDetailGroup mObjectDetailGroup = null;
		try {
			String whereClause = "status = 1 and parent_id = ?";
			String[] args = new String[] { parentId_ };
			ArrayList<ObjectDetailGroup> resultArr = new ArrayList<>();
			rows = new String[] { ObjectDetailGroupDF.ID,
					ObjectDetailGroupDF.CODE, ObjectDetailGroupDF.NAME,
					ObjectDetailGroupDF.ISKEY, ObjectDetailGroupDF.HAVE_O_INFO,
					ObjectDetailGroupDF.ITEM_ORDER,
					ObjectDetailGroupDF.PARENTID,
					ObjectDetailGroupDF.VALUETYPE,
					ObjectDetailGroupDF.HAVECHILDREND };
			db = mDal.getReadableDatabase();
			resultArr.clear();
			if (mCursor == null)
				mCursor = db.query(TABLE_NAME, rows, whereClause, args, null,
						null, "item_order");

			if (mCursor.moveToFirst()) {
				String id = mCursor.getString(0);
				String code = mCursor.getString(1);
				String name = mCursor.getString(2);
				String isKey = mCursor.getString(3);
				String haveOinfo = mCursor.getString(4);
				String itemOrder = mCursor.getString(5);
				String parentId = mCursor.getString(6);
				String valueType = mCursor.getString(7);
				String haveChild = mCursor.getString(8);
				mObjectDetailGroup = new ObjectDetailGroup(name, code, id,
						haveOinfo, parentId, valueType, haveChild, isKey);
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

		return mObjectDetailGroup;

	}

	/**
	 * 
	 * 
	 * Lay danh sach muc con
	 * 
	 * @param TABLE_NAME
	 * @return
	 */
	public ArrayList<ObjectDetailGroup> getObjectGroupChild(String TABLE_NAME,
			String getParentId, String createUser_, Boolean getValue) {
		int count = 0;
		ArrayList<SalePoint> mArrayList = null;
		Cursor mCursor = null;
		String[] rows = null;
		String whereClause = "status = 1 and parent_id = ?";
		String[] args = new String[] { getParentId };
		ArrayList<ObjectDetailGroup> resultArr = new ArrayList<>();
		rows = new String[] { ObjectDetailGroupDF.ID, ObjectDetailGroupDF.CODE,
				ObjectDetailGroupDF.NAME, ObjectDetailGroupDF.ISKEY,
				ObjectDetailGroupDF.HAVE_O_INFO,
				ObjectDetailGroupDF.ITEM_ORDER, ObjectDetailGroupDF.PARENTID,
				ObjectDetailGroupDF.VALUETYPE,
				ObjectDetailGroupDF.HAVECHILDREND };
		SQLiteDatabase db = mDal.getReadableDatabase();
		resultArr.clear();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, rows, whereClause, args, null, null,
					"item_order");
		Log.e("TAG", "SO PHAN TU : " + mCursor.getCount());
		if (mCursor.moveToFirst()) {
			do {
				String id = mCursor.getString(0);
				String code = mCursor.getString(1);
				String name = mCursor.getString(2);
				String isKey = mCursor.getString(3);
				String haveOinfo = mCursor.getString(4);
				String itemOrder = mCursor.getString(5);
				String parentId = mCursor.getString(6);
				String valueType = mCursor.getString(7);
				String haveChild = mCursor.getString(8);
				ObjectDetailGroup mObjectDetailGroup = new ObjectDetailGroup(
						name, code, id, haveOinfo, parentId, valueType,
						haveChild, isKey);
				if (getValue) {
					CollectedObjectInfo mCollectedObjectInfo = getValueObjectDetailGroup(
							Define.collected_object_info, id,
							createUser_.toUpperCase());
					if (mCollectedObjectInfo != null) {
						Log.e("TAG", "NAME = " + name + " value = "
								+ mCollectedObjectInfo.getValue());
						mObjectDetailGroup
								.setmCollectedObjectInfo(mCollectedObjectInfo);
					}
				}

				resultArr.add(mObjectDetailGroup);
				count++;
			} while (mCursor.moveToNext());
		}

		return resultArr;

	}

	public ArrayList<ObjectDetailGroup> getObjectGroupChildWithValue(
			String TABLE_NAME, String getParentId, String createUser_,
			String parentIdColletion) {
		int count = 0;
		ArrayList<SalePoint> mArrayList = null;
		Cursor mCursor = null;
		String[] rows = null;
		String whereClause = "status = 1 and parent_id = ?";
		String[] args = new String[] { getParentId };
		ArrayList<ObjectDetailGroup> resultArr = new ArrayList<>();
		rows = new String[] { ObjectDetailGroupDF.ID, ObjectDetailGroupDF.CODE,
				ObjectDetailGroupDF.NAME, ObjectDetailGroupDF.ISKEY,
				ObjectDetailGroupDF.HAVE_O_INFO,
				ObjectDetailGroupDF.ITEM_ORDER, ObjectDetailGroupDF.PARENTID,
				ObjectDetailGroupDF.VALUETYPE,
				ObjectDetailGroupDF.HAVECHILDREND };
		SQLiteDatabase db = mDal.getReadableDatabase();
		resultArr.clear();
		if (mCursor == null)
			mCursor = db.query(TABLE_NAME, rows, whereClause, args, null, null,
					"item_order");
		Log.e("TAG", "SO PHAN TU : " + mCursor.getCount());
		// if (mCursor != null) {
		// if (first == 0)
		// mCursor.moveToFirst();
		// else
		// mCursor.moveToPosition(first);
		if (mCursor.moveToFirst()) {
			do {
				String id = mCursor.getString(0);
				String code = mCursor.getString(1);
				String name = mCursor.getString(2);
				String isKey = mCursor.getString(3);
				String haveOinfo = mCursor.getString(4);
				String itemOrder = mCursor.getString(5);
				String parentId = mCursor.getString(6);
				String valueType = mCursor.getString(7);
				String haveChild = mCursor.getString(8);
				ObjectDetailGroup mObjectDetailGroup = new ObjectDetailGroup(
						name, code, id, haveOinfo, parentId, valueType,
						haveChild, isKey);
				CollectedObjectInfo mCollectedObjectInfo = getObjectInfoCollector(
						Define.collected_object_info, id, createUser_,
						parentIdColletion);
				mObjectDetailGroup
						.setmCollectedObjectInfo(mCollectedObjectInfo);
				resultArr.add(mObjectDetailGroup);
				count++;
			} while (mCursor.moveToNext());
			// } while (mCursor.moveToNext() && count < end);
		}

		return resultArr;

	}

	private CollectedObjectInfo getObjectInfoCollector(String TABLE_NAME,
                                                       String groupIdp, String createUser_, String parentIdColletion) {
		SQLiteDatabase db = mDal.getReadableDatabase();
		String[] rows = new String[] { CollectedObjectInfoF.ID,
				CollectedObjectInfoF.PARENT_ID, CollectedObjectInfoF.GROUP_ID,
				CollectedObjectInfoF.CREATE_TIME,
				CollectedObjectInfoF.UPDATE_TIME,
				CollectedObjectInfoF.CREATE_USER, CollectedObjectInfoF.VALUE,
				CollectedObjectInfoF.REPORT_CIRCLE,
				CollectedObjectInfoF.AREA_CODE, CollectedObjectInfoF.STATUS,
				CollectedObjectInfoF.YEAR_CIRCLE };

		Cursor mCursor = db
				.query(TABLE_NAME,
						rows,
						"  group_id = ? and upper(create_user) = ? and status = 1 and (parent_id = ? or id = ?)",
						new String[] { groupIdp, createUser_.toUpperCase(),
								parentIdColletion, parentIdColletion }, null,
						null, null);
		CollectedObjectInfo cOif = null;
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				String id = mCursor.getString(0);
				String parentId = mCursor.getString(1);
				String groupId = mCursor.getString(2);
				String createTime = mCursor.getString(3);
				String updateTime = mCursor.getString(4);
				String createUser = mCursor.getString(5);
				String value = mCursor.getString(6);
				String reportCircle = mCursor.getString(7);
				String areaCode = mCursor.getString(8);
				String status = mCursor.getString(9);
				String yearCircle = mCursor.getString(10);
				Log.e("TAG", "collected info = " + value);
				cOif = new CollectedObjectInfo(parentId, groupId, createTime,
						updateTime, createUser, value, reportCircle, areaCode,
						status, yearCircle);
			}
		}
		mCursor.close();
		return cOif;
	}

	private CollectedObjectInfo getValueObjectDetailGroup(String TABLE_NAME,
                                                          String groupIdp, String createUser_) {
		SQLiteDatabase db = mDal.getReadableDatabase();
		String[] rows = new String[] { CollectedObjectInfoF.ID,
				CollectedObjectInfoF.PARENT_ID, CollectedObjectInfoF.GROUP_ID,
				CollectedObjectInfoF.CREATE_TIME,
				CollectedObjectInfoF.UPDATE_TIME,
				CollectedObjectInfoF.CREATE_USER, CollectedObjectInfoF.VALUE,
				CollectedObjectInfoF.REPORT_CIRCLE,
				CollectedObjectInfoF.AREA_CODE, CollectedObjectInfoF.STATUS,
				CollectedObjectInfoF.YEAR_CIRCLE };

		Cursor mCursor = db.query(TABLE_NAME, rows,
				"  group_id = ? and upper(create_user) = ? and status = 1",
				new String[] { groupIdp, createUser_.toUpperCase() }, null,
				null, null);
		CollectedObjectInfo cOif = null;
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				String id = mCursor.getString(0);
				String parentId = mCursor.getString(1);
				String groupId = mCursor.getString(2);
				String createTime = mCursor.getString(3);
				String updateTime = mCursor.getString(4);
				String createUser = mCursor.getString(5);
				String value = mCursor.getString(6);
				String reportCircle = mCursor.getString(7);
				String areaCode = mCursor.getString(8);
				String status = mCursor.getString(9);
				String yearCircle = mCursor.getString(10);
				cOif = new CollectedObjectInfo(parentId, groupId, createTime,
						updateTime, createUser, value, reportCircle, areaCode,
						status, yearCircle);
			}
		}
		mCursor.close();
		return cOif;
	}

	public TaskObject getTaskJobById(String TABLE_NAME, String taskJobId) {
		SQLiteDatabase db = mDal.getReadableDatabase();
		Cursor mCursor = db.query(TABLE_NAME, null,
				" task_id = ? and status = 1", new String[] { taskJobId },
				null, null, null);
		TaskObject mTask = null;
		if (mCursor != null) {
			mCursor.moveToFirst();
			String taskId = mCursor.getString(0);
			String taskTypeId = mCursor.getString(1);
			String nameTask = mCursor.getString(2);
			String contentTask = mCursor.getString(3);
			String createDateTask = mCursor.getString(4);
			String imageTask = mCursor.getString(5);
			String videoTask = mCursor.getString(6);
			String statuTask = mCursor.getString(7);
			mTask = new TaskObject(taskId, taskTypeId, nameTask, contentTask,
					createDateTask, imageTask, videoTask, statuTask);
		}
		mCursor.close();
		return mTask;
	}

	public ArrayList<TaskObject> getTaskJobManager(String TABLE_NAME) {
		ArrayList<TaskObject> mArrayList = null;
		SQLiteDatabase db = mDal.getReadableDatabase();
		Cursor mCursor = db.query(TABLE_NAME, null, " status = 1 ", null, null,
				null, null);
		// String filter = Define.STAFF.KEY_STAFF_OWNER_ID + " = ?";
		// String[] value = new String[] { staff_id };
		if (mCursor != null) {

			mArrayList = new ArrayList<>();
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				String taskId = mCursor.getString(0);
				String taskTypeId = mCursor.getString(1);
				String nameTask = mCursor.getString(2);
				String contentTask = mCursor.getString(3);
				String createDateTask = mCursor.getString(4);
				String imageTask = mCursor.getString(5);
				String videoTask = mCursor.getString(6);
				String statuTask = mCursor.getString(7);
				TaskObject mTask = new TaskObject(taskId, taskTypeId, nameTask,
						contentTask, createDateTask, imageTask, videoTask,
						statuTask);
				mArrayList.add(mTask);
				mCursor.moveToNext();
			}
		}
		mCursor.close();
		return mArrayList;

	}

	public ArrayList<Staff> getListStaffManager(String TABLE_NAME, int first,
			int end, Long staffType, String inputSearch) {
		ArrayList<Staff> mArrayList = new ArrayList<>();

		try {

			Cursor mCursor = null;

			String selectCon = "";

			String[] whereArgs = null;
			// Long ownerId =
			String[] rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
					STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
					STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
					STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
					STAFF.KEY_CHANNEL_TYPE_ID };
			Long staffID = Session.loginUser.getStaffId();
			String userName = Session.loginUser.getStaffCode();

			if (userName != null && userName != "") {
				selectCon += " staff_code <> ? COLLATE NOCASE ";
				whereArgs = new String[] { userName };
			}

			selectCon += " and staff_owner_id = ? and channel_type_id = ? and status = 1";

			whereArgs = new String[] { userName, staffID.toString(),
					staffType.toString() };
			SQLiteDatabase db = mDal.getReadableDatabase();
			if (inputSearch != null && inputSearch != ""
					&& !inputSearch.isEmpty() && userName != null) {
				selectCon += " and ( name like ? COLLATE NOCASE  or staff_code like ? COLLATE NOCASE) ";

				whereArgs = new String[] { userName, staffID.toString(),
						staffType.toString(), "%" + inputSearch + "%",
						"%" + inputSearch + "%" };
				if (inputSearch.contains("%")) {
					inputSearch.replace("%", "\\%");
					whereArgs = new String[] { userName, staffID.toString(),
							staffType.toString(),
							"%" + inputSearch + "%" + " ESCAPE \\",
							"%" + inputSearch + "%" + " ESCAPE \\" };
				}

			}
			Log.e("TAG", " ownerId: " + staffID.toString());
			Log.e("TAG", " staffType: " + staffType.toString());
			Log.e("TAG", " userName: " + userName);
			Log.i("TAG", " selectCon: " + selectCon);
			Log.i("TAG", " inputSearch: " + inputSearch);
			Log.i("TAG", " staffType: " + staffType);

			mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs,
					null, null, null);
			Log.e("TAG", "So ban ghi mCursor.getCount()" + mCursor.getCount());

			mArrayList.clear();
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

						Staff mTask = new Staff(staffid, name, address, "",
								iduserno, id_issue_date, birthday, staff_code,
								channelTypeId, x, y

						);

						mArrayList.add(mTask);
					} while (mCursor.moveToNext());

				}

			}
			mCursor.close();
			return mArrayList;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return null;
		}
	}

	public ArrayList<SalePoint> getListStaffOwner(String TABLE_NAME,
			Long staffTypeSearch, Long ownerId, String inputSearch, int first,
			int end) {

		ArrayList<SalePoint> mArrayList = new ArrayList<>();

		Cursor mCursor = null;

		String selectCon = "";

		String[] whereArgs = null;
		String userName = Session.loginUser.getStaffCode();
		Log.e("LOG", "first = " + first + " end  = " + end);
		Log.e("TAG", "userName: " + userName);
		if (userName != null && userName != "") {
			selectCon += " staff_code <> ?  COLLATE NOCASE";
			whereArgs = new String[] { userName };
		}
		selectCon += " and staff_owner_id = ? and status = 1";
		whereArgs = new String[] { userName, ownerId.toString() };
		String[] rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
				STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
				STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
				STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
				STAFF.KEY_CHANNEL_TYPE_ID };

		SQLiteDatabase db = mDal.getReadableDatabase();

		if (staffTypeSearch == 0) {

			if (inputSearch != null && inputSearch != ""
					&& !inputSearch.isEmpty() && userName != null) {
				selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";
				whereArgs = new String[] { userName, ownerId.toString(),
						"%" + inputSearch + "%", "%" + inputSearch + "%" };

				if (inputSearch.contains("%")) {
					inputSearch.replace("%", "\\%");
					whereArgs = new String[] { userName, ownerId.toString(),
							"%" + inputSearch + "%" + " ESCAPE \\",
							"%" + inputSearch + "%" + " ESCAPE \\" };

				}

			}
		} else {
			selectCon += " and channel_type_id = "
					+ Long.toString(staffTypeSearch);

			whereArgs = new String[] { userName, ownerId.toString(),
					"%" + inputSearch + "%", "%" + inputSearch + "%" };
			if (inputSearch.contains("%")) {
				inputSearch.replace("%", "\\%");
				whereArgs = new String[] { userName, ownerId.toString(),
						"%" + inputSearch + "%" + " ESCAPE \\",
						"%" + inputSearch + "%" + " ESCAPE \\" };

			}
		}

		Log.i("TAG", " selectCon: " + selectCon);
		Log.i("TAG", " inputSearch: " + inputSearch);
		Log.i("TAG", " staffType: " + staffTypeSearch);

		mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs, null,
				null, null);
		Log.e("TAG", " Tong so phan tu : " + mCursor.getCount());
		if (mCursor.getCount() <= end) {
			end = mCursor.getCount();
		}

		if (mCursor != null && mCursor.getCount() > 0) {
			int count = 0;
			if (mCursor.moveToPosition(first)) {
				do {
					Long staffId = mCursor.getLong(0);
					String name = mCursor.getString(1);
					String address = mCursor.getString(2);
					String iduserno = mCursor.getString(3);
					String id_issue_date = mCursor.getString(4);
					String birthday = mCursor.getString(5);
					String staffCode = mCursor.getString(6);
					float x = mCursor.getFloat(7);
					float y = mCursor.getFloat(8);
					Long channelTypeId = mCursor.getLong(9);

					SalePoint mTask = new SalePoint(name, staffId, staffCode,
							address, x, y);
					// SalePoint mTask = new SalePoint(name, staffId, staffCode,
					// address);

					mArrayList.add(mTask);
					count++;
				} while (mCursor.moveToNext() && count < end);

			}

		}

		return mArrayList;

	}

	public ArrayList<SalePoint> getListAllStaffOwner(String TABLE_NAME,
			Long staffTypeSearch, Long ownerId, String inputSearch) {

		ArrayList<SalePoint> mArrayList = new ArrayList<>();

		Cursor mCursor = null;

		String selectCon = "";

		String[] whereArgs = null;
		String userName = Session.loginUser.getStaffCode();
		Log.e("TAG", "userName: " + userName);
		if (userName != null && userName != "") {
			selectCon += " staff_code <> ?  COLLATE NOCASE";
			whereArgs = new String[] { userName };
		}
		selectCon += " and staff_owner_id = ? and status = 1";
		whereArgs = new String[] { userName, ownerId.toString() };
		String[] rowquery = new String[] { STAFF.KEY_ID, STAFF.KEY_NAME,
				STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
				STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
				STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
				STAFF.KEY_CHANNEL_TYPE_ID };

		SQLiteDatabase db = mDal.getReadableDatabase();

		if (staffTypeSearch == 0) {

			if (inputSearch != null && inputSearch != ""
					&& !inputSearch.isEmpty() && userName != null) {
				selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";

				whereArgs = new String[] { userName, ownerId.toString(),
						"%" + inputSearch + "%", "%" + inputSearch + "%" };

			}
		} else {
			selectCon += " and channel_type_id = "
					+ Long.toString(staffTypeSearch);

			if (inputSearch != null && inputSearch != "") {
				selectCon += " and ( name like ? COLLATE NOCASE or staff_code like ? COLLATE NOCASE) ";
				whereArgs = new String[] { userName, ownerId.toString(),
						"%" + inputSearch + "%", "%" + inputSearch + "%" };

			}
		}

		Log.i("TAG", " selectCon: " + selectCon);
		Log.i("TAG", " inputSearch: " + inputSearch);
		Log.i("TAG", " staffType: " + staffTypeSearch);

		mCursor = db.query(TABLE_NAME, rowquery, selectCon, whereArgs, null,
				null, null);
		Log.i("TAG", " Tong so phan tu : " + mCursor.getCount());

		if (mCursor != null && mCursor.getCount() > 0) {
			int count = 0;
			if (mCursor.moveToFirst()) {
				do {
					Long staffId = mCursor.getLong(0);
					String name = mCursor.getString(1);
					String address = mCursor.getString(2);
					String iduserno = mCursor.getString(3);
					String id_issue_date = mCursor.getString(4);
					String birthday = mCursor.getString(5);
					String staffCode = mCursor.getString(6);
					float x = mCursor.getFloat(7);
					float y = mCursor.getFloat(8);
					Long channelTypeId = mCursor.getLong(9);

					SalePoint mTask = new SalePoint(name, staffId, staffCode,
							address, x, y);
					// SalePoint mTask = new SalePoint(name, staffId, staffCode,
					// address);

					mArrayList.add(mTask);
					count++;
				} while (mCursor.moveToNext());

			}

		}

		return mArrayList;

	}

	public ArrayList<WorkObject> getListJob(String startdate, String enddate) {
		ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();

        String sql = "SELECT ta.name, tr.task_road_id, tr.object_id, tr.object_name, tr.address,tr.schedule_date_from, tr.schedule_date_to,ta.content" +
                " FROM task ta, task_road tr" +
                " WHERE ta.task_id = tr.task_id" +
                " AND date(tr.created_date) >= date(?)" +
                " AND date(tr.created_date) < date(?)" +
                " AND tr.progress = 1 AND tr.status = 1" +
                " AND ta.task_type_id != 10" +
                " order by tr.schedule_date_from";
        java.util.Date fromDate = DateTimeUtils.convertStringToTime(startdate,
				"dd/MM/yyyy");
		java.util.Date toDate = DateTimeUtils.convertStringToTime(enddate,
				"dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.DATE, 1);

		Cursor cursor = database.rawQuery(
                sql,
				new String[] {
						DateTimeUtils.convertDateTimeToString(fromDate,
								"yyyy-MM-dd"),
						DateTimeUtils.convertDateTimeToString(cal.getTime(),
								"yyyy-MM-dd") });

		if (cursor.moveToFirst()) {
			do {
				WorkObject workObject = new WorkObject();
				String namework = cursor.getString(0);
				Log.d("namework", namework);
				workObject.setNameWork(namework);
				String workid = cursor.getString(1);
				Log.d("workid", workid);
				workObject.setWorkid(workid);
				String madiemden = cursor.getString(2);
				Log.d("madiemden", madiemden);
				workObject.setPointToId(madiemden);
				String tendiemden = cursor.getString(3);
				workObject.setNamePointTo(tendiemden);
				String adress = cursor.getString(4);
				workObject.setAddress(adress);
				String schedule_date_from = cursor.getString(5);
				workObject.setStartDate(schedule_date_from);
				String schedule_date_to = cursor.getString(6);
				workObject.setEndDate(schedule_date_to);
				String content = cursor.getString(7);
				workObject.setContent(content);
				// String sX = cursor.getString(8);
				// Log.d("x", sX);
				// workObject.setX(Long.parseLong(sX));
				// String sY = cursor.getString(9);
				// Log.d("y", sY);
				// workObject.setY(Long.parseLong(sY));
				lisWorkObjects.add(workObject);
			} while (cursor.moveToNext());
		}

		return lisWorkObjects;
	}

	public void updateTaskRoad(String task_road_id, String progress) {
        Cursor cursor = database.rawQuery("update task_road set progress=?, real_finished_date = date('now') where task_road_id = ?", new String[] {
				progress, task_road_id });
		if (cursor.moveToFirst()) {
			do {
				Log.d("sucess", "sucess");
			} while (cursor.moveToNext());
		}
	}

	// ====get count thong tin cong viec cap nhat
	public int getcountJob() {
		int count = 0;

        String sql = "SELECT count(1)" +
                " FROM task ta, task_road tr" +
                " WHERE ta.task_id = tr.task_id" +
                " AND tr.progress = 1 AND tr.status = 1 AND ta.task_type_id != 10;";

        Cursor cursor = database.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				String countitem = cursor.getString(0);
				Log.d("countitem", countitem);
				count = Integer.parseInt(countitem);
			} while (cursor.moveToNext());
		}

		return count;
	}

	// get total ,backlog,term
	public InfoJobNew getListCountJob() {
		InfoJobNew infoJobNew = new InfoJobNew();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT t1.total, t2.backlog, t3.term");
		sql.append(" FROM  (SELECT COUNT ( * ) total");
		sql.append(" FROM  task ta, task_road tr");
		sql.append(" WHERE  ta.task_id = tr.task_id");
		sql.append(" AND tr.progress = 1");
		sql.append(" AND tr.status = 1");
		sql.append(" AND (ta.task_type_id IS NULL OR ta.task_type_id != 10)) t1,");
		sql.append(" (SELECT COUNT ( * ) backlog");
		sql.append(" FROM  task ta, task_road tr");
		sql.append(" WHERE ta.task_id = tr.task_id");
		sql.append(" AND date(tr.schedule_date_to) <= date ('now')");
		sql.append(" AND tr.progress = 1");
		sql.append(" AND tr.status = 1");
		sql.append(" AND (ta.task_type_id IS NULL OR ta.task_type_id != 10)) t2,");
		sql.append(" (SELECT COUNT (*) term");
		sql.append(" FROM task ta, task_road tr");
		sql.append(" WHERE   ta.task_id = tr.task_id");
		sql.append(" AND date (tr.schedule_date_to) = date ('now', '+1 day')");
		sql.append(" AND tr.progress = 1");
		sql.append(" AND tr.status = 1");
		sql.append(" AND (ta.task_type_id IS NULL OR ta.task_type_id != 10)) t3");
		Log.d("sql", sql.toString());
		Cursor cursor = database.rawQuery(sql.toString(), null);
		if (cursor.moveToFirst()) {
			do {
				String total = cursor.getString(0);
				Log.d("total", total);
				infoJobNew.setTotal(total);
				String backlog = cursor.getString(1);
				Log.d("backlog", backlog);
				infoJobNew.setBacklog(backlog);
				String term = cursor.getString(2);
				Log.d("term", term);
				infoJobNew.setTerm(term);
			} while (cursor.moveToNext());
		}

		return infoJobNew;
	}

	public ArrayList<WorkObject> getListPonitJob(String startdate,
			String enddate) {
		ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();

        String sql = "SELECT ta.name, tr.task_road_id, tr.object_id, tr.object_name, tr.address,tr.schedule_date_from, tr.schedule_date_to,ta.content,tr.x,tr.y" +
                " FROM task ta, task_road tr" +
                " WHERE ta.task_id = tr.task_id" +
                " AND date(tr.created_date) >= date(?)" +
                " AND date(tr.created_date) < date(?)" +
                " AND tr.progress = 1 AND tr.status = 1" +
                " AND ta.task_type_id != 10" +
                " order by tr.schedule_date_to";
        java.util.Date fromDate = DateTimeUtils.convertStringToTime(startdate,
				"dd/MM/yyyy");
		java.util.Date toDate = DateTimeUtils.convertStringToTime(enddate,
				"dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(toDate);
		cal.add(Calendar.DATE, 1);

		Cursor cursor = database.rawQuery(
                sql,
				new String[] {
						DateTimeUtils.convertDateTimeToString(fromDate,
								"yyyy-MM-dd"),
						DateTimeUtils.convertDateTimeToString(cal.getTime(),
								"yyyy-MM-dd") });

		if (cursor.moveToFirst()) {
			do {
				WorkObject workObject = new WorkObject();
				String namework = cursor.getString(0);
				Log.d("namework", namework);
				workObject.setNameWork(namework);
				String workid = cursor.getString(1);
				Log.d("workid", workid);
				workObject.setWorkid(workid);
				String madiemden = cursor.getString(2);
				Log.d("madiemden", madiemden);
				workObject.setPointToId(madiemden);
				String tendiemden = cursor.getString(3);
				workObject.setNamePointTo(tendiemden);
				String adress = cursor.getString(4);
				workObject.setAddress(adress);
				String schedule_date_from = cursor.getString(5);
				workObject.setStartDate(schedule_date_from);
				String schedule_date_to = cursor.getString(6);
				workObject.setEndDate(schedule_date_to);
				String content = cursor.getString(7);
				workObject.setContent(content);
				String sX = cursor.getString(8);
				Log.d("x", sX);
				workObject.setX(Double.parseDouble(sX));
				String sY = cursor.getString(9);
				Log.d("y", sY);
				workObject.setY(Double.parseDouble(sY));
				lisWorkObjects.add(workObject);
			} while (cursor.moveToNext());
		}

		return lisWorkObjects;
	}

	public ArrayList<WorkObject> getListAllJob() {
		ArrayList<WorkObject> lisWorkObjects = new ArrayList<>();

        String sql = "SELECT ta.name, tr.task_road_id, tr.object_id, tr.object_name, tr.address,tr.schedule_date_from, tr.schedule_date_to,ta.content,tr.x,tr.y" +
                " FROM task ta, task_road tr" +
                " WHERE ta.task_id = tr.task_id" +
                " AND tr.progress = 1 AND tr.status = 1" +
                " AND ta.task_type_id != 10" +
                " order by tr.schedule_date_to";
        // sql.append(" AND date(tr.created_date) >= date(?)");
		// sql.append(" AND date(tr.created_date) < date(?)");
        // java.util.Date fromDate =
		// DateTimeUtils.convertStringToTime(startdate,
		// "dd/MM/yyyy");
		// java.util.Date toDate = DateTimeUtils.convertStringToTime(enddate,
		// "dd/MM/yyyy");
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(toDate);
		// cal.add(Calendar.DATE, 1);

		Cursor cursor = database.rawQuery(sql, null);

		if (cursor.moveToFirst()) {
			do {
				WorkObject workObject = new WorkObject();
				String namework = cursor.getString(0);
				Log.d("namework", namework);
				workObject.setNameWork(namework);
				String workid = cursor.getString(1);
				Log.d("workid", workid);
				workObject.setWorkid(workid);
				String madiemden = cursor.getString(2);
				Log.d("madiemden", madiemden);
				workObject.setPointToId(madiemden);
				String tendiemden = cursor.getString(3);
				workObject.setNamePointTo(tendiemden);
				String adress = cursor.getString(4);
				workObject.setAddress(adress);
				String schedule_date_from = cursor.getString(5);
				workObject.setStartDate(schedule_date_from);
				String schedule_date_to = cursor.getString(6);
				workObject.setEndDate(schedule_date_to);
				String content = cursor.getString(7);
				workObject.setContent(content);
				String sX = cursor.getString(8);
				Log.d("x", sX);
				workObject.setX(Double.parseDouble(sX));
				String sY = cursor.getString(9);
				Log.d("y", sY);
				workObject.setY(Double.parseDouble(sY));
				lisWorkObjects.add(workObject);
			} while (cursor.moveToNext());
		}

		return lisWorkObjects;
	}

	// get allTask,FailTask,WarningTask
	public InfoNewManager getListNotifyManager() {
		StaffDAL staffDAL = null;
		InfoNewManager infoNewManager = new InfoNewManager();
		Cursor cursor = null;
		try {
			staffDAL = new StaffDAL(database);
			com.viettel.bss.viettelpos.v4.sale.object.Staff staff = staffDAL
					.getStaffByStaffCode(Session.userName);
			Log.d("staffId", staff.getStaffId() + "");

			StringBuilder sql = new StringBuilder();
			sql.append("select * from (");
			sql.append("(SELECT count(1) as all_task FROM task_road a, task b ");
			sql.append(" WHERE 1 =1 and a.task_id = b.task_id ");
			sql.append(" AND (b.task_type_id is null or  b.task_type_id = '' or b.task_type_id != 10)");
			sql.append(" AND a.progress = 1 and a.status = 1 and a.manage_staff_id =?) ,");
			sql.append(" (SELECT count(1) as fail_task FROM task_road a, task b ");
			sql.append(" WHERE 1 =1 and a.task_id = b.task_id");
			sql.append(" AND (b.task_type_id is null or b.task_type_id = '' or b.task_type_id != 10)");
			sql.append(" AND a.progress = 1 and a.status = 1 and a.manage_staff_id =?");
			sql.append(" AND date(schedule_date_to)< date('now')),");
			sql.append(" (SELECT count(1) as warning_task from task_road a, task b");
			sql.append(" WHERE 1 =1 and a.task_id = b.task_id");
			sql.append(" AND (b.task_type_id is null or b.task_type_id = '' or b.task_type_id != 10)");
			sql.append(" AND a.progress = 1 and a.status = 1 and a.manage_staff_id =?");
			sql.append(" AND (date(schedule_date_to)= date('now') or date(schedule_date_to,'+1 day')= date('now'))))");
			Log.d("sql", sql.toString());
			cursor = database.rawQuery(sql.toString(),
					new String[] { staff.getStaffId() + "",
							staff.getStaffId() + "", staff.getStaffId() + "" });
			if (cursor == null) {
				return infoNewManager;
			}
			if (cursor.moveToFirst()) {
				do {
					String allTask = cursor.getString(0);
					Log.d("allTask", allTask);
					infoNewManager.setAllTask(allTask);
					String failTask = cursor.getString(1);
					Log.d("failTask", failTask);
					infoNewManager.setFailTask(failTask);
					String warningTask = cursor.getString(2);
					Log.d("warningTask", warningTask);
					infoNewManager.setWarningTask(warningTask);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			Log.e(Constant.TAG, "JobDal getListNotifyManager " + e.toString(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return infoNewManager;
	}

	// get list detail info work
	public ArrayList<ItemDetailWork> getListItemDetail() {
		ArrayList<ItemDetailWork> lisDetailWorks = new ArrayList<>();
        String sql = "SELECT   * FROM   (SELECT   st.staff_id, st.staff_code, st.name," +
                " ifnull (  (  SELECT   COUNT ( * )" +
                " FROM   task ta, task_road tr" +
                "  WHERE       ta.task_id = tr.task_id" +
                "   AND tr.staff_id = st.staff_id" +
                "  AND tr.progress = 1" +
                "  AND tr.status = 1" +
                "  AND (ta.task_type_id IS NULL" +
                "  OR ta.task_type_id != 10)" +
                "  GROUP BY   tr.staff_id)," +
                "  0) total," +
                "  ifnull ( (  SELECT   COUNT ( * )" +
                "  FROM   task ta, task_road tr" +
                " WHERE   ta.task_id = tr.task_id" +
                "   AND tr.staff_id = st.staff_id" +
                "   AND date(tr.schedule_date_to) < date ('now')" +
                "  AND tr.progress = 1" +
                " AND tr.status = 1" +
                " AND (ta.task_type_id IS NULL" +
                "  OR ta.task_type_id != 10)" +
                " GROUP BY   tr.staff_id), 0) backlog, " +
                "    ifnull ( (  SELECT   COUNT ( * )" +
                " FROM   task ta, task_road tr" +
                "  WHERE   ta.task_id = tr.task_id" +
                " AND tr.staff_id = st.staff_id" +
                "  AND date (tr.schedule_date_to) =" +
                " date ('now', '-1 day')" +
                "  AND tr.progress = 1" +
                " AND tr.status = 1" +
                " AND (ta.task_type_id IS NULL" +
                " OR ta.task_type_id != 10)" +
                " GROUP BY   tr.staff_id), 0)  term FROM   staff st)" +
                " WHERE   total + backlog + term > 0";
        Cursor cursor = database.rawQuery(sql, null);
		if (cursor == null) {
			return lisDetailWorks;
		}
		if (cursor.moveToFirst()) {

			do {
				ItemDetailWork itemDetailWork = new ItemDetailWork();
				String satffId = cursor.getString(0);
				Log.d("satffId", satffId);
				itemDetailWork.setStaffId(satffId);
				String staffcode = cursor.getString(1);
				Log.d("staffcode", staffcode);
				itemDetailWork.setStaffCode(staffcode);
				String name = cursor.getString(2);
				Log.d("name", name);
				itemDetailWork.setName(name);
				String total = cursor.getString(3);
				Log.d("total", total);
				itemDetailWork.setTotal(Integer.parseInt(total));
				String backlog = cursor.getString(4);
				itemDetailWork.setBackLog(Integer.parseInt(backlog));
				String term = cursor.getString(5);
				itemDetailWork.setTerm(Integer.parseInt(term));
				lisDetailWorks.add(itemDetailWork);
			} while (cursor.moveToNext());
		}
		return lisDetailWorks;
	}

}

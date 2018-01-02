package com.viettel.bss.viettelpos.v4.work.dal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Define.STAFF;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.work.object.ObjectCount;
import com.viettel.bss.viettelpos.v4.work.object.TaskObject_;

//import com.viettel.bss.viettelpos.v4.sale.object.Staff;

public class DalPolicy {
	private SQLiteDatabase database;
	private final DBOpenHelper dal;
	private final Context context;

	public DalPolicy(Context context) {
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

	public Staff getStaffByUsername(String TABLE_NAME, String staffCode) {
		// public Staff getStaffByStaffCode(String TABLE_NAME, String staffCode,
		// Location myLocation) {
		String[] rowquery = new String[]{STAFF.KEY_ID, STAFF.KEY_NAME,
				STAFF.KEY_ADDRESS, STAFF.KEY_ID_USER_NO,
				STAFF.KEY_ID_USER_NO_DATE, STAFF.KEY_ID_USER_BIRTHDAY,
				STAFF.KEY_CODE, STAFF.KEY_LOCATION_X, STAFF.KEY_LOCATION_Y,
				STAFF.KEY_CHANNEL_TYPE_ID};
		Staff mTask = new Staff();
		Cursor mCursor = null;
		String selectCon = " staff_code = ? ";
		String[] whereArgs = new String[]{staffCode};

		// SQLiteDatabase db = channelDAL.getReadableDatabase();
		mCursor = database.query(TABLE_NAME, rowquery, selectCon, whereArgs,
				null, null, null);
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

					Location locationStaff = new Location("location Satff");
					locationStaff.setLatitude(x);
					locationStaff.setLongitude(y);
					float distance = 0;
					// distance = myLocation.distanceTo(locationStaff);

					mTask = new Staff(staffid, name, address, "", iduserno,
							id_issue_date, birthday, staff_code, distance,
							channelTypeId, x, y

					);

				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mTask;
	}

	private Calendar cal;
	int colorId;
	private final long oneDayInMillSec = 86400 * 1000;

	public static String getDate(long milliSeconds, String dateFormat) {
		// Create a DateFormatter object for displaying date in specified
		// format.
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

		// Create a calendar object that will convert the date and time value in
		// milliseconds to date.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}

	public boolean updateTaskRoadProgress(String task_road_id, String staff_id,
			int progress) {
		int countUpdated = 0;
		// TODO Auto-generated method stub
		long tm = System.currentTimeMillis() + 3 * 60 * 1000;
		String newTime = getDate(tm,
				LoginActivity.getInstance()
						.getString(R.string.format_date_time)
		// "dd/MM/yyyy HH:mm:ss"
		);
		String tmp[] = newTime.split(" ");
		String myDate = tmp[0];
		String myTime = tmp[1];
		ContentValues updateValues = new ContentValues();
		updateValues.put("real_finished_date", myDate);
		// updateValues.put("tm", myTime);
		updateValues.put("progress", progress);
		countUpdated = database.update(Define.TABLE_NAME_TASK_ROAD,
				updateValues, "task_road_id = ? and object_id = ?",
				new String[]{task_road_id + "", staff_id});
		Log.e(tag, countUpdated + "...update");

		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return countUpdated == 1;
		// return arrTaskObjects;
	}

	// String table = "task t, task_road tr, staff st";
	// String[] rowquery = new String[]{"st.staff_id", "st.name",
	// "st.address", "st.id_no", "st.id_issue_date", "st.birthday",
	// "st.staff_code", "st.x", "st.y", "st.channel_type_id", distan,
	// "st.tel", "st.isdn_agent"};
    private final String tag = "dal policy";
	private int numOfDateLate_notDone = 0;
	public ObjectCount getcountPolicy_() {
		cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		Date dateNow = new Date(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DATE));
		int count = 0;
        String sql = "Select tr.schedule_date_to" +
                " from task t, task_road tr, staff st" +
                " where t.task_id = tr.task_id " + "and t.task_type_id = 10 and tr.progress = 1" + " and tr.status = 1 and tr.object_id =  st.staff_id and tr.staff_id = " + Session.loginUser.getStaffId();

        Cursor cursor = database.rawQuery(sql, null);
		count = cursor.getCount();
		int sub = 0;
		int subToiHan = 0;

		int j = 0;
		if (cursor.moveToFirst()) {
			do {
				String scheDateToText = cursor.getString(j);
				String arrScheDateTo[] = scheDateToText.split("-");
				int year = Integer.valueOf(arrScheDateTo[0]);
				int month = Integer.valueOf(arrScheDateTo[1]);
				int date = Integer.valueOf(arrScheDateTo[2]);

				Date dateScheTo = new Date(year, month, date);

				numOfDateLate_notDone = (int) ((dateNow.getTime() - dateScheTo
						.getTime()) / oneDayInMillSec);
				if (numOfDateLate_notDone < Constant.DATE_REMAIN) {
				} else if (numOfDateLate_notDone <= 0) {
					subToiHan++;
				} else {
					sub++;
				}
				String countitem = cursor.getString(0);
				Log.d("countitem", countitem);
				// count = Integer.parseInt(countitem);
			} while (cursor.moveToNext());
		}
		return new ObjectCount(count, sub, subToiHan);
	}

	public ArrayList<TaskObject_> getArrTask(int taskTypeId, String staffId,
			int mProgress) {
		ArrayList<TaskObject_> arrTaskObjects = new ArrayList<>();
		// TODO Auto-generated method stub
		String[] rowquery = new String[]{"t.task_id", "t.task_type_id",
				"t.name", "t.content", "t.image_attachment",
				"t.video_attachment", "tr.task_road_id", "tr.progress",
				"tr.schedule_date_to", "tr.real_finished_date", "tr.x", "tr.y"};
		// rowquery = null;
		String table = "task t, task_road tr";
		// table = "task t";
		// table = "task_road tr";
		String selectCon = null;
		selectCon = "t.task_id = tr.task_id and t.task_type_id = ? and tr.object_id = ? "
				+ "and t.status = 1 and tr.status = 1 and tr.staff_id = "
				+ Session.loginUser.getStaffId();
		String whereArgs[] = null;
		whereArgs = new String[]{taskTypeId + "", staffId + ""};
		// whereArgs = new String[]{"10", "13390"};
		// whereArgs = new String[]{"10", "21042"};
		Cursor mCursor = database.query(table, rowquery, selectCon, whereArgs,
				null, null, "tr.schedule_date_to");
		// database.query(table, columns, selection,
		// selectionArgs, groupBy, having, orderBy)
		// database.up
		if (mCursor != null) {
			Log.e(tag, "cursor ok --" + mCursor.getCount());

			if (mCursor.moveToFirst()) {
				int i = 0;
				// Toast.makeText(context, "cursor ok", 1).show();
				int progress = 0;

				String realDateFinished = "";
				Double x = 3.0;
				Double y = 4.0;
				do {
					// Toast.makeText(context, "cursor ok..." + i++, 1).show();
					int j = 0;
					String taskId = mCursor.getString(j++);
					String tasktypeId = mCursor.getString(j++);
					String name = mCursor.getString(j++);
					String content = mCursor.getString(j++);
					String imgUrl = mCursor.getString(j++);
					String videoUrl = mCursor.getString(j++);
					String taskRoadId = mCursor.getString(j++);
					progress = mCursor.getInt(j++);
					String scheDateTo = mCursor.getString(j++);
					realDateFinished = mCursor.getString(j++);
					x = mCursor.getDouble(j++);
					y = mCursor.getDouble(j++);

					Location locationStaff = new Location("location Satff");
					locationStaff.setLatitude(x);
					locationStaff.setLongitude(y);
					float distance = 100;
					// distance = myLocation.distanceTo(locationStaff);

					// mTask = new Staff(staffid, name, address, "", iduserno,
					// id_issue_date, birthday, staff_code, distance,
					// channelTypeId, x, y

					// );
					if (progress == mProgress) {
						arrTaskObjects.add(new TaskObject_(taskId, taskId,
								taskRoadId, name, content, "", scheDateTo,
								realDateFinished, imgUrl, videoUrl, progress // arrTaskStatus[status]
								));
					}

					String msg = taskId + ", ";
					msg += tasktypeId + ", ";
					msg += name + ", ";
					msg += content + ", ";
					msg += imgUrl + ", ";
					msg += videoUrl + ", ";
					msg += progress + ", ";
					msg += realDateFinished + ", ";
					msg += x + ", ";
					msg += y + ", ";
					msg += progress + ", ";

					Log.e(tag, msg);
				} while (mCursor.moveToNext());
			}
		} else {
			// Toast.makeText(context, "cursor null", 1).show();
		}
		mCursor.close();
		try {
			close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrTaskObjects;
	}

	public int getcountPolicy() {
		int count = 0;
        String sql = "Select count(1)" +
                " from task t, task_road tr, staff st" +
                " where t.task_id = tr.task_id " + "and t.task_type_id = 10 and tr.progress = 1" + " and tr.status = 1  and tr.staff_id = " + Session.loginUser.getStaffId();

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
}

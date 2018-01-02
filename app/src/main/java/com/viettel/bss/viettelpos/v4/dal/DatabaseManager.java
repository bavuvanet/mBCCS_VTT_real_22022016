package com.viettel.bss.viettelpos.v4.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.savelog.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "mbccs.db";
	private static final String TABLE_NAME_IMAGE_BACKUP = "FileImageTable";
	private static final int DATABASE_VERSION = 1;

	private static final String COLUMN_FILE_ID = "file_id";
	private static final String COLUMN_FILE_PATH = "path";
	private static final String COLUMN_ACTION_CODE = "actionCode";
	private static final String COLUMN_RECENTID = "reasonId";

	private static final String COLUMN_ISDN = "isdn";
	private static final String COLUMN_STATE = "status";
	private static final String COLUMN_SPINER_CODE = "codeSpinner";
	private static final String COLUMN_ACTION_AUDIT = "actionAudit";
	private static final String COLUMN_PAGE_INDEX = "pageIndex";
	private static final String COLUMN_PAGE_SIZE = "pageSize";

	public DatabaseManager(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_CONTACTS_TABLE = "CREATE TABLE "
				+ TABLE_NAME_IMAGE_BACKUP + "(" + COLUMN_FILE_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"

				+ COLUMN_FILE_PATH + " TEXT," + COLUMN_ACTION_CODE + " TEXT,"
				+ COLUMN_RECENTID + " TEXT,"

				+ COLUMN_ISDN + " TEXT," + COLUMN_STATE + " INTEGER NOT NULL,"
				+ COLUMN_SPINER_CODE + " TEXT," + COLUMN_ACTION_AUDIT
				+ " TEXT,"

				+ COLUMN_PAGE_INDEX + " TEXT," + COLUMN_PAGE_SIZE + " TEXT)";

		db.execSQL(CREATE_CONTACTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS event");
		onCreate(db);

	}

	public void insertImageBackUpToDatabase(FileObj fileObject, String filePath) {
		SQLiteDatabase db = null;
		try {

			db = this.getWritableDatabase();

			ContentValues values = new ContentValues();

			values.put(COLUMN_FILE_PATH, filePath);
			values.put(COLUMN_ACTION_CODE, fileObject.getActionCode());
			values.put(COLUMN_RECENTID, fileObject.getReasonId());
			values.put(COLUMN_ISDN, fileObject.getIsdn());
			values.put(COLUMN_STATE, fileObject.getStatus());
			values.put(COLUMN_SPINER_CODE, fileObject.getCodeSpinner());
			values.put(COLUMN_ACTION_AUDIT, fileObject.getActionAudit());
			values.put(COLUMN_PAGE_INDEX, fileObject.getPageIndex());
			values.put(COLUMN_PAGE_SIZE, fileObject.getPageSize());

			// //////////////
			long insert = db.insert(TABLE_NAME_IMAGE_BACKUP, null, values);

			Log.e("LogHelper inserted Log with Row: " + insert);// + " " +
																// values.toString());

		} catch (Exception e) {
			android.util.Log.e("error insert file to database",
					"DATABASE_MANAGER", e);
		} finally {
			if (db != null) {
				db.close();
			}
		}

	}

	public List<FileObj> getAllListFileObjectWithFileState(int state) {
		ArrayList<FileObj> mArrayList = new ArrayList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		String sql = " select * from " + TABLE_NAME_IMAGE_BACKUP +" order by file_id" ;
//				+ " where status < ? ";
		// Log.d("Log insert file to data base: " + sql);
		Cursor mCursor = null;
		try {
			mCursor = db.rawQuery(sql,new String[]{});
			if (mCursor.moveToFirst()) {
				while (!mCursor.isAfterLast()) {
					String filePath = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_FILE_PATH));
					String actionCode = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ACTION_CODE));
					String recentId = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_RECENTID));
					String isdn = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ISDN));
					int fileState = mCursor.getInt(mCursor
							.getColumnIndex(COLUMN_STATE));
					String spinnerCode = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_SPINER_CODE));
					String actionAudit = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ACTION_AUDIT));
					String pageIndex = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_PAGE_INDEX));
					String pageSize = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_PAGE_SIZE));
					int idfile = mCursor.getInt(mCursor
							.getColumnIndex(COLUMN_FILE_ID));

					FileObj fileObject = new FileObj();
					fileObject.setPath(filePath);
					fileObject.setActionCode(actionCode);
					fileObject.setReasonId(recentId);
					fileObject.setIsdn(isdn);
					fileObject.setStatus(fileState);
					fileObject.setCodeSpinner(spinnerCode);
					fileObject.setActionAudit(actionAudit);
					fileObject.setPageSize(pageSize);
					fileObject.setPageIndex(pageIndex);
					fileObject.setId(idfile);
					fileObject.setRecordId((long) idfile);
					
					mArrayList.add(fileObject);
					mCursor.moveToNext();
				}
			}
		} catch (Exception e) {
			android.util.Log.e("error insert file to database",
					"DATABASE_MANAGER", e);
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
			db.close();
		}

		// Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}

	public boolean deleteFileBackup(FileObj fileObject) {
		SQLiteDatabase db = null;
		boolean isDeleteRows = false;
		try {
			db = this.getWritableDatabase();
			String whereClause = COLUMN_ISDN + " = ? AND " + COLUMN_SPINER_CODE
					+ " = ? AND " + COLUMN_ACTION_CODE + " = ? AND "
					+ COLUMN_ACTION_AUDIT + " = ? ";
			String[] whereArgs = new String[] { fileObject.getIsdn(),
					fileObject.getCodeSpinner(), fileObject.getActionCode(),
					fileObject.getActionAudit() };
			// isDeleteRows = db.delete(TABLE_NAME_IMAGE_BACKUP, COLUMN_FILE_ID
			// + "=" + fileObject.getId(), null) > 0;
			isDeleteRows = db.delete(TABLE_NAME_IMAGE_BACKUP, whereClause,
					whereArgs) > 0;
		} catch (Exception e) {
			android.util.Log.e("error insert file to database",
					"DATABASE_MANAGER", e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
		android.util.Log.d("Log", "delete file state: " + isDeleteRows);
		return isDeleteRows;
	}

	public void updateFileState(FileObj fileObject) {
		SQLiteDatabase db = null;
		try {
			db = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put(COLUMN_STATE, fileObject.getStatus());

			String whereClause = COLUMN_ISDN + " = ? AND " + COLUMN_SPINER_CODE
					+ " = ? AND " + COLUMN_ACTION_CODE + " = ? ";
			String[] whereArgs = new String[] { fileObject.getIsdn(),
					fileObject.getCodeSpinner(), fileObject.getActionCode() };

			db.update(TABLE_NAME_IMAGE_BACKUP, values, whereClause, whereArgs);

		} catch (Exception e) {
			android.util.Log.e("error insert file to database",
					"DATABASE_MANAGER", e);
		} finally {
			if (db != null) {
				db.close();
			}
		}
	}

	public List<FileObj> getListFileBackUp(FileObj fileObj) {
		ArrayList<FileObj> mArrayList = new ArrayList<>();

		SQLiteDatabase db = this.getWritableDatabase();
		String sql = " select * from " + TABLE_NAME_IMAGE_BACKUP
				+ " where isdn = ? AND codeSpinner = ? AND actionCode = ? ";
		String[] whereArgs = new String[] { fileObj.getIsdn(),
				fileObj.getCodeSpinner(), fileObj.getActionCode() };
		// Log.d("Log insert file to data base: " + sql);
		Cursor mCursor = null;
		try {
			mCursor = db.rawQuery(sql, whereArgs);
			if (mCursor.moveToFirst()) {
				while (!mCursor.isAfterLast()) {
					String filePath = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_FILE_PATH));
					String actionCode = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ACTION_CODE));
					String recentId = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_RECENTID));
					String isdn = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ISDN));
					int fileState = mCursor.getInt(mCursor
							.getColumnIndex(COLUMN_STATE));
					String spinnerCode = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_SPINER_CODE));
					String actionAudit = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_ACTION_AUDIT));
					String pageIndex = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_PAGE_INDEX));
					String pageSize = mCursor.getString(mCursor
							.getColumnIndex(COLUMN_PAGE_SIZE));
					int idfile = mCursor.getInt(mCursor
							.getColumnIndex(COLUMN_FILE_ID));

					FileObj fileObject = new FileObj();
					fileObject.setPath(filePath);
					fileObject.setActionCode(actionCode);
					fileObject.setReasonId(recentId);
					fileObject.setIsdn(isdn);
					fileObject.setStatus(fileState);
					fileObject.setCodeSpinner(spinnerCode);
					fileObject.setActionAudit(actionAudit);
					fileObject.setPageSize(pageSize);
					fileObject.setPageIndex(pageIndex);
					fileObject.setId(idfile);

					mArrayList.add(fileObject);
					mCursor.moveToNext();
				}
			}
		} catch (Exception e) {
			android.util.Log.e("error insert file to database",
					"DATABASE_MANAGER", e);
		} finally {
			if (mCursor != null) {
				mCursor.close();
			}
			db.close();
		}

		// Log.i("TAG2", "mArrayList size = " + mArrayList.size());
		return mArrayList;

	}

}

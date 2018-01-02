package com.viettel.bss.viettelpos.v4.synchronizationdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.DBOpenHelper;
import com.viettel.bss.viettelpos.v4.commons.Define.MAXORAROWSCN;
import com.viettel.bss.viettelpos.v4.syn.object.SynchronizeDataObject;

import java.util.ArrayList;

public class GetMaxOraRowScnDal {
    private final String logTag = GetMaxOraRowScnDal.class.getName();
    @SuppressWarnings("unused")
    private SQLiteDatabase database = null;
    private DBOpenHelper getmaxorarow = null;

    public GetMaxOraRowScnDal(Context context) {
        getmaxorarow = new DBOpenHelper(context);
    }

    // open databasess
    public void open() {
        database = getmaxorarow.getWritableDatabase();
    }

    // close data base
    public void close() {
        try {
            getmaxorarow.close();
            database.close();
        } catch (Exception ignored) {
            Log.e("Exception", "Exception", ignored);
        }

    }

    // fuction insert
    public void runQueryDataFromServer(String query) {
        SQLiteDatabase db = null;
        try {
            db = getmaxorarow.getWritableDatabase();
            db.execSQL(query);
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    public void runquery(String query) {
        StringBuilder sql = new StringBuilder();
        sql.append(query);
        try {
            database.execSQL(sql.toString());
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

    }

    // update full table
    public ArrayList<OjectSyn> getAllListsyn() {
        ArrayList<OjectSyn> lisOjectSyns = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select table_name,max_ora_rowscn from max_ora_rowscn");
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql.toString(), null);
            if (cursor == null) {
                return lisOjectSyns;
            }
            if (cursor.moveToFirst()) {
                do {
                    OjectSyn ojectSyn = new OjectSyn();
                    String tableName = cursor.getString(0);
                    Log.d("tableName", tableName);
                    ojectSyn.setTable_name(tableName);
                    Long maxOraRow = cursor.getLong(1);
                    Log.d("maxOraRow", "" + maxOraRow);
                    ojectSyn.setMax_ora_rowscn("" + maxOraRow);
                    lisOjectSyns.add(ojectSyn);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return lisOjectSyns;

    }

    public ArrayList<OjectSyn> getlistSyn(String tableName, String ssyn_tatus) {
        ArrayList<OjectSyn> aOjectSyns = new ArrayList<>();
        Cursor mCursor = null;
        // String filter = MAXORAROWSCN.FIELD_STATUS + " = ?" ;


        String filter = MAXORAROWSCN.FIELD_STATUS
                + " = ?"
                + "or upper(table_name) in ('STAFF','PRICE','PRICE_SHOP_MAP','PRICE_CHANNEL_MAP','PRICE_SALES_PROGRAM_MAP'," +
                "'STOCK_CARD','STOCK_SIM','STOCK_HANDSET','STOCK_ACCESSORIES','STOCK_TRANS_SERIAL','STOCK_KIT'," +
                "'STOCK_ORDER','STOCK_ORDER_DETAIL','SALES_PROGRAM', 'STOCK_TOTAL')";
        String[] value = new String[]{ssyn_tatus};
        SQLiteDatabase db = null;
        try {
            db = getmaxorarow.getReadableDatabase();

            mCursor = db.query(tableName, new String[]{
                            MAXORAROWSCN.FIELD_TABLE_NAME,
                            MAXORAROWSCN.Field_max_ora_rowscn}, filter, value,
                    null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                while (!mCursor.isAfterLast()) {
                    String table_name = mCursor.getString(0);
                    String max_ora = mCursor.getString(1);
                    aOjectSyns.add(new OjectSyn(table_name, max_ora));
                    mCursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

        if (db != null) {
            db.close();
        }
        return aOjectSyns;
    }

    // fuction query script
    public String getMaxOrarow(String TABLE_NAME, String tableName) {
        String maxOrarow = null;
        Cursor mCursor = null;
        String filter = MAXORAROWSCN.FIELD_TABLE_NAME + " = ?";
        String[] value = new String[]{tableName};
        SQLiteDatabase db = null;
        try {
            db = getmaxorarow.getReadableDatabase();

                mCursor = db.query(TABLE_NAME,
                        new String[]{MAXORAROWSCN.Field_max_ora_rowscn},
                        filter, value, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
                maxOrarow = mCursor.getString(0);
                Log.d("TAG", maxOrarow);
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return maxOrarow;
    }

    // update field sync_status
    public void updatestatus(String tableName, String sync_status,
                             String table_name) {
        SQLiteDatabase db = null;
        try {
            db = getmaxorarow.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MAXORAROWSCN.FIELD_STATUS, sync_status);
            String filter = MAXORAROWSCN.FIELD_TABLE_NAME + " = ?";
            String[] mvalue = new String[]{table_name};
            db.update(tableName, values, filter, mvalue);
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    // check select sync_status
    public String CheckSyncStatus() {
        String sync_status = "";
        StringBuilder sql = new StringBuilder();
        sql.append("Select sync_status from max_ora_rowscn");
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql.toString(), null);
            Log.d("sql update2", sql.toString());
            if (cursor == null) {
                return sync_status;
            }
            if (cursor.moveToFirst()) {
                do {
                    sync_status = cursor.getString(1);
                    Log.d("sync_status", sync_status);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return sync_status;
    }

    // update field syn_status and max_ora_row
    // update max_ora_rowscn set max_ora_rowscn = '0' ,sync_status = '1' where
    // lower(table_name) = 'ap_param'
    public void update2(String tableName, String max_ora_rowscn,
                        String sync_status) {
        StringBuilder sql = new StringBuilder();
        sql.append("update max_ora_rowscn");
        sql.append("  set max_ora_rowscn = ?,");
        sql.append(" sync_status = ?");
        sql.append(" where lower(table_name) = ?");
        try {

            database.execSQL(sql.toString(),
                    new String[]{max_ora_rowscn + "", sync_status + "",
                            tableName.toLowerCase() + ""});
            Log.e(logTag, sql.toString() + "-->max_ora_rowscn: "
                    + max_ora_rowscn + "|status: " + sync_status
                    + "|tableName: " + tableName.toLowerCase());
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

    }

    public void update3(String tableName, String sync_status) {

        StringBuilder sql = new StringBuilder();
        sql.append("update max_ora_rowscn");
        sql.append(" set sync_status = ?");
        sql.append(" where lower(table_name) = ?");
        try {
            database.execSQL(sql.toString(), new String[]{sync_status + "",
                    tableName.toLowerCase() + ""});
            Log.e(logTag, sql.toString() + "-->" + "|status: " + sync_status
                    + "|tableName: " + tableName.toLowerCase());
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

    }

    // fuction update theo ten bang
    // public void updateSyncStatus()
    // update max_ora_rowscn set sync_status='0' where table_name='area' and
    // table_name='ap_param'
    public void updateSyncStatus(ArrayList<String> listnametable) {
        try {
            for (String tableName : listnametable) {
                String sql = "update max_ora_rowscn" +
                        " set sync_status='0'" +
                        "  where table_name = ?";
                database.execSQL(sql,
                        new String[]{tableName + ""});

            }
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }

    }

    // update max_ora_rowscn set max_ora_rowscn='121324',sync_status='1' where
    // table_name='area'
    // update field sync_status and max_ora_rowscn
    public void update(String tableName, String sync_status, String table_name,
                       String _max_ora_rows) {
        SQLiteDatabase db = null;
        try {
            db = getmaxorarow.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MAXORAROWSCN.FIELD_STATUS, sync_status);
            values.put(MAXORAROWSCN.Field_max_ora_rowscn, _max_ora_rows);
            String filter = MAXORAROWSCN.FIELD_TABLE_NAME + " = ?";
            String[] mvalue = new String[]{table_name};
            db.update(tableName, values, filter, mvalue);
        } finally {
            if (db != null) {
                db.close();
            }
        }

    }

    public void syncChange(ArrayList<SynchronizeDataObject> lstObject) {
        SQLiteDatabase db = null;
        SQLiteStatement stmt = null;
        SQLiteStatement tableStmt = null;
        try {
            db = getmaxorarow.getWritableDatabase();
            db.beginTransactionNonExclusive();
            tableStmt = db
                    .compileStatement("update max_ora_rowscn set sync_status = 1, max_ora_rowscn = ?  where lower(table_name) = ?  ");
            for (SynchronizeDataObject item : lstObject) {
                int count = 0;
                Log.e(logTag, "begin sync table: " + item.getTableName()
                        + "-----num of record: " + item.getLstData().size());
                Log.e(logTag, item.getOraRowSCN());
                stmt = db.compileStatement(item.getQuery());
                if (item.getLstData() == null || item.getLstData().isEmpty()) {
                    continue;
                }
//				Log.e(logTag, item.getQuery());
                for (String data : item.getLstData()) {
                    String[] arrData = data.split("\\@\\#\\$");

                    stmt.bindAllArgsAsStrings(arrData);
                    count = count + stmt.executeUpdateDelete();
                    stmt.clearBindings();
                }
                tableStmt.bindString(1, item.getOraRowSCN());
                tableStmt.bindString(2, item.getTableName());
//				Log.e(logTag, "update table: " + item.getTableName()
//						+ " with oraRowSCN: " + item.getOraRowSCN());
                tableStmt.executeUpdateDelete();
                tableStmt.clearBindings();
                Log.e(logTag, "finish sync table: " + item.getTableName()
                        + "-----num of record updated: " + count);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        } finally {
            if (db != null) {
                try {
                    if (db.inTransaction()) {
                        db.setTransactionSuccessful();
                        db.endTransaction();
                    }
                } catch (Exception e2) {
                    Log.e("Exception", "Exception", e2);
                }

                db.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (tableStmt != null) {
                tableStmt.close();
            }
        }
    }
}
